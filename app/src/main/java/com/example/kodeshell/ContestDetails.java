package com.example.kodeshell;

public class ContestDetails {
    private int contestIcon;
    private String contestName, contestDate, contestTime, contestDuration;

    public int getContestIcon() {
        return contestIcon;
    }

    public String getContestName() {
        return contestName;
    }

    public String getContestDate() {
        return contestDate;
    }

    public String getContestTime() {
        return contestTime;
    }

    public String getContestDuration() {
        return contestDuration;
    }

    public void setContestIcon(int contestIcon) {
        this.contestIcon = contestIcon;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public void setContestDate(String contestDate) {
        this.contestDate = contestDate;
    }

    public void setContestTime(String contestTime) {
        this.contestTime = contestTime;
    }

    public void setContestDuration(String contestDuration) {
        this.contestDuration = contestDuration;
    }
}
