package se.nordea.conway;

import org.junit.Test;
import se.nordea.conway.domain.Block;
import se.nordea.conway.service.GridImpl;

import static org.junit.Assert.assertEquals;

public class GridTest {

    GridImpl grid;

    @Test
    public void given_Few_Live_Blocks_With_No_Live_Neighbours_When_Iterated_To_One_Generation_Forward_Then_Live_Block_Dies() {

        //Given
        Block[][] blocks = {
                {new Block(true), new Block(false), new Block(false)},
                {new Block(false), new Block(false), new Block(false)},
                {new Block(false), new Block(false), new Block(true)}
        };

        grid = new GridImpl(blocks);

        assertEquals(true, grid.getBlocks()[0][0].getState());
        assertEquals(true, grid.getBlocks()[2][2].getState());

        // When
        when_Iterated_To_One_Generation_Forward();

        //Then
        assertEquals(false, grid.getBlocks()[0][0].getState());
        assertEquals(false, grid.getBlocks()[2][2].getState());
    }

    @Test
    public void given_A_Dead_Block_With_Three_Live_Neighbours_When_Iterated_To_One_Generation_Forward_Then_That_Dead_Block_Becomes_Alive() {
        //Given
        Block[][] blocks = {
                {new Block(true), new Block(false), new Block(false)},
                {new Block(true), new Block(false), new Block(false)},
                {new Block(true), new Block(false), new Block(false)}
        };

        grid = new GridImpl(blocks);

        assertEquals(false, grid.getBlocks()[1][1].getState());
        assertEquals(true, grid.getBlocks()[0][0].getState());
        assertEquals(true, grid.getBlocks()[1][0].getState());
        assertEquals(true, grid.getBlocks()[2][0].getState());

        //When
        when_Iterated_To_One_Generation_Forward();

        //Then
        assertEquals(true, grid.getBlocks()[1][1].getState());
    }

    @Test
    public void given_A_Live_Block_With_Two_Live_Neighbours_When_Iterated_To_One_Generation_Forward_Then_That_Live_Block_Stays_Alive() {

        //Given
        Block[][] blocks = {
                {new Block(false), new Block(true), new Block(false)},
                {new Block(false), new Block(true), new Block(false)},
                {new Block(false), new Block(true), new Block(false)}
        };

        grid = new GridImpl(blocks);

        assertEquals(true, grid.getBlocks()[0][1].getState());
        assertEquals(true, grid.getBlocks()[1][1].getState());
        assertEquals(true, grid.getBlocks()[2][1].getState());

        //When
        when_Iterated_To_One_Generation_Forward();

        //Then
        assertEquals(true, grid.getBlocks()[1][1].getState());
    }

    @Test
    public void given_A_Live_Block_With_More_Than_Three_Live_Neighbours_When_Iterated_To_One_Generation_Forward_Then_That_Live_Block_Dies() {

        //Given
        Block[][] blocks = {
                {new Block(true), new Block(true), new Block(false)},
                {new Block(true), new Block(true), new Block(false)},
                {new Block(false), new Block(true), new Block(false)}
        };

        grid = new GridImpl(blocks);

        assertEquals(true, grid.getBlocks()[0][0].getState());
        assertEquals(true, grid.getBlocks()[1][0].getState());
        assertEquals(true, grid.getBlocks()[0][1].getState());
        assertEquals(true, grid.getBlocks()[1][1].getState());
        assertEquals(true, grid.getBlocks()[2][1].getState());

        //When
        when_Iterated_To_One_Generation_Forward();

        //Then
        assertEquals(false, grid.getBlocks()[1][1].getState());
    }


    private void when_Iterated_To_One_Generation_Forward() {

        grid.nextGeneration();
        grid.finalizeState();
    }
}
