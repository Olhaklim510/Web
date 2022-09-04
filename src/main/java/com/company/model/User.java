package com.company.model;

public class User {
    public int id;
    public String name;
    public String username;
    public String email;
    public Adress address;
    public String phone;
    public String website;
    public Company company;

    public User() {
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", company=" + company +
                '}';
    }

    public int getId() {
        return id;
    }
}
