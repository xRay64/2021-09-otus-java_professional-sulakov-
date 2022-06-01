package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import java.util.List;
import java.util.Optional;

public class DbServiceManagerImpl implements DBServiceManager {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);
    private static final MyCache<Long, Manager> cache = new MyCache<>();

    private final DataTemplate<Manager> managerDataTemplate;
    private final TransactionRunner transactionRunner;

    public DbServiceManagerImpl(TransactionRunner transactionRunner, DataTemplate<Manager> managerDataTemplate) {
        this.transactionRunner = transactionRunner;
        this.managerDataTemplate = managerDataTemplate;

        HwListener<Long, Manager> listener = new HwListener<Long, Manager>() {
            @Override
            public void notify(Long key, Manager value, String action) {
                log.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };
        cache.addListener(listener);
    }

    @Override
    public Manager saveManager(Manager manager) {
        return transactionRunner.doInTransaction(connection -> {
            if (manager.getNo() == null) {
                var managerNo = managerDataTemplate.insert(connection, manager);
                var createdManager = new Manager(managerNo, manager.getLabel(), manager.getParam1());
                log.info("created manager: {}", createdManager);
                cacheManager(createdManager);
                return createdManager;
            }
            managerDataTemplate.update(connection, manager);
            log.info("updated manager: {}", manager);
            cacheManager(manager);
            return manager;
        });
    }

    @Override
    public Optional<Manager> getManager(long no) {
        Optional<Manager> manager = Optional.ofNullable(cache.get(no));

        if (manager.isEmpty()) {
            manager = transactionRunner.doInTransaction(connection -> {
                var managerOptional = managerDataTemplate.findById(connection, no);
                log.info("manager: {}", managerOptional);
                return managerOptional;
            });
        }
        return manager;
    }

    @Override
    public List<Manager> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var managerList = managerDataTemplate.findAll(connection);
            log.info("managerList:{}", managerList);
            return managerList;
       });
    }

    private void cacheManager(Manager manager) {
        if (manager.getNo() != null) {
            cache.put(manager.getNo(), manager);
        }
    }
}
