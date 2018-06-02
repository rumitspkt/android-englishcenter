package com.example.mobileda.englishcenter.model;

import java.util.Date;

public class Teacher{


    private String id;
    private String name;
    private String address;
    private Date birthday;
    private String literacy;
    private int income;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLiteracy() {
        return literacy;
    }

    public void setLiteracy(String literacy) {
        this.literacy = literacy;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public Teacher() {
    }

    public Teacher(String name, String address, Date birthday, String literacy, int income) {
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.literacy = literacy;
        this.income = income;
    }

    public Teacher(String id, String name, String address, Date birthday, String literacy, int income) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.literacy = literacy;
        this.income = income;
    }
}
