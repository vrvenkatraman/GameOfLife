package se.nordea.conway.service;

import se.nordea.conway.domain.Block;

import java.util.Arrays;
import java.util.stream.Stream;

public interface Grid {

    Block[][] getBlocks();

    void nextGeneration();

    void finalizeState();

    void startGame(int iterations);

    boolean isStableOrAllDead(int iteration);

    default void print() {
        Stream.of(getBlocks()).map(Arrays::toString).forEach(System.out::println);
    }

}
