package com.lambton.capstone_wic_fitandfine.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingsData implements Parcelable {

    @SerializedName("pushNotificationEnabled")
    @Expose
    private int pushNotificationEnabled;

    @SerializedName("inboxNotificationEnabled")
    @Expose
    private int inboxNotificationEnabled;

    @SerializedName("emailNotificationEnabled")
    @Expose
    private int emailNotificationEnabled;

    @SerializedName("smsNotificationEnabled")
    @Expose
    private int smsNotificationEnabled;

    public int getPushNotificationEnabled() {
        return pushNotificationEnabled;
    }

    public void setPushNotificationEnabled(int pushNotificationEnabled) {
        this.pushNotificationEnabled = pushNotificationEnabled;
    }

    public int getInboxNotificationEnabled() {
        return inboxNotificationEnabled;
    }

    public void setInboxNotificationEnabled(int inboxNotificationEnabled) {
        this.inboxNotificationEnabled = inboxNotificationEnabled;
    }

    public int getEmailNotificationEnabled() {
        return emailNotificationEnabled;
    }

    public void setEmailNotificationEnabled(int emailNotificationEnabled) {
        this.emailNotificationEnabled = emailNotificationEnabled;
    }

    public int getSmsNotificationEnabled() {
        return smsNotificationEnabled;
    }

    public void setSmsNotificationEnabled(int smsNotificationEnabled) {
        this.smsNotificationEnabled = smsNotificationEnabled;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    @SerializedName("activeStatus")
    @Expose
    private String activeStatus;

    @SerializedName("measurementType")
    @Expose
    private String measurementType;

    @SerializedName("defaultValue")
    @Expose
    private boolean defaultValue;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pushNotificationEnabled);
        dest.writeInt(this.emailNotificationEnabled);
        dest.writeInt(this.inboxNotificationEnabled);
        dest.writeInt(this.smsNotificationEnabled);
        dest.writeString(this.activeStatus);
        dest.writeInt(this.defaultValue?1:0);
        dest.writeString(this.measurementType);

    }

    public SettingsData() {
    }

    private SettingsData(Parcel in) {
        this.pushNotificationEnabled = in.readInt();
        this.emailNotificationEnabled = in.readInt();
        this.inboxNotificationEnabled = in.readInt();
        this.smsNotificationEnabled = in.readInt();
        this.activeStatus = in.readString();
        this.defaultValue = in.readInt() == 1;
        this.measurementType = in.readString();

    }


    public static final Parcelable.Creator<SettingsData> CREATOR = new Parcelable.Creator<SettingsData>() {
        @Override
        public SettingsData createFromParcel(Parcel source) {
            return new SettingsData(source);
        }

        @Override
        public SettingsData[] newArray(int size) {
            return new SettingsData[size];
        }
    };
}
