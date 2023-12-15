package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Dbms_connection {

    Connection con;
    Statement stmt;
    public Dbms_connection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root", "75388$!)^(Bunty");
            stmt = con.createStatement();

            System.out.println("Connected to database");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    //This function is used for creating a new bookid
    public int getlastbookid(){
        try {
            ResultSet rs = stmt.executeQuery("SELECT Bookid FROM books WHERE Bookid=(SELECT MAX(Bookid) FROM books);");
            if(rs.next())return rs.getInt(1);
            else return 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 1-->for adding new book data and  2-->updating existing data into database
    public void addingData(Book b,int operations){
        try{
            PreparedStatement pStmt;
            if(operations==1)pStmt= con.prepareStatement("insert into books values(?,?,?,?,?,?)");
            else pStmt=con.prepareStatement("UPDATE books SET Bookid = ?, BookName = ?,AuthorName = ?,BookCategory = ?,BookCost=?, DOP=? WHERE Bookid = "+b.bookid);
            pStmt.setInt(1, b.bookid);
            pStmt.setString(2,  b.bookName);
            pStmt.setString(3,  b.bookAuthor);
            pStmt.setString(4,  b.bookCategory);
            pStmt.setFloat(5,b.bookCost);
            pStmt.setDate(6,Date.valueOf(b.DOP));
            pStmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }
    }


    //removedb -- also for filtering based on book id. 1--> for removing 3--> for removing older date of publicaitons
    public List<Book> removedb(int bid,int operations){
        try {
            List<Book>data=new ArrayList<>();
            int yearThreshold = 21; // specifies the year, books with publication year greater than this are deleted
            if(operations == 3)data = extractData("select * from books where TIMESTAMPDIFF(year,DOP,CURDATE())>="+yearThreshold);
            else data = extractData("SELECT * FROM books WHERE Bookid = "+ bid);

            if(!data.isEmpty()){
                PreparedStatement pStmt;
                if(operations==1){
                    pStmt = con.prepareStatement("DELETE FROM books WHERE Bookid = ?");
                    pStmt.setInt(1,bid);
                }
                else pStmt = con.prepareStatement("DELETE FROM books WHERE TIMESTAMPDIFF(year,DOP,CURDATE())>="+yearThreshold);
                pStmt.executeUpdate();
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // This function is used in all the search functionality
    public List<Book> extractData(String query){

        List<Book> read=new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                Book m = new Book();
                m.bookid = rs.getInt(1);
                m.bookName = rs.getString(2);
                m.bookAuthor = rs.getString(3);
                m.bookCategory = rs.getString(4);
                m.bookCost = rs.getInt(5);
                m.DOP = rs.getDate(6).toLocalDate();
                read.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return read;
    }


    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
