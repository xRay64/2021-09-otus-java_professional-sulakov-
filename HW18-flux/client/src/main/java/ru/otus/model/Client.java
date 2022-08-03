package ru.otus.model;

import lombok.Data;

import java.util.Set;

@Data
public class Client {
    private Long id;
    private String name;
    private Address address;
    private Set<Phone> phones;
}
