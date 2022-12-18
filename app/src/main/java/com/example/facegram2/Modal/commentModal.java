package com.example.facegram2.Modal;

public class commentModal {
    String date,time,uid,userImage,usermsg,username;

    public commentModal() {
    }

    public commentModal(String date, String time, String uid, String userImage, String usermsg, String username) {
        this.date = date;
        this.time = time;
        this.uid = uid;
        this.userImage = userImage;
        this.usermsg = usermsg;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUsermsg() {
        return usermsg;
    }

    public void setUsermsg(String usermsg) {
        this.usermsg = usermsg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
