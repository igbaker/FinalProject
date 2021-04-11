package com.example.finalproject.rss;

public class RssItem {
    private final Long id;
    String title;
    String description;
    String link;
    String date;

    //item contructors
    public RssItem() {
        this.id = null;
    }

    public RssItem(Long id) {
        this.id = id;
    }

    public RssItem(String title, String link) {
        this.id = Long.parseLong(null);
        this.title = title;
        this.link = link;
    }

    public RssItem(long id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    public RssItem(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public RssItem(long id, RssItem favourite) {
        this.id = id;
        this.title = favourite.getTitle();
        this.link = favourite.getLink();
    }

    //getters and setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate(){
        return date;
    }

    public String getLink() {
        return link;
    }

}
