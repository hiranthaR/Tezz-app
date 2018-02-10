package com.tezz.tezzapp.entities;

/**
 * Created by User on 12/29/2017.
 */
public class User {

    private int id;
    private String username,name,password,address,email,telephone;
    private int who;

    public User(int id, String username, String name, String password, String address, String email, String telephone, int who) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.who = who;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }
}
