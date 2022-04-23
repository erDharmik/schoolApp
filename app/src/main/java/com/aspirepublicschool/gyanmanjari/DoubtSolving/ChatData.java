package com.aspirepublicschool.gyanmanjari.DoubtSolving;

public class ChatData {
    String message,time,role,target;

    public ChatData(String message, String time, String role, String target) {
        this.message = message;
        this.time = time;
        this.role = role;
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
