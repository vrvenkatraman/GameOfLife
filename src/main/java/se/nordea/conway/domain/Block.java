package se.nordea.conway.domain;

import java.io.Serializable;

public class Block implements Serializable {

    private boolean state;

    private boolean newState;

    public Block(boolean state) {
        this.state = state;
    }

    public void setNewState(boolean state) {
        newState = state;
    }

    public void updateState() {
        state = newState;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public String toString() {
        return String.valueOf(state);
    }

    @Override
    public boolean equals(Object o) {
        Block block = (Block) o;
        return this.state == block.state;
    }

}
