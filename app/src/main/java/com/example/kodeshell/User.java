package com.example.kodeshell;

public class User {
    String mail,firstName, lastName, password, userId,lastMessage, status, codeforcesuname, atcoderuname, leetcodeuname, phonenumber;
    int avatarid, postcount, contribution;

    public  User(){}

    public User(String userId, String firstName, String lastName, String mail, String password, String status, int avatarid, String codeforcesuname, String atcoderuname, String leetcodeuname, String phonenumber, int postcount, int contribution) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.status = status;
        this.avatarid = avatarid;
        this.codeforcesuname = codeforcesuname;
        this.atcoderuname = atcoderuname;
        this.leetcodeuname = leetcodeuname;
        this.phonenumber = phonenumber;
        this.postcount = 0;
        this.contribution = 0;
    }

    public int getPostcount() {
        return postcount;
    }

    public void setPostcount(int postcount) {
        this.postcount = postcount;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCodeforcesuname() {
        return codeforcesuname;
    }

    public void setCodeforcesuname(String codeforcesuname) {
        this.codeforcesuname = codeforcesuname;
    }

    public String getAtcoderuname() {
        return atcoderuname;
    }

    public void setAtcoderuname(String atcoderuname) {
        this.atcoderuname = atcoderuname;
    }

    public String getLeetcodeuname() {
        return leetcodeuname;
    }

    public void setLeetcodeuname(String leetcodeuname) {
        this.leetcodeuname = leetcodeuname;
    }

    public int getAvatarid() {
        return avatarid;
    }

    public void setAvatarid(int avatarid) {
        this.avatarid = avatarid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}