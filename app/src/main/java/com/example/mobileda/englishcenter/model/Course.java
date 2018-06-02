package com.example.mobileda.englishcenter.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private String id;
    private String cost;
    private String course;
    private String time;
    private String room;
    private String state;
    private DocumentReference teacher;

    public Course() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public DocumentReference getTeacher() {
        return teacher;
    }

    public void setTeacher(DocumentReference teacher) {
        this.teacher = teacher;
    }

    public Course(String cost, String course, String time, String room, String state, DocumentReference teacher) {
        this.cost = cost;
        this.course = course;
        this.time = time;
        this.room = room;
        this.state = state;
        this.teacher = teacher;
    }

    public Course(String id, String cost, String course, String time, String room, String state, DocumentReference teacher) {
        this.id = id;
        this.cost = cost;
        this.course = course;
        this.time = time;
        this.room = room;
        this.state = state;
        this.teacher = teacher;
    }
    @Override
    public String toString(){
        return this.course;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> noteDataMap = new HashMap<>();
        noteDataMap.put("cost", cost);
        noteDataMap.put("course", course);
        noteDataMap.put("room", room);
        noteDataMap.put("state", state);
        noteDataMap.put("teacher", teacher);
        noteDataMap.put("time", time);
        return  noteDataMap;
    }
}
