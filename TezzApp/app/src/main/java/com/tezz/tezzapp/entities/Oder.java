package com.tezz.tezzapp.entities;

/**
 * Created by User on 12/31/2017.
 */
public class Oder {

    private int id,tid,cid;
    private String height,chest,details,side,front;
    private User customer;

    public Oder(int id, int tid, int cid, String height, String chest, String details, String side, String front) {
        this.id = id;
        this.tid = tid;
        this.cid = cid;
        this.height = height;
        this.chest = chest;
        this.details = details;
        this.side = side;
        this.front = front;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
