package com.solvd.model;

public class Cloud {

    private int gcpProjectCode;
    private int awsProjectCode;
    private int azureProjectCode;

    public Cloud(int gcpProjectCode, int awsProjectCode, int azureProjectCode){
        this.gcpProjectCode=gcpProjectCode;
        this.awsProjectCode=awsProjectCode;
        this.azureProjectCode=azureProjectCode;
    }

    public int getGcpProjectCode() {
        return gcpProjectCode;
    }

    public void setGcpProjectCode(int gcpProjectCode) {
        this.gcpProjectCode = gcpProjectCode;
    }

    public int getAwsProjectCode() {
        return awsProjectCode;
    }

    public void setAwsProjectCode(int awsProjectCode) {
        this.awsProjectCode = awsProjectCode;
    }

    public int getAzureProjectCode() {
        return azureProjectCode;
    }

    public void setAzureProjectCode(int azureProjectCode) {
        this.azureProjectCode = azureProjectCode;
    }

    @Override
    public String toString(){
        return " The projects " + gcpProjectCode + " on GCP , " + awsProjectCode + "  on aws " + azureProjectCode +  " and Azure, are correlated ";
    }
}

