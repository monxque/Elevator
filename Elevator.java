/*
Program Name: Elevator.java
Author: Monique Leung
Date: Feb 24, 2022
*/

// Objective: This is a game for chasing after a thief by taking elevator. 

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Elevator {

    public static void main(String[] args) throws InterruptedException {
        final String INSTRUCTION = "instructions.txt";

        System.out.println("===================ELEVATOR===================");
        System.out.println();
        System.out.println("-----------------INSTRUCTIONS-----------------");
        try (Stream<String> stream = Files.lines(Paths.get(INSTRUCTION))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Exception: Instruction file not found");
        }
        System.out.println();
        System.out.println("-----------------GAME STARTS!-----------------");
        System.out.println();

        // Print the initial floor of the elevator
        System.out.println(":-------:  ");
        System.out.println("|   1   |  ");
        System.out.println(":-------:  ");

        int currentFloor = 1;
        System.out.println();
        System.out.printf("YOU ARE AT FLOOR %d NOW.", currentFloor);
        System.out.println();
        System.out.println();

        // the thief is between Floor 2 and 15
        int thiefLocation = (int) (Math.random() * 11) + 5;

        Scanner input = new Scanner(System.in);

        int inputChance = 6;

        // set a loop to print the elevator movement and tips while input chance >0
        do {
            // get a floor number from user
            System.out.println();

            // tell user how many chances they have
            if (inputChance == 6) {
                System.out.printf("You have %d chance(s).", inputChance);
            } else {
                System.out.printf("You have %d more chance(s).", inputChance);
            }
            System.out.println();
            System.out.print("- Enter a floor number (move at most 3 floors between 1F and 15F): ");

            // display error message if invalid input
            int nextFloor = -1;
            while (nextFloor == -1) {
                String str = input.next();
                nextFloor = getNextFloor(str, currentFloor);
            }

            // draw the escalator movement
            moveEscalator(nextFloor, currentFloor);

            // update the currentFloor value
            currentFloor = nextFloor;

            thiefLocation = moveThief(currentFloor, thiefLocation);

            int distance = Math.abs(thiefLocation - currentFloor);

            // give tips
            if (distance > 5) {
                System.out.println();
                System.out.printf("Tips: The thief is %d floor(s) away from you.", distance);
                System.out.println();
            } else if (distance <= 3) {
                System.out.println();
                System.out.println("Tips: The thief is <=3 floor(s) away from you.");
            } else if (distance <= 5) {
                System.out.println();
                System.out.println("Tips: The thief is <=5 floor(s) away from you.");
            }

            // if user caught the thief, end the program and display thank you message
            if (nextFloor == thiefLocation) {
                moveEscalator(nextFloor, currentFloor);
                System.out.println();
                System.out.println("BINGO! You caught the thief!");
                break;
            }

        } while (--inputChance > 0); // reduce input chance by 1

        // if used up all chances, tell the location of thief and display game over
        // message
        if (inputChance == 0) {
            System.out.println();
            System.out.printf("GAME OVER! The thief was in Floor %d.", thiefLocation);
            System.out.println();
            System.out.println("You have used up all your chances! He is gone!");
        }

        input.close();
    }

    public static void moveEscalator(int nextFloor, int currentFloor) throws InterruptedException {
        System.out.println();
        System.out.println();
        // user wants to go to higher level, draw escalator moving upwards
        if (nextFloor > currentFloor) {
            System.out
                    .printf("THE ELEVATOR IS MOVING UP FROM FLOOR %d TO FLOOR %d.", currentFloor, nextFloor);

            System.out.println();
            for (int i = currentFloor; i <= nextFloor; i++) {
                moveUp(i);
            }
        } else { // user wants to go to lower level, draw escalator moving downwards
            System.out
                    .printf("THE ELEVATOR IS MOVING DOWN FROM FLOOR %d TO FLOOR %d.", currentFloor, nextFloor);

            System.out.println();
            for (int i = currentFloor; i >= nextFloor; i--) {
                moveDown(i);
            }
        }
        // print out the existing location
        System.out.println();
        System.out.printf("YOU ARE AT FLOOR %d NOW.", nextFloor);
        System.out.println();
    }

    public static int getNextFloor(String str, int currentFloor) {
        int nextFloor = -1;
        boolean isValid = true;
        try {
            nextFloor = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            isValid = false;
            nextFloor = -1;
        }
        if (isValid && nextFloor > 15 || nextFloor < 1 || Math.abs(nextFloor - currentFloor) > 3
                || nextFloor == currentFloor) {
            isValid = false;
            nextFloor = -1;
        }
        if (!isValid) {
            System.out.println("Invalid input. Please try again.");
            System.out.println();
            System.out.print("- Enter a floor number (move at most 3 floors between 1F and 15F): ");
        }
        return nextFloor;
    }

    // print elevator going upwards
    public static void moveUp(int floor) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(":-------:     /\\  ");
        System.out.printf("|   %-2d  |    //\\\\\n", floor);
        System.out.println(":-------:   //  \\\\");
    }

    // print elevator going downwards
    public static void moveDown(int floor) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(":-------:  \\\\  //");
        System.out.printf("|   %-2d  |   \\\\//\n", floor);
        System.out.println(":-------:    \\/  ");
    }

    // moveThief
    public static int moveThief(int currentFloor, int thiefLocation) {
        // move the thief according to distance with the player
        int moveChance = (int) Math.random() * 2;

        int distance = Math.abs(thiefLocation - currentFloor);

        if (thiefLocation == 1) {
            // the thief is on floor 1, move 3 floors up if player is <= 2 floors above, or
            // move 2 floors up if player is >2 floors above
            thiefLocation = (distance <= 2) ? thiefLocation + 3 : thiefLocation + 2;
        } else if (thiefLocation == 15) {
            // the thief is on floor 15, move 3 floors down if player is <= 2 floors below,
            // or move 2 floors down if player is >2 floors below
            thiefLocation = (distance <= 2) ? thiefLocation - 3 : thiefLocation - 2;
        } else if (distance <= 2) {
            // the thief is 1-2 floor(s) away, move 3 floors upwards or downwards
            thiefLocation = (moveChance == 0) ? Math.max(1, thiefLocation - 3) : Math.min(15, thiefLocation + 3);
        } else if (distance > 2) {
            // the thief is >2 floors away, move 2 floors upwards or downwards
            thiefLocation = (moveChance == 0) ? Math.max(1, thiefLocation - 2) : Math.min(15, thiefLocation + 2);
        }
        return thiefLocation;
    }
}