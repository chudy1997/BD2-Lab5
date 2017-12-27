package model;

import javax.persistence.*;

/**
 * Created by Karol on 2017-12-23.
 */
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String description;
    private int quantity;
    private double unitPrice;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(){ }
    public Product(String name, String description, int quantity, double unitPrice, Category category, Supplier supplier) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
