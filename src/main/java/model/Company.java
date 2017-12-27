package model;

import javax.persistence.*;

/**
 * Created by Karol on 2017-12-25.
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Company(){}
    public Company(String companyName, Address address){
        this.companyName = companyName;
        this.address = address;
    }

    private String companyName;

    @Embedded
    private Address address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
