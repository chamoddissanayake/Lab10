package com.test.lab10;

public class User {
    private long itemId;
    private String nameFromDB, passwordFromDB, typeFromDB;

    public User(long itemId, String nameFromDB, String passwordFromDB, String typeFromDB) {
        this.itemId = itemId;
        this.nameFromDB = nameFromDB;
        this.passwordFromDB = passwordFromDB;
        this.typeFromDB = typeFromDB;
    }

    public User() {
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getNameFromDB() {
        return nameFromDB;
    }

    public void setNameFromDB(String nameFromDB) {
        this.nameFromDB = nameFromDB;
    }

    public String getPasswordFromDB() {
        return passwordFromDB;
    }

    public void setPasswordFromDB(String passwordFromDB) {
        this.passwordFromDB = passwordFromDB;
    }

    public String getTypeFromDB() {
        return typeFromDB;
    }

    public void setTypeFromDB(String typeFromDB) {
        this.typeFromDB = typeFromDB;
    }
}
