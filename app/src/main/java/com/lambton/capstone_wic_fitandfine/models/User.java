package com.lambton.capstone_wic_fitandfine.models;

import android.text.TextUtils;

import java.io.Serializable;

enum HeightUOM {
    ft,
    cm,
    UNKNOWN
}

enum WeightUOM {
    kg,
    lbs,
    UNKNOWN
}

public class User implements Serializable {
    private boolean isDoctor;

    private String thumbURL;
    private String userId;
    private String providerId;
    private String cookie;
    private String username;
    private String session;
    private String firstName;
    private String lastName;
    private String formattedName;
    private String location;
    private String height;
    private String weight;
    private String dob;
    private String emailAddress;
    private String contactNumber;
    private String password;


    private Gender gender;
    private HeightUOM heightUOM;
    private WeightUOM weightUOM;

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public void setDoctor(String isDoctor) {
        this.isDoctor = !TextUtils.isEmpty(isDoctor) && isDoctor.equalsIgnoreCase("Y");
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getGenderString() {
        if (gender.equals(Gender.MALE))
            return "Male";
        else if (gender.equals(Gender.MALE))
            return "Female";
        else
            return null;
    }


    public void setGender(String genderString) {
        if (!TextUtils.isEmpty(genderString)) {
            if (genderString.equalsIgnoreCase("M") || genderString.equalsIgnoreCase("Male"))
                this.gender = Gender.MALE;
            else
                //we have confirmation that app will only support two genders.
                this.gender = Gender.FEMALE;
        } else {
            this.gender = Gender.UNKNOWN;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public HeightUOM getHeightUOM() {
        return heightUOM;
    }

    public String getHeightUOMString() {
        if (heightUOM.equals(HeightUOM.ft))
            return "ft";
        else if (heightUOM.equals(HeightUOM.cm))
            return "cm";
        else
            return null;
    }

    public void setHeightUOM(HeightUOM heightUOM) {
        this.heightUOM = heightUOM;
    }

    public void setHeightUOM(String heightUOMString) {
        if (!TextUtils.isEmpty(heightUOMString)) {
            if (heightUOMString.equalsIgnoreCase("ft"))
                this.heightUOM = HeightUOM.ft;
            else
                this.heightUOM = HeightUOM.cm;
        } else {
            this.heightUOM = HeightUOM.UNKNOWN;
        }
    }

    public WeightUOM getWeightUOM() {
        return weightUOM;
    }

    public String getWeightUOMString() {
        if (weightUOM.equals(WeightUOM.kg))
            return "kg";
        else if (weightUOM.equals(WeightUOM.lbs))
            return "lbs";
        else return null;
    }

    public void setWeightUOM(WeightUOM weightUOM) {
        this.weightUOM = weightUOM;
    }

    public void setWeightUOM(String weightUOMString) {
        if (!TextUtils.isEmpty(weightUOMString)) {
            if (weightUOMString.equalsIgnoreCase("kg"))
                this.weightUOM = WeightUOM.kg;
            else
                this.weightUOM = WeightUOM.lbs;
        } else {
            this.weightUOM = WeightUOM.UNKNOWN;
        }
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFormattedName() {
        String name = "";
        if (!TextUtils.isEmpty(getFirstName())) {
            name += getFirstName() + " ";
        }
        if (!TextUtils.isEmpty(getLastName())) {
            name += getLastName();
        }
        return name;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

