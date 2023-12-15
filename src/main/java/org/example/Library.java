package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Library implements Stock{

    Dbms_connection dbms;
    List<Book> data;

    public Library(){
        dbms = new Dbms_connection();
        data = new ArrayList<>();
    }
    public void Entry(Main obj){
        Book newentry = new Book();
        newentry.bookid = dbms.getlastbookid()+1;
        System.out.print("Enter Book Name - ");
        newentry.bookName = obj.string_reader(obj.buff);
        System.out.print("Enter Book Category - ");
        newentry.bookCategory = obj.string_reader(obj.buff);
        System.out.print("Enter Book Author - ");
        newentry.bookAuthor = obj.string_reader(obj.buff);
        System.out.print("Enter Book Cost - ");
        newentry.bookCost = obj.float_reader(obj.buff);
        System.out.print("Enter publication year - ");
        newentry.DOP = LocalDate.of(obj.int_reader(obj.buff),1,1);

        dbms.addingData(newentry,1);
        display_details(newentry,1);
    }



    public void update(Main obj, int bid){
        data = dbms.extractData("select * from books where Bookid = "+bid);
        Book t = new Book(data.get(0));
        System.out.println(data);
        System.out.println("Do you want to edit Book Name\n1) Yes\n2) No");
        if(obj.int_reader(obj.buff)==1){
            System.out.print("Enter Book Name - ");
            t.bookName = obj.string_reader(obj.buff);
        }

        System.out.println("Do you want to edit Author Name\n1) Yes\n2) No");
        if(obj.int_reader(obj.buff)==1){
            System.out.print("Enter Author Name - ");
            t.bookAuthor = obj.string_reader(obj.buff);
        }

        System.out.println("Do you want to edit Book Category\n1) Yes\n2) No");
        if(obj.int_reader(obj.buff)==1){
            System.out.print("Enter Book Category - ");
            t.bookCategory = obj.string_reader(obj.buff);
        }

        System.out.println("Do you want to edit Book Cost\n1) Yes\n2) No");
        if(obj.int_reader(obj.buff)==1) {
            System.out.print("Enter Book Cost - ");
            t.bookCost = obj.int_reader(obj.buff);
        }

        System.out.println("Do you want to edit year of publication - \n1) Yes\n2) No");
        if(obj.int_reader(obj.buff)==1) {
            System.out.print("Enter Publication Year - ");
            t.DOP = LocalDate.of(obj.int_reader(obj.buff),1,1);
        }

        dbms.addingData(t,2);
        display_details(t,2);
    }

    public void remove(Main obj){
        System.out.println("Enter Book id - ");
        data = dbms.removedb(obj.int_reader(obj.buff),1);
        display_array(data);
    }



    public void display_array(List<Book> b){
        if(b.isEmpty())System.out.println("No books found");
        else for(Book c:b)display_details(c,-1);
    }

    //Displays the details of a book
    // for printing 1-->adding new book details, 2--> editing existing book details  3--> deleting book details
    public void display_details(Book b,int i){
        System.out.println();
        if(i==1)System.out.println("Successfully added - ");
        else if(i==2)System.out.println("Successfully edited - ");
        else if(i==3)System.out.println("Successfully deleted - ");
        System.out.println("Book id - "+b.bookid);
        System.out.println("Book name - "+b.bookName);
        System.out.println("Author name - "+b.bookAuthor);
        System.out.println("Genre - "+b.bookCategory);
        System.out.println("Cost - "+b.bookCost);
        System.out.println("Publication Year - "+b.DOP);
        System.out.println();


    }
    public void searchBybid(int bid){
        data = dbms.extractData("select * from books where Bookid = "+bid);
        display_array(data);
    }

    public void searchBybName(String s) {
        data = dbms.extractData("select * from books where BookName = '"+s+"'");
        display_array(data);
    }
    public void searchByaName(String s){
        data = dbms.extractData("select * from books where AuthorName = '"+s+"'");
        display_array(data);
    }
    public void searchBybcat(String s){
        data = dbms.extractData("select * from books where BookCategory = '"+s+"'");
        display_array(data);
    }

    //for finding both cheapest and costliest books
    public void cheapestBook(String op){
        data = dbms.extractData("select * from books where BookCost = (Select "+op+"(BookCost) from books)");
        display_array(data);
    }

    public void inrange(float c1, float c2){
        data = dbms.extractData("select * from books where BookCost between "+c1+" and "+c2);
        display_array(data);
    }

    public void searchDop(String d){
        data =dbms.extractData("select * from books where DOP like '%"+d+"%'");
        display_array(data);
    }

    public void viewList(){
        data = dbms.extractData("select * from books");
        display_array(data);
    }

    public void remove_old(){
        data = dbms.removedb(-1,3);
        display_array(data);
    }

    public void close(){
        dbms.closeConnection();
    }


}