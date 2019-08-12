package org.pwanb.checkers.application;

public final class Pair {
    private boolean set;
    private int X;
    private int Y;

    Pair() { set = false; }

    Pair(int x, int y) {
        set = true;
        X = x;
        Y = y;
    }

    int getX() { return X; }

    int getY() { return Y; }

    void set(int x, int y) {
        set = true;
        X = x;
        Y = y;
    }

    void unset(){ set = false; }

    boolean isSet(){ return set; }
}
