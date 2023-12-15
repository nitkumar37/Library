package org.example;

import java.time.LocalDate;

public class Book {
    String bookName,bookCategory,bookAuthor;
    LocalDate DOP;
    float bookCost;
    int bookid;



    public Book(Book b) {
        this.bookCost = b.bookCost;
        this.bookAuthor = b.bookAuthor;
        this.bookCategory = b.bookCategory;
        this.bookName = b.bookName;
        this.bookid = b.bookid;
        this.DOP = b.DOP;
    }

    public Book(){

    }

    public void later(Book a,Book b){
        a.bookCost=b.bookCost;
        a.bookAuthor = b.bookAuthor;
        a.bookCategory = b.bookCategory;
        a.bookName = b.bookName;
        a.bookid = b.bookid;
        a.DOP = b.DOP;

    }
}
