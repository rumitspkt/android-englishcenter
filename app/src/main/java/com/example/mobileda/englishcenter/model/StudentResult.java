package com.example.mobileda.englishcenter.model;

public class StudentResult {
    private String student_id;
    private String course_id;
    private String student;
    private String description;
    private Float midtermMark;
    private Float finaltermMark;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getMidtermMark() {
        return midtermMark;
    }

    public void setMidtermMark(Float midtermMark) {
        this.midtermMark = midtermMark;
    }

    public Float getFinaltermMark() {
        return finaltermMark;
    }

    public void setFinaltermMark(Float finaltermMark) {
        this.finaltermMark = finaltermMark;
    }

    public StudentResult(String student, String description) {
        this.student = student;
        this.description = description;
    }

    public StudentResult(String student, String description, Float midtermMark) {
        this.student = student;
        this.description = description;
        this.midtermMark = midtermMark;
    }

    public StudentResult(String student, String description, Float midtermMark, Float finaltermMark) {
        this.student = student;
        this.description = description;
        this.midtermMark = midtermMark;
        this.finaltermMark = finaltermMark;
    }

    public StudentResult(String student_id, String course_id, String student, String description, Float midtermMark, Float finaltermMark) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.student = student;
        this.description = description;
        this.midtermMark = midtermMark;
        this.finaltermMark = finaltermMark;
    }

    @Override
    public String toString(){
        return this.student;
    }
}
