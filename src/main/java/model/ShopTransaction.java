package model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Karol on 2017-12-25.
 */
@Entity
public class ShopTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionNumber;

    @OneToMany(mappedBy = "shopTransaction", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    private List<ProductOrder> productOrders;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public ShopTransaction(){
        this.productOrders = new LinkedList<>();
    }

    public ShopTransaction(Customer customer){
        this();
        this.customer = customer;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void addProductOrder(ProductOrder productOrder) {
        this.productOrders.add(productOrder);
    }

    public void removeProductOrder(ProductOrder productOrder) {
        this.productOrders.remove(productOrder);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}