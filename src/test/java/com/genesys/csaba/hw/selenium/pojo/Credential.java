package com.genesys.csaba.hw.selenium.pojo;

public class Credential {
    private final String username;
    private final String password;

    public Credential() {
        this.username = null;
        this.password = null;
    }

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
