package com.test.googlemaps.model;

import java.io.Serializable;

public class Results implements Serializable {

    private String name;
    private String vicinity;
    private double storeLat;
    private double storeLng;


    public Results(){
    }

    public double getStoreLat() {
        return storeLat;
    }

    public void setStoreLat(double storeLat) {
        this.storeLat = storeLat;
    }

    public double getStoreLng() {
        return storeLng;
    }

    public void setStoreLng(double storeLng) {
        this.storeLng = storeLng;
    }

    public Results(String name, String vicinity, double storeLat, double storeLng) {
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
