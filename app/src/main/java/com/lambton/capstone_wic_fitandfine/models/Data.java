package com.lambton.capstone_wic_fitandfine.models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("painAreasMaster")
    @Expose
    private List<PainAreaMaster> painAreasMaster = null;
    @SerializedName("painAreas")
    @Expose
    private ArrayList<PainArea> painAreas = null;

    public List<PainAreaMaster> getPainAreasMaster() {
        return painAreasMaster;
    }

    public void setPainAreasMaster(List<PainAreaMaster> painAreasMaster) {
        this.painAreasMaster = painAreasMaster;
    }

    public ArrayList<PainArea> getPainAreas() {
        return painAreas;
    }

    public void setPainAreas(ArrayList<PainArea> painAreas) {
        this.painAreas = painAreas;
    }

}