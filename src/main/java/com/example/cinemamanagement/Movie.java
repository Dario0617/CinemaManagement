package com.example.cinemamanagement;

import java.time.LocalDate;

public class Movie {
    private int id;
    private String name;
    private String details;
    private String gender;
    private LocalDate releaseDate;
    private int duration;

    public Movie(int id, String name, String details, String gender, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.gender = gender;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
