package com.lambton.capstone_wic_fitandfine.models;

public class Facts {

        private int levels;
        private int activity;
        private int description;
        int image;


        public Facts(int levels, int activity, int description, int image) {
            this.levels = levels;
            this.activity = activity;
            this.description = description;
            this.image = image;
        }

        public int getLevels()
        {
            return levels;
        }

        public void setLevels(int levels) {
            this.levels = levels;
        }

        public int getActivity() {

            return activity;
        }

        public void setActivity(int activity) {

            this.activity = activity;
        }

        public int getDescription() {

            return description;
        }

        public void setDescription(int description) {
            this.description = description;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }


    }

