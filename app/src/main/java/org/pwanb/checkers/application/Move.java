package org.pwanb.checkers.application;

import java.util.LinkedList;

class Move {

    private Pawn pawn;
    private LinkedList<Pair> destination;

    Move(Pawn pawn, LinkedList<Pair> destination) {
        this.pawn = new Pawn(pawn);
        this.destination = destination;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public LinkedList<Pair> getDestination() {
        return destination;
    }

    @Override public String toString() {
        return pawn.getCurrentPosition().getX() + " " + pawn.getCurrentPosition().getY() + ":"+ destination.toString();
    }
}

