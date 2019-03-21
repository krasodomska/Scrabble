package com.mygdx.game.system;

public enum Direction {
    DOWN(true),
    ACROSS(false);
    public boolean down;

    Direction(boolean isDown) {
        down = isDown;
    }
}
