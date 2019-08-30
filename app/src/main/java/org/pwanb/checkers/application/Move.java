package org.pwanb.checkers.application;

import java.util.LinkedList;

class Move {

    private Pawn pawn;
    private LinkedList<Pair> destination;
    private int power;

    Move(Pawn pawn, LinkedList<Pair> destination) {
        this.pawn = new Pawn(pawn);
        this.destination = destination;
        this.power = 0;
    }

    @Override public String toString() {
        return pawn.getCurrentPosition().getX() + " " + pawn.getCurrentPosition().getY() + ":"+ destination.toString();
    }
}

