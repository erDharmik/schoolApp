package com.aspirepublicschool.gyanmanjari.Test;

public class TestTimer {
    String test_id,time;

    public TestTimer(String test_id, String time) {
        this.test_id = test_id;
        this.time = time;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
