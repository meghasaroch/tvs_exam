package com.example.tvs_task;

import java.io.Serializable;

public class User implements Serializable {

    String username,designation,location,date,salary;
    int id;

    public User() {
    }

    public User(String username, String designation, String location, int id,String date, String salary) {
        this.username = username;
        this.designation = designation;
        this.location = location;
        this.date = date;
        this.salary = salary;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
