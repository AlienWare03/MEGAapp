package com.projectmega.menu;

/**
 * Created by Justin on 1/16/2016.
 */
public class AccountList {

    //Data variables
    private String ID;
    private String user;
    private String first;
    private String middle;
    private String last;
    private String age;
    private String section;
    private String email;

    public String getUser(){
        return user;
    }
    public void setUser(String user){
        this.user = user;
    }
    public String getID(){
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirst(){
        return first;
    }
    public void setFirst(String first) {
        this.first = first;
    }
    public String getMiddle(){
        return middle;
    }
    public void setMiddle(String middle) {
        this.middle=middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }

    public String getEmailadd() {
            return  email;
    }

    public void setEmailadd(String email) {
        this.email = email;
    }
}
