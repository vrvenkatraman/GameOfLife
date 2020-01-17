package se.nordea.conway;

import se.nordea.conway.service.Grid;
import se.nordea.conway.service.GridImpl;

import java.util.Scanner;

import static se.nordea.conway.util.GridUtil.initializeGridFromFile;
import static se.nordea.conway.util.GridUtil.validateInput;

public class GameOfLifeApp {

    private static final String VALID_INPUT = "^[1-9][0-9]*$";
    private static final String VALID_CHOICE = "^[1,2]";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 1 if you want Randomize the Grid and 2 if you want to use the default input file: ");
        int choice = validateInput(scanner, VALID_CHOICE);
        System.out.print("Number of Iterations: ");
        int iterations = validateInput(scanner, VALID_INPUT);
        Grid grid;

        switch (choice) {
            case 1:
                System.out.print("Required Size : ");
                int gridSize = validateInput(scanner, VALID_INPUT);
                grid = new GridImpl(gridSize, gridSize);
                grid.startGame(iterations);
                break;
            case 2:
                try {
                    grid = initializeGridFromFile();
                    grid.startGame(iterations);
                } catch (Exception e) {
                    System.out.println("Grid initialization failed");
                }
        }
        scanner.close();
    }
}
