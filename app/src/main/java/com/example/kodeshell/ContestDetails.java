package com.example.kodeshell;

public class ContestDetails {
    private int contestIcon;
    private String contestName, contestDate, contestTime, contestDuration,url,site,sttime,edtime;
    private boolean isalarm;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSttime() {
        return sttime;
    }

    public void setSttime(String sttime) {
        this.sttime = sttime;
    }

    public String getEdtime() {
        return edtime;
    }

    public void setEdtime(String edtime) {
        this.edtime = edtime;
    }

    public boolean isIsalarm() {
        return isalarm;
    }

    public void setIsalarm(boolean isalarm) {
        this.isalarm = isalarm;
    }
}
