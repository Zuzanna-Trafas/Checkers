package org.pwanb.checkers.application;

import android.app.Activity;
import android.widget.ImageView;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Board{
    private boolean whiteTurn;
    private Pair chosenField;
    private Field[][] board = new Field[8][8];
    private Activity activity;
    private Pawn[] white = new Pawn[12];
    private Pawn[] black = new Pawn[12];
    private PriorityQueue<Pawn> attack = new PriorityQueue<>();
    private Queue<Pair> highlights = new LinkedList<>();


    class Field implements AppCompatImageView.OnClickListener {

        private ImageView image;
        private Pair position;
        private Pawn pawn = null;

        Field(int x, int y, ImageView img) {
            position = new Pair(x,y);
            image = img;
            image.setOnClickListener(this);
        }

        void setHighlight() {
            Drawable highlight = activity.getResources().getDrawable(R.drawable.highlight);
            image.setBackground(highlight);
            highlights.add(new Pair (position.getX(),position.getY()));
        }

        void deleteHighlightField() { image.setBackground(null); }

        void setPawn(final int x, final int y, final boolean white,final int imageID ) {
            this.pawn = new Pawn(x, y, white);
            image.setImageResource(imageID);
        }

        void deletePawn(){
            pawn = null;
            image.setImageResource(PawnGraphics.EMPTY.get());
        }


        @Override
        public void onClick(View v) {
            if (attack.size() > 0) {
                if (pawn != null)
                    attackFirstClick();
                else if (chosenField.isSet())
                    attackSecondClick();
            } else {
                if (pawn != null)
                    moveFirstClick();
                else if (chosenField.isSet())
                    moveSecondClick();
            }
        }

        private void moveFirstClick() {
            int prevX = chosenField.getX();
            int prevY = chosenField.getY();
            int X = position.getX();
            int Y = position.getY();
            if (pawn.isWhite() == whiteTurn) {
                if (chosenField.isSet()) {
                    if (X != prevX || Y != prevY) {
                        deleteHighlightBoard();
                        setHighlight();
                        chosenField.set(X, Y);
                        showOption();
                    } else {
                        deleteHighlightBoard();
                        chosenField.unset();
                    }
                } else {
                    setHighlight();
                    chosenField.set(X, Y);
                    showOption();
                }
            }
        }

        private void moveSecondClick() {
            int prevX = chosenField.getX();
            int prevY = chosenField.getY();
            for (int i = 0; i < board[prevX][prevY].pawn.getMoveOption(); i++)
            {
                if (board[prevX][prevY].pawn.getPossibleMove()[i].equals(position)){
                    move(board[prevX][prevY].pawn, position);
                    chosenField.unset();
                    whiteTurn = !whiteTurn;
                    possibleAction();
                    break;
                }
            }
        }

        void showOption(){
            int x;
            int y;
            for(int i = 0 ; i< pawn.getMoveOption(); i++)
            {
                x = pawn.getPossibleMove()[i].getX();
                y = pawn.getPossibleMove()[i].getY();
                board[x][y].setHighlight();
            }
        }

        private void attackFirstClick(){
            if(attack.contains(pawn))
                System.out.println("ELO");

        }

        private void attackSecondClick(){

        }

    }



    Board(ImageView[][] boardMain, Activity activity) {
        chosenField = new Pair();
        this.activity = activity;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Field(i, j, boardMain[i][j]);
            }
        }
        this.start();
    }

    private void deleteHighlightBoard(){
        Pair highlight;
        int x, y;
        do {
            highlight = highlights.poll();
            x = highlight.getX();
            y = highlight.getY();
            board[x][y].deleteHighlightField();
        }while(highlights.peek() != null);
    }

    private void start() {
        whiteTurn = true;
        for (int i = 0; i < 4; i++) {
            board[2*i][0].setPawn(2*i, 0, true, PawnGraphics.WHITE_PAWN.get());
            board[2*i + 1][1].setPawn(2*i+1, 1, true, PawnGraphics.WHITE_PAWN.get());
            board[2*i][2].setPawn(2*i, 2, true,PawnGraphics.WHITE_PAWN.get());
            board[2*i + 1][5].setPawn(2*i+1, 5, false,PawnGraphics.BLACK_PAWN.get());
            board[2*i][6].setPawn(2*i, 6, false,PawnGraphics.BLACK_PAWN.get());
            board[2*i + 1][7].setPawn(2*i+1, 7, false,PawnGraphics.BLACK_PAWN.get());
            white[3*i] = new Pawn(board[2*i][0].pawn);
            white[3*i+1]= new Pawn(board[2*i + 1][1].pawn);
            white[3*i+2]= new Pawn(board[2*i][2].pawn);
            black[3*i] =  new Pawn(board[2*i+1][5].pawn);
            black[3*i+1]= new Pawn(board[2*i][6].pawn);
            black[3*i+2]= new Pawn(board[2*i+1][7].pawn);
        }
        this.possibleAction();
    }

    private void possibleAction(){
        if(whiteTurn) {
            updateAttackWhite();
            if(attack.size() > 0)
                showAttackOption();
            else
                updateMoveWhite();}
        else {
            updateAttackBlack();
            if(attack.size() > 0)
                showAttackOption();
            else
                updateMoveBlack();
        }
    }

    private void updateMoveWhite(){
        for (int i =0; i<12; i++)
        {
            if(white[i] != null){
                int x = white[i].getCurrentPosition().getX();
                int y = white[i].getCurrentPosition().getY();
                int itr = 0;
                board[x][y].pawn.setMoveOption();
                white[i].setMoveOption();
                if (white[i].isKing())
                {
                    //TODO king move
                }
                else
                {
                    if(y < 7)
                    {
                        if(x<7 && board[x+1][y+1].pawn == null) {
                            white[i].setPossibleMove(itr, new Pair(x+1,y+1));
                            board[x][y].pawn.setPossibleMove(itr, new Pair(x+1,y+1));
                            itr++;
                        }

                        if(x > 0 && board[x-1][y+1].pawn == null){
                            white[i].setPossibleMove(itr, new Pair(x-1,y+1));
                            board[x][y].pawn.setPossibleMove(itr, new Pair(x-1,y+1));
                        }
                    }
                }
            }
        }
    }

    private void updateMoveBlack(){
        for (int i =0; i<12; i++)
        {
            if(black[i] != null){
                int x = black[i].getCurrentPosition().getX();
                int y = black[i].getCurrentPosition().getY();
                int itr = 0;
                board[x][y].pawn.setMoveOption();
                black[i].setMoveOption();
                if (black[i].isKing())
                {
                    //TODO king move
                }
                else
                {
                    if(y > 0)
                    {
                        if(x<7 && board[x+1][y-1].pawn == null) {
                            black[i].setPossibleMove(itr, new Pair(x+1,y-1));
                            board[x][y].pawn.setPossibleMove(itr, new Pair(x+1,y-1));
                            itr++;

                        }
                        if(x > 0 && board[x-1][y-1].pawn == null){
                            black[i].setPossibleMove(itr, new Pair(x-1,y-1));
                            board[x][y].pawn.setPossibleMove(itr, new Pair(x-1,y-1));

                        }
                    }
                }
            }
        }
    }

    private void updateAttackWhite(){
        boolean empty = true;
        int priority = 0;
        for (int i =0; i<12; i++)
        {
            if(white[i] != null){
                empty = false;
                int x = white[i].getCurrentPosition().getX();
                int y = white[i].getCurrentPosition().getY();
                white[i].setMoveOption();
                if (white[i].isKing())
                {
                    //TODO king move
                }
                else
                {
                    System.out.println(i);
                    priority = white[i].setPossibleAttack(possibleAttack(white[i].getCurrentPosition(),new LinkedList<Pair>()));
                    board[x][y].pawn = new Pawn (white[i]);
                    if(priority > 1){
                        attack.add(white[i]);
                    }
                }
            }
        }
        if(empty) {
            //TODO stop game
        }
    }

    private void updateAttackBlack(){
        boolean empty = true;
        int priority  =0;
        for (int i =0; i<12; i++)
        {
            if(black[i] != null){
                empty = false;
                int x = black[i].getCurrentPosition().getX();
                int y = black[i].getCurrentPosition().getY();
                board[x][y].pawn.setMoveOption();
                black[i].setMoveOption();
                if (black[i].isKing())
                {
                    //TODO king move
                }
                else
                {
                    System.out.println(i);
                    priority = black[i].setPossibleAttack(possibleAttack(black[i].getCurrentPosition(), new LinkedList<Pair>()));
                    board[x][y].pawn = new Pawn (black[i]);
                    if(priority > 1) {
                        attack.add(black[i]);
                    }
                }
            }
        }
        if(empty) {
            //TODO stop game
        }
    }

    private LinkedList<LinkedList<Pair>> possibleAttack(Pair pawn, LinkedList<Pair> deleted){
        int x = pawn.getX();
        int y = pawn.getY();
        LinkedList<LinkedList<Pair>> help,outPossibleAttack = null;
        if(y < 6)
        {
            if(x <6 && board[x+1][y+1].pawn != null && board[x+1][y+1].pawn.isWhite() != whiteTurn
                    && board[x+2][y+2].pawn == null && !deleted.contains(new Pair(x+1,y+1))) {
                deleted.addLast(new Pair(x+1,y+1));
                outPossibleAttack = possibleAttack(new Pair(x+2, y+2), deleted);
                deleted.removeLast();
            }

            if(x >1 && board[x-1][y+1].pawn != null && board[x-1][y+1].pawn.isWhite() != whiteTurn
                    &&  board[x-2][y+2].pawn == null && !deleted.contains(new Pair(x-1,y+1))){
                deleted.addLast(new Pair(x-1,y+1));
                help = possibleAttack(new Pair(x-2, y+2), deleted);
                deleted.removeLast();
                if(outPossibleAttack != null){
                    if(outPossibleAttack.get(0).size() == help.get(0).size()){
                        outPossibleAttack.addAll(help);
                    }else if (outPossibleAttack.get(0).size() < help.get(0).size()){
                        outPossibleAttack = help;
                    }
                }else{
                    outPossibleAttack = help;
                }
            }
        }

        if(y > 1){
            if(x >1 && board[x-1][y-1].pawn != null && board[x-1][y-1].pawn.isWhite() != whiteTurn
                    &&  board[x-2][y-2].pawn == null && !deleted.contains(new Pair(x-1,y-1))){
                deleted.addLast(new Pair(x-1,y-1));
                help = possibleAttack(new Pair(x-2, y-2),deleted);
                deleted.removeLast();
                if(outPossibleAttack != null){
                    if(outPossibleAttack.get(0).size() == help.get(0).size()){
                        outPossibleAttack.addAll(help);
                    }else if (outPossibleAttack.get(0).size() < help.get(0).size()){
                        outPossibleAttack = help;
                    }
                }else{
                    outPossibleAttack = help;
                }
            }

            if(x <6 && board[x+1][y-1].pawn != null && board[x+1][y-1].pawn.isWhite() != whiteTurn
                    &&  board[x+2][y-2].pawn == null&& !deleted.contains(new Pair(x+1,y-1))) {
                deleted.addLast(new Pair(x+1,y-1));
                help = possibleAttack(new Pair(x+2, y-2),deleted);
                deleted.removeLast();
                if(outPossibleAttack != null){
                    if(outPossibleAttack.get(0).size() == help.get(0).size()){
                        outPossibleAttack.addAll(help);
                    }else if (outPossibleAttack.get(0).size() < help.get(0).size()){
                        outPossibleAttack = help;
                    }
                }else{
                    outPossibleAttack = help;
                }
            }

        }
        if (outPossibleAttack == null){
            outPossibleAttack = new LinkedList<>();
            outPossibleAttack.add(new LinkedList<Pair>());
        }
        for (int i = 0; i<outPossibleAttack.size(); i++)
            outPossibleAttack.get(i).addFirst(pawn);
        System.out.println(outPossibleAttack);
        return outPossibleAttack;
    }

    private void showAttackOption(){
        Pawn option;
        int x, y;
        PriorityQueue<Pawn> newAttack = new PriorityQueue<>();
        do {
        option = attack.poll();
        x = option.getCurrentPosition().getX();
        y = option.getCurrentPosition().getY();
        board[x][y].setHighlight();
        newAttack.add(option);
    }while(option == attack.peek());
        attack = newAttack;
    }

    private void move(Pawn pawn, Pair destination){
        int x = pawn.getCurrentPosition().getX();
        int y = pawn.getCurrentPosition().getY();
        int dstX = destination.getX();
        int dstY = destination.getY();
        board[dstX][dstY].pawn = new Pawn(board[x][y].pawn);
        board[dstX][dstY].pawn.setCurrentPosition(destination);
        if((dstY == 7 && whiteTurn) || (dstY == 0 && !whiteTurn))
            board[dstX][dstY].pawn.setKing();
        board[dstX][dstY].image.setImageResource(PawnGraphics.get(whiteTurn, board[dstX][dstY].pawn.isKing()));
        if(whiteTurn) {
            int idx = idxOfPawn(white, pawn.getCurrentPosition());
            white[idx].setCurrentPosition(destination);
        }else{
            int idx = idxOfPawn(black, pawn.getCurrentPosition());
            black[idx].setCurrentPosition(destination);
        }
        deleteHighlightBoard();
        board[x][y].deletePawn();
    }

    private void deletePawn(Pawn pawn){
        int x = pawn.getCurrentPosition().getX();
        int y = pawn.getCurrentPosition().getY();
        board[x][y].deletePawn();
        if(whiteTurn) {
            int idx = idxOfPawn(black, pawn.getCurrentPosition());
            black[idx] = null;
        }else{
            int idx = idxOfPawn(white, pawn.getCurrentPosition());
            white[idx]= null;
        }
    }

    private int idxOfPawn(Pawn[] pawns, Pair position)
    {
        for(int i = 0; i< 12; i++)
        {
            if(pawns[i]!= null && position.equals(pawns[i].getCurrentPosition()))
                return i;
        }
        return -1;
    }
}
