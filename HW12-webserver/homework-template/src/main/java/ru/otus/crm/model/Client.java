package ru.otus.crm.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "client"
    )
    @JoinColumn(
            name = "id_address",
            foreignKey = @ForeignKey(name = "fk_address")
    )
    private Address address;

    @Column(name = "password_md5")
    private String passwordMD5;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "client"
    )
    private List<Phone> phones = new ArrayList<>();

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        if (address != null) {
            address.setClient(this);
        }
        this.phones = phones;
        if (phones != null) {
            phones.forEach(phone -> phone.setClient(this));
        }
    }

    @Override
    public Client clone() {
        List<Phone> copyPhones = new ArrayList<>();
        this.phones.forEach(phone -> copyPhones.add(phone.clone()));
        return new Client(
                this.id,
                this.name,
                this.address.clone(),
                copyPhones
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        address.setClient(this);
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        phones.forEach(phone -> phone.setClient(this));
        this.phones = phones;
    }

    public String getPasswordMD5() {
        return passwordMD5;
    }

    public void setPasswordMD5(String password) {
        this.passwordMD5 = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
