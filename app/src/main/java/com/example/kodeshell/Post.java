package com.example.kodeshell;

public class Post {
    String id, userID;
    String userName;
    String time;
    String content;
    int upVote;
    int downVote;
    int avatarid;

    Post(String id, String userID, String userName, String time, String content, int upVote, int downVote, int avatarID){
        this.id = id;
        this.userID = userID;
        this.userName = userName;
        this.time = time;
        this.content = content;
        this.upVote = upVote;
        this.downVote = downVote;
        this.avatarid = avatarID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getAvatarid() {
        return avatarid;
    }

    public void setAvatarid(int avatarid) {
        this.avatarid = avatarid;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

}
