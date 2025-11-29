package com.solvd.model;

public class Manager {

    String username;
    String email;
    String password;
    int projectCode;
    int employeeCode;

    public Manager(String username, String email, String password, int projectCode, int employeeCode){
        this.username=username;
        this.email=email;
        this.password=password;
        this.projectCode=projectCode;
        this.employeeCode=employeeCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(int projectCode) {
        this.projectCode = projectCode;
    }

    public int getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Override
    public String toString() {
        return " username:  " + this.username  + ", email : " + this.email + ", project code: " + this.projectCode + " password is secret ";
    }
}
