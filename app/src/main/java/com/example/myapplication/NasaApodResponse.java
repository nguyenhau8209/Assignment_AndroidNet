package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class NasaApodResponse {
    @SerializedName("url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    // Các getters và setters cho imageUrl
}

