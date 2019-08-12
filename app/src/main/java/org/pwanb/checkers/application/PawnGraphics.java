package org.pwanb.checkers.application;


class PawnGraphics {
    final private int BLACK_PAWN;
    final private int BLACK_KING;
    final private int WHITE_PAWN;
    final private int WHITE_KING;
    final private int EMPTY;

    PawnGraphics() {
        BLACK_PAWN = R.drawable.black_pawn;
        BLACK_KING = R.drawable.black_king;
        WHITE_PAWN = R.drawable.white_pawn;
        WHITE_KING = R.drawable.white_king;
        EMPTY = R.drawable.empty;
    }

    int getBLACK_PAWN() {
        return BLACK_PAWN;
    }

    int getBLACK_KING() {
        return BLACK_KING;
    }

    int getWHITE_PAWN() {
        return WHITE_PAWN;
    }

    int getWHITE_KING() {
        return WHITE_KING;
    }

    int getEMPTY() {
        return EMPTY;
    }
}
