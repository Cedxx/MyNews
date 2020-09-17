package com.example.mynews.Models;

public class News {
    private String title, date, section, imageUrl, articleUrl;

    public News(String title, String date, String section, String imageUrl, String articleUrl){
        this.title = title;
        this.date = date;
        this.section = section;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
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

    public String getArticleUrl(){
        return articleUrl;
    }
}
