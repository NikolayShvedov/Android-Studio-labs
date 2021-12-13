package ru.android.lab6.models;

public class Notification {

    private int id;
    private String title;
    private String notification;
    private String date_notify;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDateNotify() {
        return date_notify;
    }

    public void setDateNotify(String date_notify) {
        this.date_notify = date_notify;
    }
}
