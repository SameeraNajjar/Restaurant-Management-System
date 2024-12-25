package org.example.rmsproject.models;



import javax.persistence.*;

@Entity
@Table(name="menu_items")
public class MenuItem {

    @Id
    @GeneratedValue
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private double price;

    @Column(name="is_available")
    private boolean isAvailable;

    // منشئ بدون وسائط
    public MenuItem() {
        // يمكن تركه فارغاً أو تعيين بعض القيم الافتراضية إذا كان ذلك مطلوباً
    }

    // منشئ مع وسائط (استخدامه عند الحاجة)
    public MenuItem(String name, String description, double price, boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters and Setters
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
}
