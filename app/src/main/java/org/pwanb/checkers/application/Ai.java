package org.pwanb.checkers.application;

import java.util.LinkedList;

class Ai {
    private DecisionTree decisionTree;
    private Move move;

    Ai(Board board){
        Board copyBoard = new Board(board);
        decisionTree = makeDecisionTree(copyBoard);
        move = selectMove(decisionTree);
    }

    public Move getMove() {
        return move;
    }

    public DecisionTree makeDecisionTree(Board board) {
        LinkedList<Move> allMoves = board.allMoves(board.getBlackPawns());
        DecisionTree mainTree = new DecisionTree(board, score(board), null, null);
        for (Move move : allMoves) {
            Board tmpBoard =  new Board(board);
            Pawn pawn = move.getPawn();
            LinkedList<Pair> destination = move.getDestination();
            tmpBoard.move(pawn, destination.get(0));
            DecisionTree firstLayer = new DecisionTree(tmpBoard, score(tmpBoard), move, null);
            LinkedList<Move> firstMoves = tmpBoard.allMoves(board.getWhitePawns());
            for (Move move1 : firstMoves) {
                Board tmpBoard1 = new Board(tmpBoard);
                Pawn pawn1 = move1.getPawn();
                LinkedList<Pair> destination1 = move1.getDestination();
                tmpBoard1.move(pawn1, destination1.get(0));
                DecisionTree secondLayer = new DecisionTree(tmpBoard1, score(tmpBoard1), move1, null);
                LinkedList<Move> secondMoves = tmpBoard1.allMoves(board.getBlackPawns());
                for (Move move2 : secondMoves) {
                    Board tmpBoard2 = new Board(tmpBoard1);
                    Pawn pawn2 = move2.getPawn();
                    LinkedList<Pair> destination2 = move2.getDestination();
                    tmpBoard2.move(pawn2, destination2.get(0));
                    secondLayer.setChild(new DecisionTree(tmpBoard2, score(tmpBoard2), move2, null));
                }
                firstLayer.setChild(secondLayer);
            }
            mainTree.setChild(firstLayer);
        }
        return mainTree;
    }

    private int score(Board board) {
        int count_color = 0;
        int count_opponent = 0;
        Pawn[] color = board.getBlackPawns();
        Pawn[] opponent = board.getWhitePawns();
        for (int i = 0 ; i < 12 ; i++) {
            if (color[i]!=null) {
                count_color++;
            }
            if (color[i].isKing()) {
                count_color+=2;
            }
            if (opponent[i]!=null) {
                count_opponent++;
            }
            if (opponent[i].isKing()) {
                count_opponent += 2;
            }
        }
        return count_color - count_opponent;
    }

    public Move selectMove(DecisionTree decisionTree) {
        Move chosenMove = null;
        int max = decisionTree.getScore();
        DecisionTree decisionTree1 = null;
        DecisionTree decisionTree2 = null;
        for (DecisionTree child : decisionTree.getChildren()) {
            if (child.getScore() >= max) {
                max = child.getScore();
                decisionTree1 = child;
            }
        }
        for (DecisionTree child1 : decisionTree1.getChildren()) {
            if (child1.getScore() <= max) {
                max = child1.getScore();
                decisionTree2 = child1;
            }
        }
        for (DecisionTree child2 : decisionTree2.getChildren()) {
            if (child2.getScore() >= max) {
                max = child2.getScore();
                chosenMove = child2.getMove();
            }
        }

        return chosenMove;
    }
}
