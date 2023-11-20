package com.project.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="contact")
public class Contact {

    @Column(nullable = false, name="first_name")
    private String firstName;

    @Column( nullable = false, name="last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable=false, name="civility_id")
    private Civility civility;

    @OneToMany(mappedBy="contact")
    private List<Phone> phones;

    @OneToMany(mappedBy="contact")
    private List<Email> email;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "address",
            joinColumns = @JoinColumn(name = "contact_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName="id")
    )
    private List<Address> addresses;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Civility getCivility() {
        return civility;
    }

    public void setCivility(Civility civility) {
        this.civility = civility;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Email> getEmail() {
        return email;
    }

    public void setEmail(List<Email> email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }


}