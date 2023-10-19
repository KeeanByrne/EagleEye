package com.example.eagleeye;

public class SettingsData {
    String ProfilePic = "Default";
    String Metric = "Default";
    int MaxDistance = 0;
    String UserID = "";
    String Username = "Default";
    String Password = "Default";

    //Default constructor
    public SettingsData(){}

    //Constructor
    public SettingsData(String newPic, String newMetric, int newMaxD, String newUserID, String newUsername, String newPassword){
        this.ProfilePic = newPic;
        this.Metric = newMetric;
        this.MaxDistance = newMaxD;
        this.UserID = newUserID;
        this.Username = newUsername;
        this.Password = newPassword;
    }
}
