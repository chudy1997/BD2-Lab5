package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Karol on 2017-12-23.
 */
@Entity
public class Customer extends Company{
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    private List<ShopTransaction> transactions;

    private String login;
    private String password;

    public Customer(){
        super();
        this.transactions = new LinkedList<>();
    }

    public Customer(String companyName, Address address, String login, String password){
        super(companyName, address);
        this.transactions = new LinkedList<>();
        this.login = login;
        this.password = password;
    }

    public List<ShopTransaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(ShopTransaction transaction) {
        this.transactions.add(transaction);
    }

    public void removeTransaction(ShopTransaction transaction) {
        this.transactions.remove(transaction);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}