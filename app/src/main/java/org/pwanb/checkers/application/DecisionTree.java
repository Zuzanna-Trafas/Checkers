package org.pwanb.checkers.application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecisionTree {
    private Board board;
    private int score;
    private ArrayList<DecisionTree> children;
    private Move move;

    public DecisionTree(Board board, int score, Move move, DecisionTree... children) {
        this.board = board;
        this.score = score;
        if (children != null){
            this.children = new ArrayList<>(Arrays.asList(children));
        }else
            this.children = new ArrayList<>();

        this.move = move;
    }

    public Board getBoard() {
        return board;
    }

    public Move getMove() {
        return move;
    }

    public int getScore() {
        return score;
    }

    public List<DecisionTree> getChildren() {
        return children;
    }

    public void setScore(int newValue) {
        score = newValue;
    }

    public void setChildren(DecisionTree ... children) {
        for (DecisionTree i : children) {
            setChild(i);
        }
    }

    public void setChild(DecisionTree child) {
        children.add(child);
    }

    public int getNumberChildren() {
        return children.size();
    }
}



