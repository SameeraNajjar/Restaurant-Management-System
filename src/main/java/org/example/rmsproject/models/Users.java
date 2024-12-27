package org.example.rmsproject.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullName")
    private String fullName;



    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;

    // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //  @JoinColumn(name = "user_id") // Links roles via user ID
    // private Set<Role> roles = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Users() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return fullName;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // public Set<Role> getRoles() {
    // return roles;
    // }

    // public void setRoles(Set<Role> roles) {
    //    this.roles = roles;
    //  }
}
