package ru.otus.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.Set;

@Getter
@Setter
@Table("client")
public class Client {
    @Id
    private final Long id;
    @Nonnull
    private final String name;
    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Address address;
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    public Client(@Nonnull String name) {
        this(null, name, null, null);
    }

    public Client(@Nonnull String name, Address address,  Set<Phone> phones) {
        this(null, name, address, phones);
    }

    @PersistenceConstructor
    public Client(Long id, @Nonnull String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

}
