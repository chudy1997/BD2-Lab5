package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by Karol on 2017-12-23.
 */
@Entity
public class Supplier extends Company{
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    private Set<Product> products;

    public Supplier(){
        super();
    }

    public Supplier(String companyName, Address address){
        super(companyName, address);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
