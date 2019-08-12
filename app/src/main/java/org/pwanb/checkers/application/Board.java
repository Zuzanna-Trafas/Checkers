package org.pwanb.checkers.application;

public class Board {

    private color[][] boardState = new color[8][8];  //our board


    public enum color {
        BLACK,
        BLACK_PROMOTED,
        WHITE,
        WHITE_PROMOTED,
        EMPTY
    }


}
