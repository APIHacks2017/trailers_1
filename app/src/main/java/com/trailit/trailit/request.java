package com.trailit.trailit;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by theMachine on 17-06-2017.
 */

public class request {

    LatLng loc;
    @SerializedName("lat")
    Double lat;
    @SerializedName("long")
    Double lon;
    @SerializedName("pointType")
    String[] pointType;
    @SerializedName("stTime")
    String stTime;
    @SerializedName("edTime")
    String edTime;


    public LatLng getLoc() {
        return loc;
    }

    public void setLoc(LatLng loc) {
        this.loc = loc;
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

    public String[] getPointType() {
        return pointType;
    }

    public void setPointType(String[] pointType) {
        this.pointType = pointType;
    }

    public String getStTime() {
        return stTime;
    }

    public void setStTime(String stTime) {
        this.stTime = stTime;
    }

    public String getEdTime() {
        return edTime;
    }

    public void setEdTime(String edTime) {
        this.edTime = edTime;
    }
}
