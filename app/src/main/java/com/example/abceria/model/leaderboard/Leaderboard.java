package com.example.abceria.model.leaderboard;

import com.example.abceria.model.user.User;

public class Leaderboard {
    private User user;
    private Integer score;

    public Leaderboard(User user, Integer score) {
        this.user = user;
        this.score = score;
    }

    public Leaderboard(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
