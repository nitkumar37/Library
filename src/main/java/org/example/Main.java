package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    int selectedOperations,bookid;
    InputStreamReader isr;
    BufferedReader buff;
    Map<Integer,Book> db;


    public Main(){
        if(isr == null)isr = new InputStreamReader(System.in);
        if(buff==null)buff = new BufferedReader(isr);
        db = new HashMap<>();
        this.bookid=1;
    }

    public String string_reader(BufferedReader buff){

        try {
            return buff.readLine();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    public float float_reader(BufferedReader buff){
        try {
            return Float.parseFloat(buff.readLine());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return -1F;
    }

    public int int_reader(BufferedReader buff){

        try {
            return Integer.parseInt(buff.readLine());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void operations(Library lib, Main obj){
        while(true){
            System.out.println("Select an operation:\n1) See the Book List\n2) Save Book\n3) Update Book\n4) Remove Book\n5) Find Book \n6) Remove books more than 21 years of publication\n7) Exit");
            obj.selectedOperations = obj.int_reader(obj.buff);
            while(obj.selectedOperations>5 && obj.selectedOperations<1)obj.selectedOperations = obj.int_reader(obj.buff);

            switch (obj.selectedOperations){
                case 1:
                    lib.viewList();
                    break;
                case 2: //save book operations
                    lib.Entry(obj);
                    break;
                case 3: //update Book
                    System.out.print("Enter the book id - ");
                    obj.bookid = obj.int_reader(obj.buff);
                    lib.update(obj,obj.bookid);
                    break;
                case 4:
                    lib.remove(obj);
                    break;
                case 5:
                    System.out.println("Find by -\n1) Book id\n2) Book name\n3) Author Name\n4) Book Category\n5) Cheapest book\n6) Costliest book\n7) In price range\n8) Year of Publication\n9) Go back");
                    obj.selectedOperations=obj.int_reader(obj.buff);
                    while (obj.selectedOperations >6 && obj.selectedOperations<1)obj.selectedOperations = obj.int_reader(obj.buff);
                    switch (obj.selectedOperations){
                        case 1:
                            System.out.print("Enter the book id - ");
                            obj.bookid = obj.int_reader(obj.buff);
                            lib.searchBybid(obj.bookid);
                            break;
                        case 2:
                            System.out.print("Enter book name - ");
                            lib.searchBybName(obj.string_reader(obj.buff));
                            break;
                        case 3:
                            System.out.print("Enter Author name - ");
                            lib.searchByaName(obj.string_reader(obj.buff));
                            break;
                        case 4:
                            System.out.print("Enter Book Category - ");
                            lib.searchBybcat(obj.string_reader(obj.buff));
                            break;
                        case 5:
                            lib.cheapestBook("MIN");
                            break;
                        case 6:
                            lib.cheapestBook("MAX");
                            break;
                        case 7:
                            System.out.print("Enter the maximum price - ");
                            float max_price = obj.float_reader(obj.buff);
                            System.out.print("Enter the minimum price - ");
                            lib.inrange(obj.float_reader(obj.buff),max_price);
                            break;
                        case 8:
                            System.out.println("Enter Year of publicaton - ");
                            lib.searchDop(obj.string_reader(obj.buff));
                            break;
                        case 9:
                            break;
                        default:
                            System.out.println("Please select correct input");
                    }
                    break;
                case 6:
                    lib.remove_old();
                    break;
                case 7:
                    lib.close();
                    return;
                default:
                    System.out.println("Please select correct input");

            }
        }

    }

    public static void main(String args[]){
        Main obj = new Main();
        while(true) {
            Library lib = new Library();
            System.out.println("Welcome to the Library");
            obj.operations(lib,obj);
        }
    }
}
