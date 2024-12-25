package org.example.rmsproject.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="menus")
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "menu")
    private List<Category> categories;


    public Menu() {

    }


    public Menu(String name, List<Category> categories) {
        this.name = name;
        this.categories = categories;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}