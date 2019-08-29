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

        void setHighlightOrange() {
            Drawable highlight = activity.getResources().getDrawable(R.drawable.highlight_organe);
            image.setBackground(highlight);
            highlights.add(new Pair (position.getX(),position.getY()));
        }

        void setHighlightRed() {
            Drawable highlight = activity.getResources().getDrawable(R.drawable.highlight_red);
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
                        setHighlightOrange();
                        chosenField.set(X, Y);
                        showOptionForMove();
                    } else {
                        deleteHighlightBoard();
                        chosenField.unset();
                    }
                } else {
                    setHighlightOrange();
                    chosenField.set(X, Y);
                    showOptionForMove();
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

        void showOptionForMove(){
            int x;
            int y;
            for(int i = 0 ; i< pawn.getMoveOption(); i++)
            {
                x = pawn.getPossibleMove()[i].getX();
                y = pawn.getPossibleMove()[i].getY();
                board[x][y].setHighlightOrange();
            }
        }

        private void attackFirstClick(){
            int prevX = chosenField.getX();
            int prevY = chosenField.getY();
            int X = position.getX();
            int Y = position.getY();
            if (attack.contains(pawn)) {
                if (chosenField.isSet()) {
                    if (X != prevX || Y != prevY) {
                        deleteHighlightBoard();
                        showAttackOption();
                        setHighlightOrange();
                        chosenField.set(X, Y);
                        showOptionForAttack();
                    } else {
                        deleteHighlightBoard();
                        showAttackOption();
                        chosenField.unset();
                    }
                } else {
                    setHighlightOrange();
                    chosenField.set(X, Y);
                    showOptionForAttack();
                }
            }
        }

        private void attackSecondClick(){  //TODO multi attack
            int prevX = chosenField.getX();
            int prevY = chosenField.getY();
            for (LinkedList<Pair> path : board[prevX][prevY].pawn.getPossibleAttack() )
            {
                if (path.get(1).equals(position)){
                    attack(board[prevX][prevY].pawn, position);


                }
            }
            //zmiana gracza
            chosenField.unset();
            whiteTurn = !whiteTurn;
            deleteHighlightBoard();
            attack.clear();
            possibleAction();
        }

        void showOptionForAttack(){
            int x,y;
            for(LinkedList<Pair> path: pawn.getPossibleAttack()) {
                x = path.get(1).getX();
                y = path.get(1).getY();
                board[x][y].setHighlightOrange();
            }
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
        while(highlights.peek() != null){
            highlight = highlights.poll();
            x = highlight.getX();
            y = highlight.getY();
            board[x][y].deleteHighlightField();
        }
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
            updateAttack(white);
            if(attack.size() > 0)
                showAttackOption();
            else
                updateMove(white);
            System.out.println(allMoves(white));
        }
        else {
            updateAttack(black);
            if(attack.size() > 0)
                showAttackOption();
            else
                updateMove(black);
            System.out.println(allMoves(black));
        }

    }

    private void updateMove(Pawn[] pawns){
        for (int i =0; i<12; i++)
        {
            if(pawns[i] != null)
            {
                int x = pawns[i].getCurrentPosition().getX();
                int y = pawns[i].getCurrentPosition().getY();
                int itr = 0;
                board[x][y].pawn.setMoveOption();
                pawns[i].setMoveOption();
                if (whiteTurn)
                {
                    if (pawns[i].isKing())
                    {
                        //TODO king move
                    } else {
                        if (y < 7)
                        {
                            if (x < 7 && board[x + 1][y + 1].pawn == null)
                            {
                                pawns[i].setPossibleMove(itr, new Pair(x + 1, y + 1));
                                board[x][y].pawn.setPossibleMove(itr, new Pair(x + 1, y + 1));
                                itr++;
                            }
                            if (x > 0 && board[x - 1][y + 1].pawn == null)
                            {
                                pawns[i].setPossibleMove(itr, new Pair(x - 1, y + 1));
                                board[x][y].pawn.setPossibleMove(itr, new Pair(x - 1, y + 1));
                            }
                        }
                    }
                } else {
                    if (pawns[i].isKing())
                    {
                        //TODO king move
                    } else
                        {
                        if(y > 0)
                        {
                            if(x<7 && board[x+1][y-1].pawn == null)
                            {
                                pawns[i].setPossibleMove(itr, new Pair(x+1,y-1));
                                board[x][y].pawn.setPossibleMove(itr, new Pair(x+1,y-1));
                                itr++;
                            }
                            if(x > 0 && board[x-1][y-1].pawn == null)
                            {
                                pawns[i].setPossibleMove(itr, new Pair(x-1,y-1));
                                board[x][y].pawn.setPossibleMove(itr, new Pair(x-1,y-1));
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateAttack(Pawn[] pawns){
        boolean empty = true;
        int priority;
        for (int i =0; i<12; i++)
        {
            if(pawns[i] != null){
                empty = false;
                int x = pawns[i].getCurrentPosition().getX();
                int y = pawns[i].getCurrentPosition().getY();
                pawns[i].setMoveOption();
                if (pawns[i].isKing())
                {
                    //TODO king move
                }
                else
                {
                    System.out.println(i);
                    priority = pawns[i].setPossibleAttack(possibleAttack(pawns[i].getCurrentPosition(),new LinkedList<Pair>()));
                    board[x][y].pawn = new Pawn (pawns[i]);
                    if(priority > 1){
                        attack.add(pawns[i]);
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

    private void choseAttackOption(){
        Pawn option = null;
        PriorityQueue<Pawn> newAttack = new PriorityQueue<>();
        while(attack.peek() != null){
            if(option != null && !option.equals(attack.peek()))
                break;
            option = attack.poll();
            newAttack.add(option);
        }
        attack = newAttack;
    }

    private void showAttackOption(){
        int x, y;
        choseAttackOption();
        for (Pawn pawn : attack) {
            x = pawn.getCurrentPosition().getX();
            y = pawn.getCurrentPosition().getY();
            board[x][y].setHighlightRed();
        }
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

    private void attack(Pawn pawn, Pair destination){
        int x = (pawn.getCurrentPosition().getX() + destination.getX())/2;
        int y = (pawn.getCurrentPosition().getY() +  destination.getY())/2;
        move(pawn,destination);
        delete(new Pair(x,y));
    }

    private void delete(Pair pawn){
        int x = pawn.getX();
        int y = pawn.getY();
        board[x][y].deletePawn();
        if(whiteTurn) {
            int idx = idxOfPawn(black, pawn);
            black[idx] = null;
        }else{
            int idx = idxOfPawn(white, pawn);
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

    private LinkedList<Pawn> allMoves(Pawn[] pawns){
        LinkedList<Pawn> allMoves;
        if(attack.size() > 0){
            choseAttackOption();
            allMoves = new LinkedList<>(attack);
        } else {
            allMoves = new LinkedList<>();
            for(int i = 0; i < 12; i++){
                if(pawns[i] != null && pawns[i].getMoveOption() > 0){
                    allMoves.add(new Pawn(pawns[i]));
                }
            }
        }
        return allMoves;
    }
}
