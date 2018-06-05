package com.example.mobileda.englishcenter.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Teacher{


    private String id;
    private String name;
    private String address;
    private Date birthday;
    private String literacy;

    public Teacher(String name, String address, Date birthday, String literacy) {
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.literacy = literacy;
    }

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

    public Map<String, Object> toMap(){
        Map<String, Object> noteDataMap = new HashMap<>();
        noteDataMap.put("name", name);
        noteDataMap.put("literacy", literacy);
        noteDataMap.put("birthday", birthday);
        noteDataMap.put("address", address);
        noteDataMap.put("income", income);
        return  noteDataMap;
    }
}
