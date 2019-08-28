package org.pwanb.checkers.application;

final class Pair {
    private boolean set;
    private int X;
    private int Y;

    Pair() { set = false; }

    Pair(int x, int y) {
        set = true;
        X = x;
        Y = y;
    }

    @Override public String toString() {
        return X + " " + Y;
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

    boolean isEqual(Pair other){
        if (other.X == this.X && other.Y == this.Y)
            return true;
        return false;
    }
}
