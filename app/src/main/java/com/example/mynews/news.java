package com.example.mynews;

public class news {
    private String title, imageUrl, date, section;

    public news(String title, String imageUrl, String date, String section){
        this.title = title;
        this.imageUrl = imageUrl;
        this.date = section;
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }
}
