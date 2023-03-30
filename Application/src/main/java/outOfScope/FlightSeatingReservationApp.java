package outOfScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlightSeatingReservationApp{

    // CLI will be turned to Rest Interface
    // this should be connected with the endpoint
    // Scanner - the request
    // response.

    // Ticket Agent App

    public int[][] populateAndPrintSeatingChart() {
        int[][] seatChart = new int[4][4];
        for (int row = 0; row < seatChart.length; row++) {
            for (int col = 0; col < seatChart.length; col++) {
                int seatNumber = Integer.parseInt(String.valueOf(row) + col);
                seatChart[row][col] = seatNumber;
                if (row == 0 || col == 0) {
                    // do something
                } else {
                    System.out.print(seatChart[row][col] + "\t");
                }
            }
            System.out.println();
        }
        return seatChart;
    }

    public List<Integer> seatReservationFunction() {
        System.out.println("Flight seating chart");
        int[][] seats = populateAndPrintSeatingChart();


        List<Integer> reservedSeatList = new ArrayList<>();
        //System.out.println(reservedSeatList);
        List<Integer> userReservedSeatList = new ArrayList<>();

        while (true) {
            System.out.println("Please enter a seat number: ");
            Scanner scan = new Scanner(System.in);
            int seatNumber = scan.nextInt();
            boolean isReserved = reservedSeatList.contains(seatNumber);

            if (isReserved) {
                System.out.println("Seat " + seatNumber + " is already booked.Please choose another seat.");
            } else if (seatNumber % 10 == 1) {
                System.out.println("Seat number is " + seatNumber + " is located in the right window aisle.");
                reservedSeatList.add(seatNumber);
                userReservedSeatList.add(seatNumber);
                //System.out.println(reservedSeatList);

            } else if (seatNumber % 10 == 2) {
                System.out.println("Seat number is " + seatNumber + " is located in the middle aisle.");
                reservedSeatList.add(seatNumber);
                userReservedSeatList.add(seatNumber);
                //System.out.println(reservedSeatList);

            } else {
                System.out.println("Seat number is " + seatNumber + " is located in the left window aisle.");
                reservedSeatList.add(seatNumber);
                userReservedSeatList.add(seatNumber);
                //System.out.println(reservedSeatList);
            }
            System.out.println("do you want to continue? ");
            updatedSeatingChart(seats, seatNumber);
            String answer = scan.next();

            if (answer.equals("no")) {
                System.out.println("Thanks for reserving your seat!");
                userReservedSeatList.add(seatNumber);
                break;
            }
        }
        System.out.println(userReservedSeatList);
        return userReservedSeatList;
    }
    private static void updatedSeatingChart(int[][] seats, int seatNumber) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats.length; j++) {
                if (seats[i][j] == seatNumber) {
                    seats[i][j] = 0;
                    System.out.println("Seats currently available: ");
                    printUpdatedArray(seats);
                }
            }
            System.out.println();
        }
    }
    private static void printUpdatedArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (i == 0 || j == 0) {
                    // do something
                } else {
                    System.out.print(array[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }
}




