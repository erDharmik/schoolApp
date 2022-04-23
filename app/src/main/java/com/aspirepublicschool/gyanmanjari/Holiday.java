package com.aspirepublicschool.gyanmanjari;

class Holiday {
    String date,events;

    public Holiday(String date, String events) {
        this.date = date;
        this.events = events;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }
}
