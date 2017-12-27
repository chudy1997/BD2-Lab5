package model;

import javax.persistence.*;

/**
 * Created by Karol on 2017-12-23.
 */
@Entity
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int quantity;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "shoptransaction_id")
    private ShopTransaction shopTransaction;

    public ProductOrder() { }
    public ProductOrder(int quantity, Product product, ShopTransaction shopTransaction){
        this.quantity = quantity;
        this.product = product;
        this.shopTransaction = shopTransaction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShopTransaction getShopTransaction() {
        return shopTransaction;
    }

    public void setShopTransaction(ShopTransaction shopTransaction) {
        this.shopTransaction = shopTransaction;
    }
}