package se.nordea.conway.service;

import org.apache.commons.lang3.SerializationUtils;
import se.nordea.conway.domain.Block;

import java.util.Arrays;
import java.util.Random;

import static se.nordea.conway.util.GridUtil.iterate;

public class GridImpl implements Grid {

    private final Block[][] blocks;
    private final int height;
    private final int width;
    private final Random random = new Random();
    private Block[][] previousGen;

    public GridImpl(final Block[][] blocks) {
        this.height = blocks.length;
        this.width = blocks.length;
        this.blocks = blocks;
    }

    public GridImpl(final int height, final int width) {
        this.height = height;
        this.width = width;
        this.blocks = new Block[height][width];
        populateSeeds();
    }

    /**
     * Initializes the blocks randomly
     */
    private void populateSeeds() {
        iterate(blocks, (row, column) -> blocks[row][column] = new Block(random.nextBoolean()));
        System.out.println("\nInitial Grid \n");
        print();
    }

    /**
     * Calculate Block Status by checking the states of the nearby Blocks
     */
    private boolean getBlockStatus(int row, int column) {
        final int neighbourAliveCount = getNearbyAliveBlocks(row, column);
        return neighbourAliveCount >= 2 && neighbourAliveCount <= 3
                && (neighbourAliveCount == 3 || blocks[row][column].getState());
    }

    private int getNearbyAliveBlocks(final int row, final int column) {

        int totalAliveCount = 0;

        totalAliveCount += getTopRowAliveBlocks(row, column);

        totalAliveCount += getCurrentRowAliveBlock(row, column);

        totalAliveCount += getBottomRowAliveBlock(row, column);

        return totalAliveCount;
    }

    private int getTopRowAliveBlocks(final int row, final int column) {

        int topAliveCount = 0;

        if (row != 0 && column != 0 && isAlive(row - 1, column - 1)) {
            topAliveCount++;
        }

        if (row != 0 && isAlive(row - 1, column)) {
            topAliveCount++;
        }

        if (row != 0 && column != width - 1 && isAlive(row - 1, column + 1)) {
            topAliveCount++;
        }
        return topAliveCount;
    }

    private int getCurrentRowAliveBlock(final int row, final int column) {

        int currentAliveCount = 0;

        if (column != 0 && isAlive(row, column - 1)) {
            currentAliveCount++;
        }
        if (column != width - 1 && isAlive(row, column + 1)) {
            currentAliveCount++;
        }
        return currentAliveCount;
    }

    private int getBottomRowAliveBlock(final int row, final int column) {

        int bottomAliveCount = 0;

        if (row != height - 1 && column != 0 && isAlive(row + 1, column - 1)) {
            bottomAliveCount++;
        }

        if (row != height - 1 && isAlive(row + 1, column)) {
            bottomAliveCount++;
        }

        if (row != height - 1 && column != width - 1 && isAlive(row + 1, column + 1)) {
            bottomAliveCount++;
        }
        return bottomAliveCount;
    }

    /**
     * Checks if the block is alive
     */
    private boolean isAlive(final int row, final int col) {
        return blocks[row][col].getState();
    }

    @Override
    public Block[][] getBlocks() {
        return blocks;
    }

    /**
     * Game starts here
     */
    @Override
    public void startGame(final int iterations) {
        for (int i = 1; i <= iterations; i++) {
            if (this.isStableOrAllDead(i)) {
                System.out.println("\nGrid has reached its saturation point in iteration " + (i - 1) + ". No more iterations needed");
                break;
            }
            this.nextGeneration();
            this.finalizeState();
            System.out.println("\n Iteration -- " + i + " \n");
            this.print();
        }
    }

    /**
     * Iterates to next generation for the new state
     */
    @Override
    public void nextGeneration() {
        previousGen = SerializationUtils.clone(blocks);
        iterate(blocks, (row, column) -> blocks[row][column].setNewState(getBlockStatus(row, column)));
    }

    /**
     * Checks if all the blocks are dead or has reached stable state
     */
    @Override
    public boolean isStableOrAllDead(int iteration) {
        return Arrays.deepEquals(blocks, previousGen);
    }

    /**
     * Finalizes the state of blocks
     */
    @Override
    public void finalizeState() {
        iterate(getBlocks(), (row, column) -> getBlocks()[row][column].updateState());
    }

}
