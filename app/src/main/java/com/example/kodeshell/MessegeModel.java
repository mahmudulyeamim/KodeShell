package com.example.kodeshell;

public class MessegeModel {
    String message;
    String senderUID;
    long timeStamp;

    public MessegeModel() {
    }

    public MessegeModel(String message, String senderUID, long timeStamp) {
        this.message = message;
        this.senderUID = senderUID;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}