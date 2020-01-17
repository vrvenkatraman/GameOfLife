package se.nordea.conway.util;

import se.nordea.conway.domain.Block;
import se.nordea.conway.service.Grid;
import se.nordea.conway.service.GridImpl;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.BiConsumer;

import static java.lang.Math.toIntExact;

public class GridUtil {

    private static final String GRID_FILE = "src/main/resources/inputGrid.txt";
    private static final String LINE_SPLIT = " ";

    public static void iterate(Block[][] blocks, BiConsumer<Integer, Integer> consumer) {
        for (int i = 0; i < blocks.length; i++) {
            for (int k = 0; k < blocks[i].length; k++) {
                consumer.accept(i, k);
            }
        }
    }

    public static int validateInput(final Scanner scanner, String validRegex) {
        int input = 0;
        while (scanner.hasNext()) {
            String tmp = scanner.next();
            if (tmp.matches(validRegex)) {
                return Integer.parseInt(tmp);
            } else {
                System.out.printf("\"%s\" is not a valid input.\n", tmp);
            }
        }
        return input;
    }

    /**
     * Initializes the grid using input file, 1 will be considered true/alive, anything else will be considered false/dead
     */
    public static Grid initializeGridFromFile() throws Exception {
        int gridSize = getLineCount(GRID_FILE);
        try (BufferedReader br = Files.newBufferedReader(Paths.get(GRID_FILE))) {
            Block[][] blocks = new Block[gridSize][gridSize];
            for (int i = 0; i < gridSize; i++) {
                String line = br.readLine();
                String[] input = line.split(LINE_SPLIT);
                for (int j = 0; j < input.length; j++) {
                    blocks[i][j] = new Block(input[j].equalsIgnoreCase("1"));
                }
            }
            Grid grid = new GridImpl(blocks);
            System.out.println("\nInitial Grid \n");
            grid.print();
            return grid;
        }
    }

    private static int getLineCount(String fileName) throws Exception {
        return toIntExact(Files.lines(Paths.get(fileName)).count());
    }
}

