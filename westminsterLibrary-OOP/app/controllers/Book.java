package controllers;

import java.util.ArrayList;

//this is a child class of LibraryItem (inheritance)
public class Book extends LibraryItem{

    //declaring variables
    private ArrayList<String>authorList;
    private String publisher;
    private int numOfPages;

    //constructor
    public Book(ArrayList<String> authorList,String publisher,int numOfPages,String isbn,String title,
                String sector,String publicationDate,DateTime burrowedDate,DateTime burrowedTime,Reader currentReader){
        super(isbn,title,sector,publicationDate,burrowedDate,burrowedTime,currentReader);
        this.authorList=authorList;
        this.publisher=publisher;
        this.numOfPages=numOfPages;
    }


    //getters and setters
    public ArrayList<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(ArrayList<String> authorList) {
        this.authorList = authorList;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }
}
