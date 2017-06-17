package com.trailit.trailit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by theMachine on 17-06-2017.
 */

public class point {
    @SerializedName("name")
    String pName;
    @SerializedName("type")
    String pType;
    @SerializedName("lat")
    Double lat;
    @SerializedName("long")
    Double lon;

    public point(String pName, String pType, Double lat, Double lon) {
        this.pName = pName;
        this.pType = pType;
        this.lat = lat;
        this.lon = lon;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
