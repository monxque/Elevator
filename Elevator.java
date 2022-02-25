/*
Program Name:	Elevator.java
Author: 		Monique Leung
Date:			Feb 24, 2022
*/

// Objective: This is a game for chasing after a thief by taking elevator. 

import java.util.Scanner;

public class Elevator {

    public static void main(String[] args) {

        // Print the initial floor of the elevator
        System.out.println("=====CHASE AFTER HIM!=====");
        System.out.println(
                "\nThere is a thief in this building. What you have to do is to chase after him. \n\nThis is the pattern of how he moves:\n1. If you are at 1-2 floor(s) above or below him, he will move 3 floors up or down.\n2. If you are >2 floors away from him, he will move 2 floors up or down.\n3. If he is too close to the highest or lowest floor, he may move less than 2 or 3 floors and stay in highest or lowest floor in that round.\n\nYou can only move at most 3 floors each time and have 6 chances to catch him, or he will leave out of your sight forever!\n");
        System.out.println("-----------------GAME STARTS!-----------------\n");
        System.out.println(":-------:  ");
        System.out.println("|   1   |  ");
        System.out.println(":-------:  ");

        int currentFloor = 1;
        System.out.println("\nYOU ARE AT FLOOR " + currentFloor + " NOW.\n");

        // thief is between Floor 2 and 15
        int thiefLocation = (int) (Math.random() * 11) + 5;

        // get a floor number from user
        Scanner input = new Scanner(System.in);
        System.out.print("- Enter a floor number (move at most 3 floors between 1F and 15F): ");
        int nextFloor = input.nextInt();
        int inputChance = 6;

        // set a loop to print the movement of the elevator until the input matches the
        // thief's location
        while (nextFloor != thiefLocation) {

            // display error message if invalid input and skip the loop
            if (nextFloor > 15 || nextFloor < 1 || Math.abs(nextFloor - currentFloor) > 3 || nextFloor == currentFloor) {
                System.out.println("Invalid input. Please try again.\n");
                System.out.print("- Enter a floor number (move at most 3 floors between 1F and 15F): ");
                nextFloor = input.nextInt();
                continue;
            }

            // draw the escalator movement
            moveEscalator(nextFloor, currentFloor);

            // update the currentFloor value
            currentFloor = nextFloor;


            // reduce the input chance
            inputChance--;

            //if no more chance left
            if (inputChance == 0) {
                // tell the location of thief and display game over message
                System.out.println("GAME OVER! The thief was in Floor "+thiefLocation+".");
                System.out.println("You have used up all your chances! He is gone!");
                break;
            } else { // if still have chance(s) left
                // move the thief
                thiefLocation = moveThief(currentFloor, thiefLocation);
                
                // System.out.println(thiefLocation);
                
                // give tips
                if (Math.abs(thiefLocation - currentFloor) > 5) {
                    System.out.printf("Tips: The thief is %d floor(s) away from you.\n",
                            Math.abs(thiefLocation - currentFloor));
                } else if (Math.abs(thiefLocation - currentFloor) <=3) {
                    System.out.println("Tips: The thief is <=3 floor(s) away from you.");
                } else if (Math.abs(thiefLocation - currentFloor) <=5) {
                    System.out.println("Tips: The thief is <=5 floor(s) away from you.");
                }
                // get a new input from user and continue the loop
                System.out.print("\nYou have " + inputChance
                        + " more chance(s).\n\n- Enter a floor number (move at most 3 floors between 1F and 15F): ");
                nextFloor = input.nextInt();
            }
        }
        // if user caught the thief, end the program and display thank you message
        if (nextFloor == thiefLocation) {
            moveEscalator(nextFloor, currentFloor);
            System.out.println("\nBINGO! You caught the thief!");
        }
        input.close();
    }

    public static void moveEscalator(int nextFloor, int currentFloor) {
        // initialize the count of the floor display
        int count = 0;

        // user wants to go to higher level
        if (nextFloor > currentFloor) {
            System.out
                    .println("\nTHE ELEVATOR IS MOVING UP FROM FLOOR " + currentFloor + " TO FLOOR " + nextFloor
                            + ".\n");
            count = nextFloor;

            // draw escalator going upwards until reaching the destination
            while (true) {
                moveUp(count);
                count--;
                if (count < currentFloor) {
                    break;
                }
            }
        } else {

            // user wants to go to lower level
            System.out.println(
                    "\nTHE ELEVATOR IS MOVING DOWN FROM FLOOR " + currentFloor + " TO FLOOR " + nextFloor + ".\n");
            count = currentFloor;

            // draw escalator going downwards until reaching the destination
            while (true) {
                moveDown(count);
                count--;
                if (count < nextFloor) {
                    break;
                }
            }
        }
        // print out the existing location
        System.out.println("\nYOU ARE AT FLOOR " + nextFloor + " NOW.\n");
    }

    // print elevator going upwards
    public static void moveUp(int floor) {
        System.out.println(":-------:     /\\  ");
        System.out.printf("|   %-2d  |    //\\\\\n", floor);
        System.out.println(":-------:   //  \\\\");
    }

    // print elevator going downwards
    public static void moveDown(int floor) {
        System.out.println(":-------:  \\\\  //");
        System.out.printf("|   %-2d  |   \\\\//\n", floor);
        System.out.println(":-------:    \\/  ");
    }

    // moveThief
    public static int moveThief(int currentFloor, int thiefLocation) {
        // move the thief according to distance with the player
        int moveChance = (int) Math.random() * 2;

        if (currentFloor > thiefLocation) {
            // if the thief is in lower floor
            if (thiefLocation == 1) {
                // the thief is on floor 1, move 3 floors up if <= 2 floors above, or move 2 floors up if >2 floors above
                thiefLocation = (currentFloor - thiefLocation <= 2) ? thiefLocation + 3: thiefLocation +2;
            } else if (currentFloor - thiefLocation <= 2) {
                // the thief is 1-2 floor(s) below, move 3 floors upwards or downwards
                thiefLocation = (moveChance == 0) ? Math.max(1, thiefLocation - 3) : Math.min(15, thiefLocation + 3);
            } else if (currentFloor - thiefLocation > 2) {
                // the thief is >2 floors below, move 3 floors upwards or downwards
                thiefLocation = (moveChance == 0) ? Math.max(1, thiefLocation - 2) : Math.min(15, thiefLocation + 2);
            } 
        } else {
            // if the thief is in higher floor
            if (thiefLocation - currentFloor <= 2 && thiefLocation == 15) {
                // the thief is on floor 15, move 3 floors down if <= 2 floors above, or move 2 floors down if >2 floors above
                thiefLocation = (thiefLocation - currentFloor <=2) ? thiefLocation - 3: thiefLocation -2;
            } else if (thiefLocation - currentFloor <= 2) {
                // the thief is 1-2 floor(s) above, move 3 floors upwards or downwards
                thiefLocation = (moveChance == 0) ? Math.max(1, thiefLocation - 3) : Math.min(15, thiefLocation + 3);
            } else if (thiefLocation - currentFloor > 2) {
                // the thief is >2 floors above, move 2 floors upwards or downwards
                thiefLocation = (moveChance == 0) ? Math.max(1, thiefLocation - 2) : Math.min(15, thiefLocation + 2);
            }
        }
        return thiefLocation;
    }
}