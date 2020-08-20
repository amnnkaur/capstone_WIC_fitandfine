package com.lambton.capstone_wic_fitandfine.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PainSubCategory implements Serializable {

    @SerializedName("subCatg")
    @Expose
    private String subCatg;
    @SerializedName("pid")
    @Expose
    private Integer pid;

    public String getSubCatg() {
        return subCatg;
    }

    public void setSubCatg(String subCatg) {
        this.subCatg = subCatg;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

}