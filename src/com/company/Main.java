package com.company;

import com.company.interfaces.Theater;
import com.company.service.MovieTheater;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    //10X20 layout of theater
    private static int noOfRows = 10;
    private static int noOfCols = 10;

    public static void main(String[] args) {
        System.out.println("Enter file path");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        Theater movieTheater = new MovieTheater(noOfCols, noOfRows);
        BufferedReader bufferedReader;
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            while((line = bufferedReader.readLine()) != null) {
                //do operation on lines
                System.out.println(line);
                movieTheater.processRequest(line);
            }
            movieTheater.printReservations();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
