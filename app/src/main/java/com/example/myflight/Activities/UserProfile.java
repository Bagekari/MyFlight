package com.example.myflight.Activities;

public class UserProfile {
    private String Email, Name;

    public UserProfile() {

    }

    UserProfile(String userEmail, String userName) {
        this.Email = userEmail;
        this.Name = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
