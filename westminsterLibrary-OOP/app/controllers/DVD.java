package controllers;

import java.util.ArrayList;


//this is a child class of LibraryItem(inheritance)
public class DVD extends LibraryItem {

    //declaring variables
    private ArrayList<String>languages;
    private ArrayList<String>subtitles;
    private String producer;
    private ArrayList<String>actors;

    //constructor
    public DVD(ArrayList<String>languages,ArrayList<String>subtitles,String producer,ArrayList<String>actors,
               String isbn,String title,String sector,String publicationDate,DateTime burrowedDate,DateTime burrowedTime,
               Reader currentReader){
        super(isbn,title,sector,publicationDate,burrowedDate,burrowedTime,currentReader);
        this.languages=languages;
        this.subtitles=subtitles;
        this.producer=producer;
        this.actors=actors;
    }

    //getters and setters
    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public ArrayList<String> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(ArrayList<String> subtitles) {
        this.subtitles = subtitles;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }
}
