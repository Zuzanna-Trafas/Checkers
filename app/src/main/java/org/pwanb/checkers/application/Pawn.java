package org.pwanb.checkers.application;

class Pawn implements Comparable<Pawn>{
    private boolean king;
    private boolean white;
    private Pair currentPosition;
    private int moveOption;
    private Pair[] possibleMove;


    Pawn(int x, int y, boolean white) {
        king = false;
        this.white = white;
        currentPosition = new Pair(x, y);
        possibleMove = new Pair[13];
    }

    Pawn(Pawn oldPawn) {
        white = oldPawn.white;
        king = oldPawn.king;
        moveOption = oldPawn.moveOption;
        currentPosition = new Pair(oldPawn.currentPosition.getX(), oldPawn.currentPosition.getY());
        possibleMove = new Pair[13];
    }

    void setPossibleMove(int itr, Pair possibleMove) {
        this.possibleMove[itr] = possibleMove;
        moveOption = itr + 1;
    }

    @Override
    public int compareTo(Pawn other) {
        return this.moveOption - other.moveOption;
    }

    Pair[] getPossibleMove() { return possibleMove; }

    int getMoveOption() { return moveOption; }

    Pair getCurrentPosition() { return currentPosition; }

    boolean isKing() { return king; }

    void setKing() { this.king = true; }

    boolean isWhite() { return white; }

    void setCurrentPosition(Pair currentPosition) { this.currentPosition = currentPosition; }

    void setMoveOption(){ moveOption = 0;}
}
