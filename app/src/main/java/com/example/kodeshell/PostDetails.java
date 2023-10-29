package com.example.kodeshell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostDetails {
    private String id, username, time, post;
    private int avatar;
    private int upVoteIcon;
    private int downVoteIcon;
    private int upVote, downVote;
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PostDetails that = (PostDetails) obj;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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
