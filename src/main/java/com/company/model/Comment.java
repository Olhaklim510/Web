package com.company.model;

public class Comment {
    public int postId;
    public int id;
    public String name;
    public String email;
    public String body;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "postId=" + postId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
