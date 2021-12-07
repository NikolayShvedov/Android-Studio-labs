package ru.android.lab41.models;

public class MusicsRadioRequest {

    private int id;
    private String author_name;
    private String music_name;
    private String date_create;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return author_name;
    }

    public void setAuthorName(String author_name) {
        this.author_name = author_name;
    }

    public String getMusicName() {
        return music_name;
    }

    public void setMusicName(String music_name) {
        this.music_name = music_name;
    }

    public String getDateCreate() {
        return date_create;
    }

    public void setDateCreate(String date_create) {
        this.date_create = date_create;
    }
}
