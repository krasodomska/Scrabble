package com.mygdx.game.system;
public class Direction {
    public enum Diretions {
        DOWN(true),
        ACROSS(false);
        boolean down;

        Diretions(boolean isDown) {
            down = isDown;
        }
    }
}
