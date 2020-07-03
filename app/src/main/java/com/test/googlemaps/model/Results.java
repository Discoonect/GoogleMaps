package com.test.googlemaps.model;

public class Results {

    private String name;
    private String vicinity;

    public Results(){
    }

    public Results(String name, String vicinity) {
        this.name = name;
        this.vicinity = vicinity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
