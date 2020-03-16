package com.example.mynews;

public class news {
    private String title, date, section, imageUrl;

    public news(String title, String date, String section, String imageUrl){
        this.title = title;
        this.date = date;
        this.section = section;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
