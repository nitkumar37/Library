package org.example;

import java.io.BufferedReader;
import java.util.Map;

public interface Stock {
    public void Entry(Main obj);
    public void update(Main obj , int bid);

    public void remove(Main obj);

    //Displays the details of a book
    public void display_details(Book b,int i);
    public void searchBybid(int bid);

    public void searchBybName(String s);
    public void searchByaName(String s);
    public void searchBybcat(String s);
    public void cheapestBook(String op);
    public void inrange(float c1, float c2);

}
