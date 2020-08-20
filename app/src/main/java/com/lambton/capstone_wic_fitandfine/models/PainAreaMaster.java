package com.lambton.capstone_wic_fitandfine.models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PainAreaMaster implements Serializable {

    @SerializedName("painCategory")
    @Expose
    private String painCategory;
    @SerializedName("painSubCategories")
    @Expose
    private List<PainSubCategory> painSubCategories = null;

    public String getPainCategory() {
        return painCategory;
    }

    public void setPainCategory(String painCategory) {
        this.painCategory = painCategory;
    }

    public List<PainSubCategory> getPainSubCategories() {
        return painSubCategories;
    }

    public void setPainSubCategories(List<PainSubCategory> painSubCategories) {
        this.painSubCategories = painSubCategories;
    }

}