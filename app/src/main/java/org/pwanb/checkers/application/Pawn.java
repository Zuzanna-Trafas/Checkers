package org.pwanb.checkers.application;

import java.util.LinkedList;

class Pawn implements Comparable<Pawn>{
    private boolean king;
    private boolean white;
    private int moveOption;
    private Pair currentPosition;
    private LinkedList<LinkedList<Pair>> possibleAttack = new LinkedList<>();
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
        for(int i = 0; i < oldPawn.possibleAttack.size(); i++)
            possibleAttack.add(new LinkedList<>(oldPawn.possibleAttack.get(i)));
    }

    @Override
    public int compareTo(Pawn other) { return (this.moveOption - other.moveOption)* -1; }
    // PriorityQueue have the least element at the beginning, but we need the greatest, so
    // we reverse the order by multiple by -1
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return ((Pawn) o).moveOption == moveOption;
    }

    Pair[] getPossibleMove() { return possibleMove; }

    int getMoveOption() { return moveOption; }

    Pair getCurrentPosition() { return currentPosition; }

    LinkedList<LinkedList<Pair>> getPossibleAttack() { return possibleAttack; }

    boolean isKing() { return king; }

    boolean isWhite() { return white; }

    void setPossibleMove(int itr, Pair possibleMove) {
        this.possibleMove[itr] = possibleMove;
        moveOption = itr + 1;
    }

    void setKing() { this.king = true; }

    void setCurrentPosition(Pair currentPosition) { this.currentPosition = currentPosition; }

    void setMoveOption(){ moveOption = 0;}

    int setPossibleAttack(LinkedList<LinkedList<Pair>> possibleAttack){
        setEmptyQueue();
        moveOption = possibleAttack.get(0).size();
        for(int i = 0; i < possibleAttack.size(); i++)
            this.possibleAttack.add(new LinkedList<>(possibleAttack.get(i)));
        return moveOption;
    }

    private void setEmptyQueue(){
        for(int i = 0; i < possibleAttack.size(); i++)
            possibleAttack.get(i).clear();
        possibleAttack.clear();
    }

}
