package com.lambton.capstone_wic_fitandfine.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Muscle implements Parcelable {

    private String name;
    private List<Exercise> exercises;
    private int id;

    public Muscle() {
    }

    public Muscle(int id, String name, List<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
    }

    protected Muscle(Parcel in) {
        name = in.readString();
        exercises = in.createTypedArrayList(Exercise.CREATOR);
        id = in.readInt();
    }

    public static final Creator<Muscle> CREATOR = new Creator<Muscle>() {
        @Override
        public Muscle createFromParcel(Parcel in) {
            return new Muscle(in);
        }

        @Override
        public Muscle[] newArray(int size) {
            return new Muscle[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeList(exercises);
    }

    @Override
    public String toString() {
        return "Muscle{" +
                "name='" + name + '\'' +
                ", exercises=" + exercises +
                ", id=" + id +
                '}';
    }
}