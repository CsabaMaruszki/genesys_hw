package com.genesys.csaba.hw.api.pojo;

public class User {
    private final String name;
    private final String email;

    public User(){
        this.name=null;
        this.email=null;
    }
    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
