package com.example.kodeshell;

public class CommentDetails {
    private String username, comment, time;
    private int avatar;

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
