package com.example.mobileda.englishcenter.model;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

//public class TeacherLogin implements Serializable {
//    private String teacher_id;
//    private String uname;
//    private String pwd;
//
//
//    public String getTeacher_id() {
//        return teacher_id;
//    }
//
//    public void setTeacher_id(String teacher_id) {
//        this.teacher_id = teacher_id;
//    }
//
//    public String getUname() {
//        return uname;
//    }
//
//    public void setUname(String uname) {
//        this.uname = uname;
//    }
//
//    public String getPwd() {
//        return pwd;
//    }
//
//    public void setPwd(String pwd) {
//        this.pwd = pwd;
//    }
//
//
//
//    public TeacherLogin(String teacher_id, String uname, String pwd) {
//        this.teacher_id = teacher_id;
//        this.uname = uname;
//        this.pwd = pwd;
//    }
//
//    public TeacherLogin(){}
//
//
//
//
//}

public class TeacherLogin implements Serializable {

    private DocumentReference teacher;
    private String uname;
    private String pwd;

    public DocumentReference getTeacher() {
        return teacher;
    }
    public TeacherLogin(){}

    public void setTeacher(DocumentReference teacher) {
        this.teacher = teacher;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public TeacherLogin(DocumentReference teacher, String uname, String pwd) {
        this.teacher = teacher;
        this.uname = uname;
        this.pwd = pwd;
    }
}
