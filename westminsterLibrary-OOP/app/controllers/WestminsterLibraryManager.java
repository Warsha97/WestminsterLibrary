package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import play.libs.Json;
import play.mvc.*;
import scala.Int;

import java.sql.*;
import java.util.ArrayList;

interface LibraryManager{

    Result addNewBook();
  Result addNewDVD();
  Result deleteBook();
  Result deleteDVD();
  Result displayBooks() throws SQLException;
  Result burrowBook();
  Result burrowDVD();
  Result returnBook();
  Result ReturnDVD();
  Result registerReader();
  Result generateReport() throws SQLException;


}

public class WestminsterLibraryManager extends Controller implements LibraryManager  {




//providing implementation for the addNewBook method
    @Override
    public Result addNewBook(){

    //getting db connection using the DBConnection class
      DBConnection connection=new DBConnection();
      Connection con=connection.connectingDB();

        String bookInputs=request().body().asText();//taking request body content to a variable

        //all the inputs are bundled in one string variable
        //splitting the string.
        String[]bookDetail=new String[7];
        bookDetail=bookInputs.split("@");
        String isbn=bookDetail[0];
        String title=bookDetail[1];
        String sector=bookDetail[2];
        String[]authors=bookDetail[3].split(",");
        String publisher=bookDetail[4];
        String publicationDate=bookDetail[5];
        int numOfPages=Integer.parseInt(bookDetail[6]);
        DateTime burrowedDate=null;
        DateTime burrowedTime=null;
        Reader currentReader=null;


        ArrayList<String>listOfAuthors=new ArrayList<>();

        for(int i=0;i<authors.length;i++){
            listOfAuthors.add(authors[i]);

        }

        //creating a Book object
        Book book=new Book(listOfAuthors,publisher,numOfPages,isbn,title,sector,publicationDate,burrowedDate,burrowedTime,currentReader);

        //the following code is to get the row count.
        //as to the requirement, only 100 books can be stored.
        int rowCount=0;
        try {
            Statement st=con.createStatement();
            ResultSet resultSet=st.executeQuery("select count(*) from books");
            while (resultSet.next()) {
                rowCount= resultSet.getInt(1);
            }

            System.out.println("Row Count is : "+rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //if there is less than 100 books in the table new book query is executed
        if(rowCount<100) {

            try {
                String savebookquery = "INSERT INTO books(isbn,title,sector,author,publisher,publicationDate,numOfPages,currentReaderId,burrowedDate,burrowedTime,returnDate,returnTime)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";


                PreparedStatement cmd = con.prepareStatement(savebookquery);//prepared statement interface defines the methods and properties that enable the java programme to send sql commands and receive data from data base

                cmd.setString(1, book.getIsbn());//setting the isbn of book object to the isbn column in the sql books table
                cmd.setString(2, book.getTitle());//setting the title of book object to the title column in the sql books table
                cmd.setString(3, book.getSector());//setting the sector of book object to the sector column in the sql books table
                cmd.setString(4, bookDetail[3]);//setting the authors input value to the author column in the sql books table
                cmd.setString(5, book.getPublisher());//setting the publisher of book object to the publisher column in the sql books table
                cmd.setString(6, book.getPublicationDate());//setting the publicationDate of book object to the publicationDate column in the sql books table
                cmd.setInt(7, book.getNumOfPages());//setting the numOfPages of book object to the numOfPages column in the sql books table
                cmd.setInt(8, 9);//setting the currentReader of book object to the currentReaderId column in the sql books table
                cmd.setString(9, "");//setting the burrowedDate of book object to the burrowedDate column in the sql books table
                cmd.setString(10, "");//setting the burrowedTime of book object to the burrowedTime column in the sql books table
                cmd.setString(11, "");
                cmd.setString(12, "");

                cmd.execute();//executing the command query

                JsonNode jsonNode = Json.toJson(new AppSummary("NEW BOOK SAVED SUCCESSFULLY : "+bookInputs+"  AVAILABLE SLOTS : "+(100-(rowCount+1))));//if the query executes and save in DB, saved details will be displayed with a successful msg
                System.out.println(jsonNode);
                System.out.println(request().body().asText());//print the request body

                JsonNode json = request().body().asJson();
                System.out.println(json);

                return ok(jsonNode).as("application/json");

            } catch (SQLException e) {
                JsonNode jsonNode = Json.toJson(new AppSummary("Error while saving new book"));
                return ok(jsonNode).as("application/json");
            }

        }
        else{
            JsonNode jsonNode = Json.toJson(new AppSummary("NOT ENOUGH SPACE IN THE BOOK STORE"));
            return ok(jsonNode).as("application/json");
        }


}

//providing implementation of addNewDVD
   public Result addNewDVD(){

    //connecting to db
       DBConnection connection=new DBConnection();
       Connection con=connection.connectingDB();

        String dvdInputs=request().body().asText();//taking request body content to a variable



        //all the inputs are bundled in one string variable
        //splitting the string.
        String[]dvdDetail;
        dvdDetail=dvdInputs.split("@");
        String isbn=dvdDetail[0];
        String title=dvdDetail[1];
        String sector=dvdDetail[2];
        String producer=dvdDetail[3];
        String[]actors=dvdDetail[4].split(",");
        String publicationDate=dvdDetail[5];
        String[]languages=dvdDetail[6].split(",");
        String[]subtitles=dvdDetail[7].split(",");
        DateTime burrowedDate=null;
        DateTime burrowedTime=null;
        Reader currentReader=null;



        ArrayList<String>listOfActors=new ArrayList<>();
        ArrayList<String>listOfLanguages=new ArrayList<>();
        ArrayList<String>listOfSubtitles=new ArrayList<>();

        for(int i=0;i<actors.length;i++){
            listOfActors.add(actors[i]);
        }

        for(int i=0;i<languages.length;i++){

            listOfLanguages.add(languages[i]);
        }

        for(int i=0;i<subtitles.length;i++){
            listOfSubtitles.add(subtitles[i]);
        }

        //creating a DVD object
        DVD dvd=new DVD(listOfLanguages,listOfSubtitles,producer,listOfActors,isbn,title,sector,publicationDate,null,null,null);


        //the following code is to get the row count.
        //as to the requirement, only 50 DVDs can be stored.
        int rowCount=0;
        try {
            Statement st=con.createStatement();
            ResultSet resultSet=st.executeQuery("select count(*) from dvds");
            while (resultSet.next()) {
                rowCount= resultSet.getInt(1);
            }

            System.out.println("Row Count is : "+rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //if there is less than 50 DVDs in the table new dvd query is executed
        if(rowCount<50) {

            try {
                String savedvdquery = "INSERT INTO dvds(isbn,title,sector,producer,actors,publicationDate,languages,subtitles,currentReaderId,burrowedDate,burrowedTime,returnDate,returnTime)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement cmd = con.prepareStatement(savedvdquery);//prepared statement interface defines the methods and properties that enable the java programme to send sql commands and receive data from data base

                cmd.setString(1,dvd.getIsbn());
                cmd.setString(2,dvd.getTitle());
                cmd.setString(3,dvd.getSector());
                cmd.setString(4,dvd.getProducer());
                cmd.setString(5,dvdDetail[4]);
                cmd.setString(6,dvd.getPublicationDate());
                cmd.setString(7,dvdDetail[6]);
                cmd.setString(8,dvdDetail[7]);
                cmd.setInt(9,9);
                cmd.setString(10,"");
                cmd.setString(11,"");
                cmd.setString(12,"");
                cmd.setString(13,"");

                cmd.execute();//executing the command query

                JsonNode jsonNode = Json.toJson(new AppSummary("NEW DVD SAVED SUCCESSFULLY : "+dvdInputs+" AVAILABLE SLOTS : "+(50-(rowCount+1))+" MORE"));//if the query executes and save in DB, saved details will be displayed with a successful msg
                System.out.println(jsonNode);
                System.out.println(request().body().asText());//print the request body

                JsonNode json = request().body().asJson();
                System.out.println(json);

                return ok(jsonNode).as("application/json");


            } catch (SQLException e) {
                //if query excecution fails this error msg will be displayed
                JsonNode jsonNode = Json.toJson(new AppSummary("Error while saving new DVD"));
                return ok(jsonNode).as("application/json");
            }

        }
        else{
            //if there is no space to add a new DVD following msg will be diplyed in the UI
            JsonNode jsonNode = Json.toJson(new AppSummary("NOT ENOUGH SPACE IN THE DVD STORE"));
            return ok(jsonNode).as("application/json");
        }


    }


public Result showDeletingBook(){
    //setting the connection to the database using jdbc connectivity
    DBConnection connection=new DBConnection();
    Connection con=connection.connectingDB();

    String frontendIsbn=request().body().asText();//taking request body content to a variable

   String authors="";
   String isbn="";
   String title="";
   String sector="";
   String publisher="";


   String currentReaderId="";


    try {
        String selectquery="SELECT * FROM books WHERE isbn='"+frontendIsbn+"'";

        Statement statement = null;
        statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(selectquery);

        while(resultSet.next()){
            isbn=resultSet.getString("isbn");
            authors=resultSet.getString("author");
            title=resultSet.getString("title");
            sector=resultSet.getString("sector");
            publisher=resultSet.getString("publisher");
            currentReaderId=resultSet.getString("currentReaderId");
            }

            if(Integer.parseInt(currentReaderId)!=9){
               String[]negative={"","You can't Delete this Book.It is currently Burrowed","","",""};
                JsonNode jsonNode = Json.toJson(new ReturningDetails(negative));
                System.out.println("Cannot delete this book at the moment");
                return ok(jsonNode).as("application/json");

            }
            else{
            String[]positive={isbn,"Title : "+title,"Sector : "+sector,"Author : "+authors,"publisher : "+publisher};
                JsonNode jsonNode = Json.toJson(new ReturningDetails(positive));
                System.out.println(positive);
                return ok(jsonNode).as("application/json");
            }


    }
    catch (SQLException e) {
        e.printStackTrace();
        String[]error={""};
        JsonNode jsonNode = Json.toJson(new ReturningDetails(error));
        System.out.println(error);
        return ok(jsonNode).as("application/json");
    }

}


    public Result showDeletingDVD(){
        //setting the connection to the database using jdbc connectivity
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        String frontendIsbn=request().body().asText();//taking request body content to a variable

        String producer="";
        String isbn="";
        String title="";
        String sector="";
        String actors="";


        String currentReaderId="";


        try {
            String selectquery="SELECT * FROM dvds WHERE isbn='"+frontendIsbn+"'";

            Statement statement = null;
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(selectquery);

            while(resultSet.next()){
                isbn=resultSet.getString("isbn");
                producer=resultSet.getString("producer");
                title=resultSet.getString("title");
                sector=resultSet.getString("sector");
                actors=resultSet.getString("actors");
                currentReaderId=resultSet.getString("currentReaderId");
            }

            if(Integer.parseInt(currentReaderId)!=9){
                String[]negative={"","You can't Delete this DVD.It is currently Burrowed","","",""};
                JsonNode jsonNode = Json.toJson(new ReturningDetails(negative));
                System.out.println("Cannot delete this DVD at the moment");
                return ok(jsonNode).as("application/json");

            }
            else{
                String[]positive={isbn,"Title : "+title,"Sector : "+sector,"Producer : "+producer,"actors : "+actors};
                JsonNode jsonNode = Json.toJson(new ReturningDetails(positive));
                System.out.println(positive);
                return ok(jsonNode).as("application/json");
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
            String[]error={""};
            JsonNode jsonNode = Json.toJson(new ReturningDetails(error));
            System.out.println(error);
            return ok(jsonNode).as("application/json");
        }

    }


    //providing implementation for deletBook method
public Result deleteBook(){

    //setting the connection to the database using jdbc connectivity
    DBConnection connection=new DBConnection();
    Connection con=connection.connectingDB();

    //taking request body content to a variable
    String frontendIsbn=request().body().asText();


    //getting the current row count
    int rowCount=0;

    try {
        Statement st=con.createStatement();
        ResultSet resultSet=st.executeQuery("select count(*) from books");
        while (resultSet.next()) {
            rowCount= resultSet.getInt(1);
        }

        String deleteQuery = "DELETE FROM books WHERE isbn='" + frontendIsbn + "'";
        PreparedStatement cmd = con.prepareStatement(deleteQuery);
        cmd.execute();

        //after deleting if it is successful, row count will be one less and available slots will increse by 1
        String[]response={"Book Deleted Successfully!","Available slots : "+String.valueOf(100-(rowCount-1))};

        JsonNode jsonNode = Json.toJson(new ReturningDetails(response));
        System.out.println(response);
        return ok(jsonNode).as("application/json");

    }catch (SQLException e) {
        e.printStackTrace();
        //if deletion is uncussesful row count will not change
        String[]error={"Error While Deleting the Book","Available slots : "+String.valueOf(100-rowCount)};
        JsonNode jsonNode = Json.toJson(new ReturningDetails(error));
        System.out.println(error);
        return ok(jsonNode).as("application/json");
    }
}


//providing implementation foe deleteDVD method
    public Result deleteDVD(){

        //setting the connection to the database using jdbc connectivity
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        //taking request body content to a variable
        String frontendIsbn=request().body().asText();

        //getting current row count
        int rowCount=0;
        try {
            Statement st=con.createStatement();
            ResultSet resultSet=st.executeQuery("select count(*) from dvds");
            while (resultSet.next()) {
                rowCount= resultSet.getInt(1);
            }

            String deleteQuery = "DELETE FROM dvds WHERE isbn='" + frontendIsbn + "'";
            PreparedStatement cmd = con.prepareStatement(deleteQuery);
            cmd.execute();

            //if deletion in successful then one less row count and available slots add one
            String[]response={"DVD Deleted Successfully!","Available slots : "+String.valueOf(50-(rowCount-1))};

            JsonNode jsonNode = Json.toJson(new ReturningDetails(response));
            System.out.println(response);
            return ok(jsonNode).as("application/json");

        }catch (SQLException e) {
            e.printStackTrace();
            //if deletion is unsuccessful row count and available slots are not changed
            String[]error={"Error While Deleting the DVD","Available slots : "+String.valueOf(50-rowCount)};
            JsonNode jsonNode = Json.toJson(new ReturningDetails(error));
            System.out.println(error);
            return ok(jsonNode).as("application/json");
        }
    }



    @Override
    //providing impelementation for displayItems method
    public Result displayBooks() throws SQLException {

        //setting the connection to the database using jdbc connectivity
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        ArrayList<Object> itemList = new ArrayList<>();

        String query = "SELECT * FROM books";
        Statement statement = null;

            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            String availability="";

            //iterating through a while loop and creating book objects
        //and adding each obect to the list
            while (resultSet.next()) {

                Book tableBook = new Book(new ArrayList<>(), resultSet.getString("publisher"), resultSet.getInt("numOfPages"), resultSet.getString("isbn"),
                        resultSet.getString("title"), resultSet.getString("sector"), resultSet.getString("publicationDate"), new DateTime(0, 0, 0, 0, 0), new DateTime(0, 0, 0, 0, 0),
                        new Reader(resultSet.getInt("currentReaderId"), "Book", "", null));

                itemList.add(tableBook);
            }


        String querydvd = "SELECT * FROM dvds";
        Statement stmt = null;

        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(querydvd);

        //iterating through a while loop and creating DVD objects
        //and adding each object to the list
        while (rs.next()) {
            DVD tableDVD=new DVD(new ArrayList<>(),new ArrayList<>(),"",new ArrayList<>(),rs.getString("isbn"),
                    rs.getString("title"),rs.getString("sector"),"",new DateTime(0,0,0,0,0),
                    new DateTime(0,0,0,0,0),new Reader(rs.getInt("currentReaderId"),"DVD","",null));

            itemList.add(tableDVD);
        }



           //creating a Gson object to send the list to the UI
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String jsonObject = gson.toJson(itemList);
            return ok(jsonObject).as("application/json");

    }



    public Result registerReader(){
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        String readerInputs=request().body().asText();//taking request body content to a variable
        String[]readerDetails=new String[3];
        readerDetails=readerInputs.split(":");
        Reader reader=new Reader(0,readerDetails[0],readerDetails[1],readerDetails[2]);


        try {
            String savereaderquery = "INSERT INTO readers(readerName,readerTp,readerEmail)VALUES(?,?,?)";
            PreparedStatement cmd = con.prepareStatement(savereaderquery);//prepared statement interface defines the methods and properties that enable the java programme to send sql commands and receive data from data base
            cmd.setString(1,reader.getReaderName());
            cmd.setString(2,reader.getMobileNumber());
            cmd.setString(3,reader.getEmail());
            cmd.execute();
            JsonNode jsonNode = Json.toJson(new AppSummary("NEW READER SAVED SUCCESSFULLY : "+readerInputs));//if the query executes and save in DB, saved details will be displayed with a successful msg
            System.out.println(jsonNode);
            return ok(jsonNode).as("application/json");

        } catch (SQLException e) {
            e.printStackTrace();
            JsonNode jsonNode = Json.toJson(new AppSummary("Error while saving new Reader"));
            return ok(jsonNode).as("application/json");
        }

    }



    public Result showSelectedBook(){
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();


        String selectedBook=request().body().asText();//taking request body content to a variable
        System.out.println(selectedBook);


        String isbn="";
        String title="";
        String sector="";
        String author="";
        String publisher="";
        String publicationDate="";
        String numOfPages="";
        int currentReaderId=0;
        String burrowedDate="";
        String burrowedTime="";
        String returnDate="";
        String returnTime="";


        try {


            String selectquery="SELECT * FROM books WHERE isbn='"+selectedBook+"'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(selectquery);

            while(resultSet.next()){
                 isbn  = resultSet.getString("isbn");
                 title  = resultSet.getString("title");
                sector  = resultSet.getString("sector");
                 author  = resultSet.getString("author");
                 publisher  = resultSet.getString("publisher");
                 publicationDate  = resultSet.getString("publicationDate");
                 numOfPages  = String.valueOf(resultSet.getInt("numOfPages"));
                 currentReaderId  = resultSet.getInt("currentReaderId");
                 burrowedDate  = resultSet.getString("burrowedDate");
                 burrowedTime  = resultSet.getString("burrowedTime");
                 returnDate=resultSet.getString("returnDate");
                returnTime=resultSet.getString("returnTime");


            }

            String[]availableArray={"AVAILABLE!","","","","","","ISBN : "+isbn,"TITLE : "+title,"SECTOR : "+sector};
            if(currentReaderId==9){
                JsonNode jsonNode = Json.toJson(new ReturningDetails(availableArray));
                //System.out.println(selectedBook);
                System.out.println("book is available");
                return ok(jsonNode).as("application/json");
            }
            else{
                String selectcurrentreaderquery="SELECT * FROM readers WHERE readerId='"+currentReaderId+"'";

                Statement st = con.createStatement();
                ResultSet rs = statement.executeQuery(selectcurrentreaderquery);
                String name="";
                String tp="";
                String email="";

                while(rs.next()){
                    name=rs.getString("readerName");
                    tp=rs.getString("readerTp");
                    email=rs.getString("readerEmail");
                    }

                    String[]unavailableArray={"NOT AVAILABLE AT THE MOMENT","SHOULD BE AVAILABLE ON OR BEFORE : "+returnDate,"CURRENT READER : "+currentReaderId,"NAME : "+name,"CONTACT : "+tp,"EMAIL : "+email,"","",""};
                JsonNode jsonNode = Json.toJson(new ReturningDetails(unavailableArray));
                System.out.println("book is not available");
                return ok(jsonNode).as("application/json");
            }

        } catch (SQLException e) {
            String[]errorArray={"Error while searching the Book","","","","","","","",""};
            e.printStackTrace();
            JsonNode jsonNode = Json.toJson(new ReturningDetails(errorArray));
            return ok(jsonNode).as("application/json");
        }

    }

    //method to show all DVD details when the isbn of the DVD is entered
    public Result showSelectedDVD(){
        //setting the connection to the database using jdbc connectivity
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        String selectedDVD=request().body().asText();//taking request body content to a variable
        System.out.println(selectedDVD);


        String isbn="";
        String title="";
        String sector="";
        String producer="";
        String actors="";
        String publicationDate="";
        String languages="";
        String subtitles="";
        int currentReaderId=0;
        String burrowedDate="";
        String burrowedTime="";
 String returnDate="";
 String returnTime="";

        try {


            String selectquery="SELECT * FROM dvds WHERE isbn='"+selectedDVD+"'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(selectquery);

            while(resultSet.next()){
                isbn  = resultSet.getString("isbn");
                title  = resultSet.getString("title");
                sector  = resultSet.getString("sector");
                producer  = resultSet.getString("producer");
                actors  = resultSet.getString("actors");
                publicationDate  = resultSet.getString("publicationDate");
                languages  = resultSet.getString("languages");
                subtitles  = resultSet.getString("subtitles");
                currentReaderId  = resultSet.getInt("currentReaderId");
                burrowedDate  = resultSet.getString("burrowedDate");
                burrowedTime  = resultSet.getString("burrowedTime");
                returnDate  = resultSet.getString("returnDate");
                returnTime  = resultSet.getString("returnTime");
                //System.out.println(title+" ");
                //JsonNode jsonNode = Json.toJson(new AppSummary(isbn+title));
                //return ok(jsonNode).as("application/json");

            }

            String[]availableResponse={"AVAILABLE!","","","","","","ISBN : "+isbn,"TITLE : "+title,"SECTOR : "+sector};


            if(currentReaderId==9){
                JsonNode jsonNode = Json.toJson(new ReturningDetails(availableResponse));
                //System.out.println(selectedBook);
                System.out.println("DVD is available");
                return ok(jsonNode).as("application/json");
            }
            else{
                String selectcurrentreaderquery="SELECT * FROM readers WHERE readerId='"+currentReaderId+"'";

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(selectcurrentreaderquery);
                String name="";
                String tp="";
                String email="";

                while(rs.next()){
                    name=rs.getString("readerName");
                    tp=rs.getString("readerTp");
                    email=rs.getString("readerEmail");
                }

                String[]unavailableResponse={"NOT AVAILABLE AT THE MOMENT","SHOULD BE AVAILABLE ON OR BEFORE : "+returnDate,"CURRENT READER : "+currentReaderId,"NAME : "+name,"CONTACT : "+tp,"EMAIL : "+email,"","",""};
                JsonNode jsonNode = Json.toJson(new ReturningDetails(unavailableResponse));
                System.out.println("DVD is not available");
                return ok(jsonNode).as("application/json");
            }

        } catch (SQLException e) {
            String[]error={"ERROR WHILE SEARCHING THE DVD","","","","","","",""};
            e.printStackTrace();
            JsonNode jsonNode = Json.toJson(new ReturningDetails(error));
            return ok(jsonNode).as("application/json");
        }

    }




    @Override
    //provideing implementation for burrowBook method
    public Result burrowBook(){

         //connecting to DB
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        //taking request body content to a variable
        String burrowBookData=request().body().asText();

        System.out.println(burrowBookData);

        //splitting the larger string taking each split in to corresponding array index
        String[]burrowBookDetails=new String[7];
        burrowBookDetails=burrowBookData.split("@");

        String isbn=burrowBookDetails[0];
        String readerId=burrowBookDetails[1];
        int year=Integer.parseInt(burrowBookDetails[2]);
        int month=Integer.parseInt(burrowBookDetails[3]);
        int day=Integer.parseInt(burrowBookDetails[4]);
        int hour=Integer.parseInt(burrowBookDetails[5]);
        int minute=Integer.parseInt(burrowBookDetails[6]);

        //DateTime object
        DateTime burroweddatetime=new DateTime(minute,hour,day,month,year);

        String date=burroweddatetime.getDate();
        String time=burroweddatetime.getTime();

        //getting returning date using the method in DateTime class
        String returningDate=burroweddatetime.getBookReturningDate(7);
        System.out.println(date);
        System.out.println(time);

        //selecting the reader details using the reader id input
try {
    String selectReaderQuery="SELECT * FROM readers WHERE readerId='"+readerId+"'";
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(selectReaderQuery);
    String name="";
    String tp="";
    String email="";

    while(rs.next()){
        name=rs.getString("readerName");
        tp=rs.getString("readerTp");
        email=rs.getString("readerEmail");
    }

    //successful msg with reader details
    String[]burrowedSuccessfully={"Burrowed Book Successfully","Due Date and Time : "+returningDate+"@"+time,"Reader : "+readerId,"Name : "+name,"Contact : "+tp,"Email : "+email};


    //update the book table record by storing current reader id.
    String updatequery = "UPDATE books SET currentReaderId = '" + readerId + "', burrowedDate= '" + date + "', burrowedTime='" + time + "', returnDate='" + returningDate + "',  returnTime='" + time + "' WHERE isbn = '" + isbn + "'";
    PreparedStatement cmd = con.prepareStatement(updatequery);//prepared statement interface defines the methods and properties that enable the java programme to send sql commands and receive data from data base
    cmd.execute();

    JsonNode jsonNode = Json.toJson(new ReturningDetails(burrowedSuccessfully));
    return ok(jsonNode).as("application/json");


}catch(SQLException e) {
    String[]unsuccessful={"Error while Burrowing! please try again","","","","",""};
    e.printStackTrace();
    JsonNode jsonNode = Json.toJson(new ReturningDetails(unsuccessful));
    return ok(jsonNode).as("application/json");
        }





    }


    @Override
    //providing implementation for burrowDVD method
    public Result burrowDVD(){

         //connecting to db
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        //taking request body content to a variable
        String burrowDVDData=request().body().asText();

        System.out.println(burrowDVDData);

        //splitting larger string and taking each split part to corresponding array index
        String[]burrowDVDDetails=new String[7];
        burrowDVDDetails=burrowDVDData.split("@");

        String isbn=burrowDVDDetails[0];
        String readerId=burrowDVDDetails[1];
        int year=Integer.parseInt(burrowDVDDetails[2]);
        int month=Integer.parseInt(burrowDVDDetails[3]);
        int day=Integer.parseInt(burrowDVDDetails[4]);
        int hour=Integer.parseInt(burrowDVDDetails[5]);
        int minute=Integer.parseInt(burrowDVDDetails[6]);

        //DateTime object
        DateTime burroweddatetime=new DateTime(minute,hour,day,month,year);

        String date=burroweddatetime.getDate();
        String time=burroweddatetime.getTime();
        String returningDate=burroweddatetime.getBookReturningDate(3);
        System.out.println(date);
        System.out.println(time);


          //selecting reader details matching to the reader id input
        try {
            String selectReaderQuery="SELECT * FROM readers WHERE readerId='"+readerId+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectReaderQuery);
            String name="";
            String tp="";
            String email="";

            while(rs.next()){
                name=rs.getString("readerName");
                tp=rs.getString("readerTp");
                email=rs.getString("readerEmail");
            }

            //sucessful msg
            String[]burrowedSuccessfully={"Burrowed DVD Successfully","Due Date and Time : "+returningDate+"@"+time,"Reader : "+readerId,"Name : "+name,"Contact : "+tp,"Email : "+email};


            //update DVD table record by inserting current reader id
            String updatequery = "UPDATE dvds SET currentReaderId = '" + readerId + "', burrowedDate= '" + date + "', burrowedTime='" + time + "', returnDate='" + returningDate + "',  returnTime='" + time + "' WHERE isbn = '" + isbn + "'";
            PreparedStatement cmd = con.prepareStatement(updatequery);//prepared statement interface defines the methods and properties that enable the java programme to send sql commands and receive data from data base
            cmd.execute();

            JsonNode jsonNode = Json.toJson(new ReturningDetails(burrowedSuccessfully));
            return ok(jsonNode).as("application/json");


        }catch(SQLException e) {
            String[]unsuccessful={"Error while Burrowing! please try again","","","","",""};
            e.printStackTrace();
            JsonNode jsonNode = Json.toJson(new ReturningDetails(unsuccessful));
            return ok(jsonNode).as("application/json");
        }


    }



    public Result showReturningBook(){

        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        String isbnOfReturningBook=request().body().asText();//taking request body content to a variable

String[]dateArray=new String[3];
String[]timeArray=new String[2];
String isbn="";
String title="";
String burrowedDate="";
int numOfPages=0;
String burrowedTime="";
String currentReaderId="";
String currentReaderName="";
String dueDate="";
String dueTime="";

       try {
            String selectingQuery="SELECT * FROM books WHERE isbn='"+isbnOfReturningBook+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectingQuery);
            while(rs.next()){
                isbn=rs.getString("isbn");
                numOfPages=rs.getInt("numOfPages");
                title=rs.getString("title");
                burrowedDate=rs.getString("burrowedDate");
                burrowedTime=rs.getString("burrowedTime");
                currentReaderId=rs.getString("currentReaderId");
                dueDate=rs.getString("returnDate");
                dueTime=rs.getString("returnTime");
                }



           String readerQuery="SELECT readerName FROM readers WHERE readerId='"+currentReaderId+"'";
           Statement stmt = con.createStatement();
           ResultSet rslt = st.executeQuery(readerQuery);
           while(rslt.next()){
               currentReaderName=rslt.getString("readerName");
           }


           System.out.println("details : ");
           String[]arr1={isbn,title,burrowedDate+" "+burrowedTime,currentReaderId+" "+currentReaderName,dueDate+" "+dueTime};

            System.out.println(arr1);

            JsonNode jsonNode = Json.toJson(new ReturningDetails(arr1));
            return ok(jsonNode).as("application/json");
        } catch (SQLException e) {
            e.printStackTrace();
            JsonNode jsonNode = Json.toJson(new AppSummary("failed"));
            return ok(jsonNode).as("application/json");
        }



    }


    @Override
    //providing implementation for returnBook method
    public Result returnBook(){

    //connecting to db
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        //taking request body content to a variable
        String returnBookDetails=request().body().asText();

        //splitting larger string and taking each split part to corresponding array index
        String[]array=returnBookDetails.split("@");
        String isbn=array[0];
        int returningDay=Integer.parseInt(array[1]);
        int returningMonth=Integer.parseInt(array[2]);
        int returningYear=Integer.parseInt(array[3]);
        int returningMin=Integer.parseInt(array[4]);
        int returningHour=Integer.parseInt(array[5]);

        String dueDate="";
        String dueTime="";

        Statement st = null;

        //selecting due date and time
        try {
            String returnQuery="SELECT * FROM books WHERE isbn='"+isbn+"'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(returnQuery);
            while(rs.next()){
                dueDate=rs.getString("returnDate");
                dueTime=rs.getString("returnTime");
            }

            String[]arrDateSplits=dueDate.split("/");
            String[]arrTimeSplit=dueTime.split(":");

            int dueMin=Integer.parseInt(arrTimeSplit[1]);
            int dueHour=Integer.parseInt(arrTimeSplit[0]);
            int dueDay= Integer.parseInt(arrDateSplits[0]);
            int dueMonth=Integer.parseInt(arrDateSplits[1]);
            int dueYear=Integer.parseInt(arrDateSplits[2]);

            //DateTime object
            DateTime datetime=new DateTime(dueMin,dueHour,dueDay,dueMonth,dueYear);

            //calculating overdue using method in DateTime class
            int keptDurationInDays=datetime.keptPeriod(returningDay,returningMonth,returningYear);
            //calculating fee using method in DateTime class
            double fine=datetime.fineAsToHours(keptDurationInDays,dueHour,returningHour,dueMin,returningMin);

            String[]responseArr={String.valueOf(keptDurationInDays),String.valueOf(fine)+"£"};

            //updating book table record to remove current reader id
            String updateAfterReturn = "UPDATE books SET currentReaderId = '" + 9 + "', burrowedDate= '" + "" + "', burrowedTime='" + "" + "', returnDate='" + "" + "',  returnTime='" + "" + "' WHERE isbn = '" + isbn + "'";
            PreparedStatement cmd = con.prepareStatement(updateAfterReturn);//prepared statement interface defines the methods and properties that enable the java programme to send sql commands and receive data from data base

            cmd.execute();

            //sending overdue and the fee to the UI
            JsonNode jsonNode = Json.toJson(new ReturningDetails(responseArr));
            return ok(jsonNode).as("application/json");

        } catch (SQLException e) {
            e.printStackTrace();
            String[]errorArr={"Error while returning Book","Error while retrieving fine"};
            JsonNode jsonNode = Json.toJson(new ReturningDetails(errorArr));
            return ok(jsonNode).as("application/json");
        }





    }


    public Result showReturningDVD(){

        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        String isbnOfReturningDVD=request().body().asText();//taking request body content to a variable

        String[]dateArray=new String[3];
        String[]timeArray=new String[2];
        String isbn="";
        String title="";
        String burrowedDate="";
        String burrowedTime="";
        String currentReaderId="";
        String currentReaderName="";
        String dueDate="";
        String dueTime="";

        try {
            String selectingQuery="SELECT * FROM dvds WHERE isbn='"+isbnOfReturningDVD+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectingQuery);
            while(rs.next()){
                isbn=rs.getString("isbn");
                title=rs.getString("title");
                burrowedDate=rs.getString("burrowedDate");
                burrowedTime=rs.getString("burrowedTime");
                currentReaderId=rs.getString("currentReaderId");
                dueDate=rs.getString("returnDate");
                dueTime=rs.getString("returnTime");
            }



            String readerQuery="SELECT readerName FROM readers WHERE readerId='"+currentReaderId+"'";
            Statement stmt = con.createStatement();
            ResultSet rslt = st.executeQuery(readerQuery);
            while(rslt.next()){
                currentReaderName=rslt.getString("readerName");
            }


            System.out.println("details : ");
            String[]arr1={isbn,"Title : "+title,"Burrowed Date : "+burrowedDate+" @ "+burrowedTime,"Reader:"+currentReaderId+" , "+currentReaderName,"Due Date : "+dueDate+" @ "+dueTime};

            System.out.println(arr1);

            JsonNode jsonNode = Json.toJson(new ReturningDetails(arr1));
            return ok(jsonNode).as("application/json");
        } catch (SQLException e) {
            String[]errorArray={"","Error while searching records! Please try again","","",""};
            e.printStackTrace();
            JsonNode jsonNode = Json.toJson(new ReturningDetails(errorArray));
            return ok(jsonNode).as("application/json");
        }

    }


@Override
//providing implementation to returnDVD method
    public Result ReturnDVD(){

        //connecting to db
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        //taking request body content to a variable
        String returnDVDDetails=request().body().asText();

        //splitting larger string and taking each split in to corresponding array index
        String[]array=returnDVDDetails.split("@");

        String isbn=array[0];
        int returningDay=Integer.parseInt(array[1]);
        int returningMonth=Integer.parseInt(array[2]);
        int returningYear=Integer.parseInt(array[3]);
        int returningMin=Integer.parseInt(array[4]);
        int returningHour=Integer.parseInt(array[5]);

        String dueDate="";
        String dueTime="";

        Statement st = null;
        //selecting due date and time from db
        try {
            String returnQuery="SELECT * FROM dvds WHERE isbn='"+isbn+"'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(returnQuery);
            while(rs.next()){
                dueDate=rs.getString("returnDate");
                dueTime=rs.getString("returnTime");
            }

            String[]arrDateSplits=dueDate.split("/");
            String[]arrTimeSplit=dueTime.split(":");

            int dueMin=Integer.parseInt(arrTimeSplit[1]);
            int dueHour=Integer.parseInt(arrTimeSplit[0]);
            int dueDay= Integer.parseInt(arrDateSplits[0]);
            int dueMonth=Integer.parseInt(arrDateSplits[1]);
            int dueYear=Integer.parseInt(arrDateSplits[2]);

            //DateTime object
            DateTime datetime=new DateTime(dueMin,dueHour,dueDay,dueMonth,dueYear);

            //calculating overdue using method in DateTime class
            int keptDurationInDays=datetime.keptPeriod(returningDay,returningMonth,returningYear);
            //calculating fee using method in DateTime class
            double fine=datetime.fineAsToHours(keptDurationInDays,dueHour,returningHour,dueMin,returningMin);

            String[]responseArr={String.valueOf(keptDurationInDays),String.valueOf(fine)+"£"};

            //updating DVD table record  by removing hte current reader id
            String updateAfterReturn = "UPDATE dvds SET currentReaderId = '" + 9 + "', burrowedDate= '" + "" + "', burrowedTime='" + "" + "', returnDate='" + "" + "',  returnTime='" + "" + "' WHERE isbn = '" + isbn + "'";
            PreparedStatement cmd = con.prepareStatement(updateAfterReturn);//prepared statement interface defines the methods and properties that enable the java programme to send sql commands and receive data from data base

            cmd.execute();

            //sending overdue and fee to the UI
            JsonNode jsonNode = Json.toJson(new ReturningDetails(responseArr));
            return ok(jsonNode).as("application/json");

        } catch (SQLException e) {
            e.printStackTrace();
            String[]errorArr={"Error while returning DVD",""};
            JsonNode jsonNode = Json.toJson(new ReturningDetails(errorArr));
            return ok(jsonNode).as("application/json");
        }


    }


    @Override
    //providing implementation for the generateReport method
    public Result generateReport() throws SQLException {

        //connecting to db
        DBConnection connection=new DBConnection();
        Connection con=connection.connectingDB();

        //taking request body content to a variable
        String frontendData=request().body().asText();

        //splitting larger string and taking ewach in to corresponding array index
        String[]splitData=frontendData.split("@");
        int reportYear=Integer.parseInt(splitData[0]);
        int reportMonth=Integer.parseInt(splitData[1]);
        int reportDay=Integer.parseInt(splitData[2]);
        int reportMinute=Integer.parseInt(splitData[3]);
        int reportHour=Integer.parseInt(splitData[4]);

        ArrayList<Object> itemList = new ArrayList<>();

         //selecting due date and time
        String queryBook = "SELECT * FROM books WHERE currentReaderId!='"+9+"'";
        Statement statement = null;

        statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(queryBook);

        while (resultSet.next()) {

            String dueDate=resultSet.getString("returnDate");
            String dueTime=resultSet.getString("returnTime");

            String[]arrDateSplits=dueDate.split("/");
            String[]arrTimeSplit=dueTime.split(":");

            int dueHour=Integer.parseInt(arrTimeSplit[0]);
            int dueMin=Integer.parseInt(arrTimeSplit[1]);
            int dueDay= Integer.parseInt(arrDateSplits[0]);
            int dueMonth=Integer.parseInt(arrDateSplits[1]);
            int dueYear=Integer.parseInt(arrDateSplits[2]);

            //DateTime object
            DateTime datetime=new DateTime(dueMin,dueHour,dueDay,dueMonth,dueYear);

            //these 2 variables are what we want.. overdue and corresponding fee
            int keptDurationInDays=datetime.keptPeriod(reportYear,reportMonth,reportDay);
            double fine=datetime.fineAsToHours(keptDurationInDays,dueHour,reportHour,dueMin,reportMinute);

            //converting in to string
            String overdue=String.valueOf(keptDurationInDays);
            String correspondingFee=String.valueOf(fine+"£");

            //if only the overdue is > 0, means, there is an overdue, that object will be added to the list
            if(keptDurationInDays>0){
                Book tableBook = new Book(new ArrayList<>(), "", resultSet.getInt("numOfPages"), resultSet.getString("isbn"),
                        resultSet.getString("title"), resultSet.getString("sector"), correspondingFee, new DateTime(0, 0, 0, 0, 0), new DateTime(0, 0, 0, 0, 0),
                        new Reader(resultSet.getInt("currentReaderId"), "BOOK", overdue, null));

                itemList.add(tableBook);
            }


        }

        //selecting due date and time
        String queryDVD = "SELECT * FROM dvds WHERE currentReaderId!='"+9+"'";
        Statement stmt = null;

        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryDVD);

        while (rs.next()) {

            String dueDate=resultSet.getString("returnDate");
            String dueTime=resultSet.getString("returnTime");

            String[]arrDateSplits=dueDate.split("/");
            String[]arrTimeSplit=dueTime.split(":");

            int dueHour=Integer.parseInt(arrTimeSplit[0]);
            int dueMin=Integer.parseInt(arrTimeSplit[1]);
            int dueDay= Integer.parseInt(arrDateSplits[0]);
            int dueMonth=Integer.parseInt(arrDateSplits[1]);
            int dueYear=Integer.parseInt(arrDateSplits[2]);

            //DateTime object
            DateTime datetime=new DateTime(dueMin,dueHour,dueDay,dueMonth,dueYear);

            //these 2 variables are what we want.. overdue and corresponding fee
            int keptDurationInDays=datetime.keptPeriod(reportYear,reportMonth,reportDay);
            double fine=datetime.fineAsToHours(keptDurationInDays,dueHour,reportHour,dueMin,reportMinute);

            //converting in to string
            String overdue=String.valueOf(keptDurationInDays);
            String correspondingFee=String.valueOf(fine+"£");

            //only if there is an overdue that object wil be added to the list
            if(keptDurationInDays>0){
               DVD reportDVD=new DVD(new ArrayList<>(),new ArrayList<>(),"",new ArrayList<>(),rs.getString("isbn"),
                       rs.getString("title"),rs.getString("sector"),correspondingFee,new DateTime(0,0,0,0,0),
                       new DateTime(0,0,0,0,0),new Reader(rs.getInt("currentReaderId"),"DVD",overdue,""));
                itemList.add(reportDVD);
            }


        }

        //creating Gson object and sending Arraylist of objects to the UI
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String jsonObject = gson.toJson(itemList);
        return ok(jsonObject).as("application/json");
    }
}







