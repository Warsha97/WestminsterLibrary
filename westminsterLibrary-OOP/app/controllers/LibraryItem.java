package controllers;

//LibraryItem abstract class(abstraction)
public abstract class LibraryItem {

    //declaring private variables(encapsulation)
    private String isbn;
    private String title;
    private String sector;
    private String publicationDate;
    private DateTime burrowedDate;
    private DateTime burrowedTime;
    private Reader currentReader;

    //constructor
    public LibraryItem(String isbn, String title, String sector, String publicationDate, DateTime burrowedDate,
                       DateTime burrowedTime,Reader currentReader){
        this.isbn=isbn;
        this.title=title;
        this.sector=sector;
        this.publicationDate=publicationDate;
        this.burrowedDate=burrowedDate;
        this.burrowedTime=burrowedTime;
        this.currentReader=currentReader;

    }

    //getters for all attributes
    //setters for only burrowedDate, burrowedTime and currentReader
    public String getIsbn() {return isbn;}

    public String getTitle() {return title;}

    public String getSector() {return sector;}

    public String getPublicationDate() {return publicationDate;}

    public DateTime getBurrowedDate() {
        return burrowedDate;
    }

    public void setBurrowedDate(DateTime burrowedDate) {
        this.burrowedDate = burrowedDate;
    }

    public DateTime getBurrowedTime() {
        return burrowedTime;
    }

    public void setBurrowedTime(DateTime burrowedTime) {
        this.burrowedTime = burrowedTime;
    }

    public Reader getCurrentReader() {
        return currentReader;
    }

    public void setCurrentReader(Reader currentReader) {
        this.currentReader = currentReader;
    }
}
