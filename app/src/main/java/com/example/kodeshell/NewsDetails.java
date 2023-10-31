package com.example.kodeshell;

import java.util.List;
import java.util.Objects;

public class NewsDetails {
    String newsid;
    private String time, post;

    private List<CommentDetails> comments;

    public List<CommentDetails> getComments() {
        return comments;
    }

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getTime() {
        return time;
    }

    public String getPost() {
        return post;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setComments(List<CommentDetails> comments) {
        this.comments = comments;
    }
}

