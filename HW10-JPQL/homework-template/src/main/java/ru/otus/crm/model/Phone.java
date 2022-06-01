package ru.otus.crm.model;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@ToString
public class Phone implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String number;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_client"))
    @ToString.Exclude
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone clone() {
        return new Phone(this.id, this.number);
    }
}
