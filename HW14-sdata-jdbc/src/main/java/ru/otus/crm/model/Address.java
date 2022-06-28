package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("client_address")
public class Address {
    @Id
    private final Long id;
    private final Long clientId;
    @Nonnull
    private final String street;

    public Address(@Nonnull String street) {
        this(null, null, street);
    }

    @PersistenceConstructor
    public Address(Long id, @Nonnull Long clientId, @Nonnull String street) {
        this.id = id;
        this.clientId = clientId;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    @Nonnull
    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", street='" + street + '\'' +
                '}';
    }
}
