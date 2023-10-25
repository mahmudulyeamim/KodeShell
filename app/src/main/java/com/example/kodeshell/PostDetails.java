package com.example.kodeshell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostDetails {
    private String id, username, imageURL, time, post;
    private int avatar;
    private int upVoteIcon;
    private int downVoteIcon;
    private int upVote, downVote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<CommentDetails> comments;

    public int getAvatar() {
        return avatar;
    }

    public int getUpVoteIcon() {
        return upVoteIcon;
    }

    public int getDownVoteIcon() {
        return downVoteIcon;
    }

    public String getUsername() {
        return username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTime() {
        return time;
    }

    public String getPost() {
        return post;
    }

    public int getUpVote() {
        return upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public List<CommentDetails> getComments() {
        return comments;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

    public void setComments(List<CommentDetails> comments) {
        this.comments = comments;
    }

    public void setUpVoteIcon(int upVoteIcon) {
        this.upVoteIcon = upVoteIcon;
    }

    public void setDownVoteIcon(int downVoteIcon) {
        this.downVoteIcon = downVoteIcon;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
