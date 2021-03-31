package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class AppSummary {
    private String content;

    AppSummary(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class ReturningDetails{
    private String[]detailArray;

    public ReturningDetails(String[] detailArray) {
        this.detailArray = detailArray;
    }

    public String[] getDetailArray() {
        return detailArray;
    }

    public void setDetailArray(String[] detailArray) {
        this.detailArray = detailArray;
    }
}

public class HomeController extends Controller {

    public Result appSummary() {
        JsonNode jsonNode = Json.toJson(new AppSummary("Warsha"));
        return ok(jsonNode).as("application/json");
    }

    public Result postTest() {
        JsonNode jsonNode = Json.toJson(new AppSummary("Post Request Test => Data Sending Success"));
        return ok(jsonNode).as("application/json");
        }

    public Result postNewBookTest() throws ClassNotFoundException, SQLException {
        //setting the connection to the database using jdbc connectivity
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "");

        String name=request().body().asText();//taking request body content to a variable
        JsonNode jsonNode=Json.toJson(new AppSummary(name));

        //all the inputs are bundled in one string variable
        //spliting the string.
        String[]bookDetail=new String[4];
        bookDetail=name.split("@");
        String isbn=bookDetail[0];
        String title=bookDetail[1];
        String sector=bookDetail[2];
        String authors=bookDetail[3];

        String saveque = "INSERT INTO books(isbn,title,sector,author)VALUES(?,?,?,?)";
        PreparedStatement cmd = con.prepareStatement(saveque);//prepared statement interface defines the methods and properties that enable the java programme to send sql commands and receive data from data base

        cmd.setString(1,isbn );//setting the name input value to the name column in the sql customer table
        cmd.setString(2, title);//setting the address input value to the address column in the sql customer table
        cmd.setString(3, sector);//setting the contact number input value to the contactNumber column in the sql customer table
        cmd.setString(4, authors);//setting the username input value to the userName column in the sql customer table

        cmd.execute();//executing the command query


        System.out.println(jsonNode);
        System.out.println(request().body().asText());//print the request body

        JsonNode json=request().body().asJson();
        System.out.println(json);

        return ok(jsonNode).as("application/json");
    }












}
