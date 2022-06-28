package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("phone")
public class Phone {
    @Id
    private final Long clientId;
    @Nonnull
    private final String number;


    public Phone(@Nonnull String number) {
        this(null, number);
    }

    @PersistenceConstructor
    public Phone(Long clientId, @Nonnull String number) {
        this.clientId = clientId;
        this.number = number;
    }

    public Long getClientId() {
        return clientId;
    }

    @Nonnull
    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "clientId=" + clientId +
                ", number='" + number + '\'' +
                '}';
    }
}
