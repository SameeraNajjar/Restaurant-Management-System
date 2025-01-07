package org.example.rmsproject.models.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Table(name = "menuitem")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "available")
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category; // العلاقة مع Category

    @Transient
    private StringProperty nameProperty;

    // Constructors
    public MenuItem() {
        this.nameProperty = new SimpleStringProperty(this, "name", name);
    }

    // مُنشئ جديد يتطابق مع التوقيع المستخدم ويتضمن الفئة
    public MenuItem(String name, String description, double price, boolean isAvailable, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.category = category;  // تعيين الفئة
    }

    // Getters and Setters
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
        this.nameProperty().set(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public StringProperty nameProperty() {
        if (nameProperty == null) {
            nameProperty = new SimpleStringProperty(this, "name", name);
        }
        return nameProperty;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + (category != null ? category.getName() : "No Category") +
                '}';
    }
}
