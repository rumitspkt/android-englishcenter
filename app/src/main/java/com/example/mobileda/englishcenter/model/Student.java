package com.example.mobileda.englishcenter.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Student {
    private String id;
    private String name;
    private String address;
    private String literacy;
    private Date birthday;

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

    public String getLiteracy() {
        return literacy;
    }

    public void setLiteracy(String literacy) {
        this.literacy = literacy;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Student() {
    }

    public Student(String name, String address, String literacy, Date birthday) {
        this.name = name;
        this.address = address;
        this.literacy = literacy;
        this.birthday = birthday;
    }

    public Student(String id, String name, String address, String literacy, Date birthday) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.literacy = literacy;
        this.birthday = birthday;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> noteDataMap = new HashMap<>();
        noteDataMap.put("name", name);
        noteDataMap.put("literacy", literacy);
        noteDataMap.put("birthday", birthday);
        noteDataMap.put("address", address);
        return  noteDataMap;
    }
}
