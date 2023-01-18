package com.example.firebasetask;

public class UserData {
   public String id;
    public String name;
    public   String domain;

    public UserData() {
    }

    public UserData(String id, String name, String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }
}
