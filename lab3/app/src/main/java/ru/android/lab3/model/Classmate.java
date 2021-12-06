package ru.android.lab3.model;

public class Classmate {

    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String date_create;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDateCreate() {
        return date_create;
    }

    public void setDateCreate(String date_create) {
        this.date_create = date_create;
    }
}
