package com.solvd.database.model;

public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private int projectCode;

    public User() {}

    public User(Long id, String username, String email, String password, int projectCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.projectCode = projectCode;
    }

    public Long getId() {
        return id;
    }

    public void setId() {
        this.id=id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public int getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(int projectCode) {
        this.projectCode = projectCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return " username:  " + this.username  + ", email : " + this.email + ", project code: " + this.projectCode + " password is secret ";
    }
}

