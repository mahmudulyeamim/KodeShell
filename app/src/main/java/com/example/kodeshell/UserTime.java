package com.example.kodeshell;

public class UserTime {
    User user;
    long timeStamp;

    public UserTime(User user, long timeStamp) {
        this.user = user;
        this.timeStamp = timeStamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
