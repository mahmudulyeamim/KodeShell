package com.example.kodeshell;

public class Codeforces_Contest {
    private String name;
    private long startTimeSeconds;
    private int durationSeconds;

    public Codeforces_Contest(String name, long startTimeSeconds, int durationSeconds) {
        this.name = name;
        this.startTimeSeconds = startTimeSeconds;
        this.durationSeconds = durationSeconds;
    }

    public String getName() {
        return name;
    }

    public long getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

}
