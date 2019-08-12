package org.pwanb.checkers.application;

public final class Pair {
    private boolean set;
    private int X;
    private int Y;

    public Pair()
    {
        set = false;
    }

    public Pair(int x, int y)
    {
        set = true;
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void set(int x, int y) {
        X = x;
        Y = y;
    }

    boolean isSet(){
        return set; 
    }
}
