package com.lambton.capstone_wic_fitandfine.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PainArea implements Serializable {
    private boolean callFromCalendar;


    public static final String TAG="PAIN_AREA";

    @SerializedName("painArea")
    @Expose
    private String painArea;

    public boolean isCallFromCalendar() {
        return callFromCalendar;
    }

    public void setCallFromCalendar(boolean callFromCalendar) {
        this.callFromCalendar = callFromCalendar;
    }

    @SerializedName("painAreaId")

    @Expose
    private Integer painAreaId;
    @SerializedName("painLevel")
    @Expose
    private Integer painLevel;
    @SerializedName("painId")
    @Expose
    private Integer painId;
    @SerializedName("medication")
    @Expose
    private String medication;
    @SerializedName("treatment")
    @Expose
    private String treatment;
    @SerializedName("trackStatus")
    @Expose
    private String trackStatus;
    @SerializedName("painCreatDateTime")
    @Expose
    private long painCreatDateTime;

    public String getPainArea() {
        return painArea;
    }

    public void setPainArea(String painArea) {
        this.painArea = painArea;
    }

    public Integer getPainAreaId() {
        return painAreaId;
    }

    public void setPainAreaId(Integer painAreaId) {
        this.painAreaId = painAreaId;
    }

    public Integer getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(Integer painLevel) {
        this.painLevel = painLevel;
    }

    public Integer getPainId() {
        return painId;
    }

    public void setPainId(Integer painId) {
        this.painId = painId;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    public long getPainCreatDateTime() {
        return painCreatDateTime;
    }

    public void setPainCreatDateTime(long painCreatDateTime) {
        this.painCreatDateTime = painCreatDateTime;
    }

}

