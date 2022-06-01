package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private final WeakHashMap<K, V> cacheMap = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cacheMap.put(key, value);
        listeners.forEach(kvHwListener -> kvHwListener.notify(key, value, "PUT"));
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
        listeners.forEach(kvHwListener -> kvHwListener.notify(key, null, "REMOVE"));
    }

    @Override
    public V get(K key) {
        V value = cacheMap.get(key);
        listeners.forEach(kvHwListener -> kvHwListener.notify(key, value, "GET"));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
