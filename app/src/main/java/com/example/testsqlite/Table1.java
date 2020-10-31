package com.example.testsqlite;

public class Table1 {
    String userid;

    public Table1() {
    }

    public Table1(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    String password;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
