package org.pwanb.checkers.application;

import java.util.LinkedList;
import java.util.List;

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
        for(int i = 0; i < 4; i++) {
            possibleAttack.add(new LinkedList<Pair>());

        }
    }

    Pawn(Pawn oldPawn) {
        white = oldPawn.white;
        king = oldPawn.king;
        moveOption = oldPawn.moveOption;
        currentPosition = new Pair(oldPawn.currentPosition.getX(), oldPawn.currentPosition.getY());
        possibleMove = new Pair[13];
        for(int i = 0; i < 4; i++)
            possibleAttack.add(new LinkedList<>(oldPawn.possibleAttack.get(i)));
    }

    @Override
    public int compareTo(Pawn other) { return this.moveOption - other.moveOption; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pawn pawn = (Pawn) o;
        return ((Pawn) o).currentPosition.equals(currentPosition);
    }

    Pair[] getPossibleMove() { return possibleMove; }

    int getMoveOption() { return moveOption; }

    Pair getCurrentPosition() { return currentPosition; }

    List<Pair> getPossibleAttack(int i) {  return possibleAttack.get(i);  }

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
            this.possibleAttack.set(i, new LinkedList<>(possibleAttack.get(i)));
        return moveOption;
    }

    void setEmptyQueue(){
        for(int i = 0; i < possibleAttack.size(); i++)
            possibleAttack.get(i).clear();
    }

}
