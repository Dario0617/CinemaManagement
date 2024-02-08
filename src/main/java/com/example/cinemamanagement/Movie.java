package com.example.cinemamanagement;

import java.time.LocalDate;

public class Movie {
    private int Id;
    private String Name;
    private String Details;
    private String Gender;
    private LocalDate ReleaseDate;
    private int Duration;

    public Movie(int id, String name, String details, String gender, LocalDate releaseDate, int duration) {
        this.Id = id;
        this.Name = name;
        this.Details = details;
        this.Gender = gender;
        this.ReleaseDate = releaseDate;
        this.Duration = duration;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDetails() {
        return Details;
    }

    public String getGender() {
        return Gender;
    }

    public LocalDate getReleaseDate() {
        return ReleaseDate;
    }

    public int getDuration() {
        return Duration;
    }
}
