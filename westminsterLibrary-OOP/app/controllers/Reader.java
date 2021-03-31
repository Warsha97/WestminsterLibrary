package controllers;

//Reader class holds information about the following attributes
public class Reader {

    //declaring private variables(encapsulation)
    private int readerID;
    private String readerName;
    private String mobileNumber;
    private String email;

    //constructor
    public Reader(int readerId,String readerName,String mobileNumber,String email){
        this.readerID=readerId;
        this.readerName=readerName;
        this.mobileNumber=mobileNumber;
        this.email=email;
    }

    //getters and setters
    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
