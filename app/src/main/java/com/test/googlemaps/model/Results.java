package com.test.googlemaps.model;

public class Results {

    private String name;
    private String vicinity;
    private double storeLat;
    private double storeLng;


    public Results(){
    }

    public Results(String name, String vicinity,double storeLat, double storeLng) {
        this.name = name;
        this.vicinity = vicinity;
        this.storeLat = storeLat;
        this.storeLng = storeLng;
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
