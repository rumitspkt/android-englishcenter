package com.example.mobileda.englishcenter.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private String id;
    private String title;
    private String content;
    private DocumentReference teacher;
    private DocumentReference course;

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DocumentReference getTeacher() {
        return teacher;
    }

    public void setTeacher(DocumentReference teacher) {
        this.teacher = teacher;
    }

    public DocumentReference getCourse() {
        return course;
    }

    public void setCourse(DocumentReference course) {
        this.course = course;
    }

    public Message(String title, String content, DocumentReference teacher, DocumentReference course) {
        this.title = title;
        this.content = content;
        this.teacher = teacher;
        this.course = course;
    }

    public Message(String id, String title, String content, DocumentReference teacher, DocumentReference course) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.teacher = teacher;
        this.course = course;
    }
    public Map<String, Object> toMap(){
        Map<String, Object> noteDataMap = new HashMap<>();
        noteDataMap.put("title", title);
        noteDataMap.put("content", content);
        noteDataMap.put("teacher", teacher);
        noteDataMap.put("course", course);
        return  noteDataMap;
    }
}
