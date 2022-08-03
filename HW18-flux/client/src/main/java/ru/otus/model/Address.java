package ru.otus.model;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private Long clientId;
    private String street;
}
