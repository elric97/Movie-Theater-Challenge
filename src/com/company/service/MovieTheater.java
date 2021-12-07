package com.company.service;

import com.company.interfaces.Theater;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieTheater implements Theater {

    private final static Logger LOGGER =
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private HashMap<Character, ArrayList<Integer>> theaterMap;
    private LinkedHashMap<String, ArrayList<String>> reservationsMap;
    private HashMap<Character, Integer> seatsAvailable;
    private int seatsPerRow, totalRows, totalAvailableSeats;

    public MovieTheater(int seats, int rows) {
        theaterMap = new HashMap<>();
        reservationsMap = new LinkedHashMap<>();
        seatsAvailable = new HashMap<>();

        totalAvailableSeats = seats*rows;
        seatsPerRow = seats;
        totalRows = rows;

        char start = 'A';
        for(int i=0; i<rows; i++) {
            theaterMap.put(start, createRows(seats));
            seatsAvailable.put(start, seats);
            start++;
        }
    }

    private ArrayList<Integer> createRows(int seats) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<seats; i++) {
            list.add(i+1);
        }
        return list;
    }

    @Override
    public void processRequest(String request) {
        String[] split = request.split(" ");
        if(validateRequest(split)) {
            String name = split[0];
            int requestedSeats = Integer.parseInt(split[1]);
            char row = bookTickets(name, requestedSeats);
            if(row<'A' || row>'Z') {
                System.out.println(split[0] + " unable to book tickets");
            }
        } else {
            LOGGER.log(Level.INFO, split[0] + " can not be fulfilled because of incorrect params");
        }
    }

    private char bookTickets(String name, int seats) {
        if(seats > totalAvailableSeats) {
            return '-';
        }

        char row = findRows(seats);
        if(row == '-')
            return row;

        ArrayList<Integer> seatsList = findSeats(row, seats);

        reservationsMap.put(name, getSeatNumber(seatsList, row));
        totalAvailableSeats -= seats;
        seatsAvailable.put(row, seatsAvailable.get(row) - seats);

        return row;
    }

    private ArrayList<String> getSeatNumber(ArrayList<Integer> seats, char row) {
        ArrayList<String> list = new ArrayList<>();
        for(int i : seats) {
            list.add(row + "" + i);
        }

        return list;
    }

    private ArrayList<Integer> findSeats(char row, int seats) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> theaterRow = theaterMap.get(row);

        for(int i=0; i<theaterRow.size() && seats>0; i++) {
            if(theaterRow.get(i) != -1) {
                list.add(theaterRow.get(i));
                theaterRow.set(i, -1);
                seats--;
            }
        }

        theaterMap.put(row, theaterRow);

        return list;
    }

    private char findRows(int seats) {
        int min = Integer.MAX_VALUE;
        char row = '-';

        char start = 'A';

        for(int i=0; i<totalRows; i++) {
            int count = seatsAvailable.get(start) - seats;
            if(count >= 0 && count < min) {
                min = count;
                row = start;
            }

            start++;
        }

        return row;
    }

    private boolean validateRequest(String[] split) {
        return split.length == 2 && !split[0].isEmpty() && Integer.parseInt(split[1]) <= 20 && Integer.parseInt(split[1]) > 0;
    }


    @Override
    public void printReservations() {
        String content = "";
        for(String key : reservationsMap.keySet()) {
            StringBuilder temp = new StringBuilder(key);
            for(String s : reservationsMap.get(key)) {
                temp.append(" ").append(s);
            }
            content += temp;
            content += "\n";
        }

        createOutputFile("output.txt", content);
    }

    private void createOutputFile(String fileName, String content) {
        try {
            Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
            File f = new File(fileName);
            System.out.println(f.getCanonicalPath());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
