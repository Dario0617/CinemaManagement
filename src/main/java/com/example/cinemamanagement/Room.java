package com.example.cinemamanagement;

public class Room {
    private int Id;
    private String Name;
    private int Capacity;

    public Room(int id, String name, int capacity) {
        this.Id = id;
        this.Name = name;
        this.Capacity = capacity;
    }

    public int GetId() {
        return Id;
    }

    public String GetName() {
        return Name;
    }

    public int GetCapacity() {
        return Capacity;
    }
}
