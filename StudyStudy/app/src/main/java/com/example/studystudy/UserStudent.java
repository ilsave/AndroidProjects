package com.example.studystudy;

public class UserStudent {
    private String email;
    private String password;
    private String name;
    private String gender;
    private String number;
    private String date;
    private String educationDirection;
    private String kafedraName;
    private String facultatyName;
    private String info;

    public UserStudent(String email, String password, String name, String gender, String number, String date, String educationDirection, String kafedraName, String facultatyName, String info) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.number = number;
        this.date = date;
        this.educationDirection = educationDirection;
        this.kafedraName = kafedraName;
        this.facultatyName = facultatyName;
        this.info = info;
    }

    public UserStudent(){

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEducationDirection() {
        return educationDirection;
    }

    public void setEducationDirection(String educationDirection) {
        this.educationDirection = educationDirection;
    }

    public String getKafedraName() {
        return kafedraName;
    }

    public void setKafedraName(String kafedraName) {
        this.kafedraName = kafedraName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFacultatyName() {
        return facultatyName;
    }

    public void setFacultatyName(String facultatyName) {
        this.facultatyName = facultatyName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
