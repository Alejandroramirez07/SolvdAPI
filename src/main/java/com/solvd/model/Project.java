package com.solvd.model;

public class Project {

    int projectCode;
    String projectName;

    public Project() {}

    public Project(int  projectCode, String  projectName){
        this.projectCode= projectCode;
        this.projectName= projectName;
    }

    public int getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(int  projectCode) {
        this.projectCode =  projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String  projectName) {
        this.projectName =  projectName;
    }

    @Override
    public String toString(){
        return "The project name is " + projectName + ". The project code is " + projectCode;
    }
}

