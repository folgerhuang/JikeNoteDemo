package com.jkxy.jikenotedemo;

import java.io.Serializable;

/**
 * Created by shiyu on 6/7/2016.
 */
public class NotificationBean implements Serializable {
    private long id;
    private int hour;
    private String event;

    public NotificationBean(long id, int hour, String event) {
        this.id = id;
        this.hour = hour;
        this.event = event;
    }

    public NotificationBean() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
