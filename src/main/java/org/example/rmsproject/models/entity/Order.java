package org.example.rmsproject.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId") // Ensure this matches your DB column name
    private int orderId;

    @Column(name = "customerName", nullable = true)
    private String customerName;

    @Column(name = "specialInstructions", columnDefinition = "TEXT")
    private String specialInstructions;


    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "totalPrice", nullable = false)
    private double totalPrice;

    @ManyToMany
    @JoinTable(
            name = "order_order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "order_item_id")
    )
    private List<OrderItem> items;





    public Order(int orderId, String customerName, String specialInstructions, String status, double totalPrice, List<OrderItem> items) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.specialInstructions = specialInstructions;
        this.status = status;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Order() {

    }


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }


    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
