package com.company.model;

public class Todo {
    public int userId;
    public int id;
    public String title;
    public String completed;

    public Todo() {
    }

    @Override
    public String toString() {
        return "Todo{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }
}
