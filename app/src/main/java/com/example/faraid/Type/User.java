package com.example.faraid.Type;

public class User {
    String username, email, password, noPhone, lvl;

    public User() {
    }

    public User(String username, String email, String password, String coPassword, String lvl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.noPhone = coPassword;
        this.lvl = lvl;
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

    public String getCoPassword() {
        return noPhone;
    }

    public void setCoPassword(String coPassword) {
        this.noPhone = coPassword;
    }

    public String getLvl() {
        return lvl;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }
}