package org.pwanb.checkers.application;

import android.app.Activity;
import android.widget.ImageView;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;

class Board{
    private boolean whiteTurn;
    private Pair chosenField;
    private Field[][] board = new Field[8][8];
    private Activity activity;
    private Pawn[] white = new Pawn[12];
    private Pawn[] black = new Pawn[12];

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
        }

        void deleteHighlight() { image.setBackground(null); }

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
            if (pawn != null)
                firstClick();
            else if (chosenField.isSet())
                secondClick();

        }

        private void firstClick() {
            int prevX = chosenField.getX();
            int prevY = chosenField.getY();
            int X = position.getX();
            int Y = position.getY();
            if (pawn.isWhite() == whiteTurn) {
                if (chosenField.isSet()) {
                    if (X != prevX || Y != prevY) {
                        board[prevX][prevY].deleteHighlight();
                        board[prevX][prevY].deleteOption();
                        setHighlight();
                        chosenField.set(X, Y);
                        showOption();
                    } else {
                        deleteHighlight();
                        deleteOption();
                        chosenField.unset();
                    }
                } else {
                    setHighlight();
                    chosenField.set(X, Y);
                    showOption();
                }
            }
        }

        private void secondClick() {
            int prevX = chosenField.getX();
            int prevY = chosenField.getY();
            int X = position.getX();
            int Y = position.getY();
            for (int i = 0; i < board[prevX][prevY].pawn.getMoveOption(); i++)
            {
                if (board[prevX][prevY].pawn.getPossibleMove()[i].isEqual(position)){
                    move(board[prevX][prevY].pawn, position);
                    chosenField.unset();
                    whiteTurn = !whiteTurn;
                    possibleMoves();
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

        void deleteOption(){
            int x;
            int y;
            for(int i = 0 ; i< pawn.getMoveOption(); i++)
            {
                x = pawn.getPossibleMove()[i].getX();
                y = pawn.getPossibleMove()[i].getY();
                board[x][y].deleteHighlight();
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
        this.possibleMoves();
    }

    private void possibleMoves(){
        if(whiteTurn)
            updateWhite();
        else
            updateBlack();
    }

    private void updateWhite(){
        boolean empty = true;

        for (int i =0; i<12; i++)
        {
            if(white[i] != null){
                empty = false;
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
                        if(x<7) {
                            if(board[x+1][y+1].pawn == null)
                            {

                                white[i].setPossibleMove(itr, new Pair(x+1,y+1));
                                board[x][y].pawn.setPossibleMove(itr, new Pair(x+1,y+1));
                                itr++;
                            }
                            else{
                                //TODO attack option
                                // wyslc do innej listy o wyzszym priorytecie
                            }
                        }
                        if(x > 0){
                            if(board[x-1][y+1].pawn == null)
                            {
                                white[i].setPossibleMove(itr, new Pair(x-1,y+1));
                                board[x][y].pawn.setPossibleMove(itr, new Pair(x-1,y+1));
                            }
                            else{
                                //TODO attack option
                                // wyslc do innej listy o wyzszym priorytecie
                            }
                        }
                    }

                    if(y > 0)
                    {
                        if(x<7 && board[x+1][y-1].pawn != null && board[x+1][y-1].pawn.isWhite() != whiteTurn) {

                                //TODO attack option
                                // wyslc do innej listy o wyzszym priorytecie
                        }
                        if(x>0 && board[x-1][y-1].pawn != null && board[x-1][y-1].pawn.isWhite() != whiteTurn) {

                            //TODO attack option
                            // wyslc do innej listy o wyzszym priorytecie
                        }

                    }
                }
            }
        }
        if(empty) {
            //TODO stop game
        }
    }

    private void updateBlack(){
        boolean empty = true;

        for (int i =0; i<12; i++)
        {
            if(black[i] != null){
                empty = false;
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
                        if(x<7) {
                            if(board[x+1][y-1].pawn == null)
                            {

                                black[i].setPossibleMove(itr, new Pair(x+1,y-1));
                                board[x][y].pawn.setPossibleMove(itr, new Pair(x+1,y-1));
                                itr++;
                            }
                            else{
                                //TODO attack option
                                // wyslc do innej listy o wyzszym priorytecie
                            }
                        }
                        if(x > 0){
                            if(board[x-1][y-1].pawn == null)
                            {
                                black[i].setPossibleMove(itr, new Pair(x-1,y-1));
                                board[x][y].pawn.setPossibleMove(itr, new Pair(x-1,y-1));
                            }
                            else{
                                //TODO attack option
                                // wyslc do innej listy o wyzszym priorytecie
                            }
                        }
                    }

                    if(y < 7)
                    {
                        if(x<7 && board[x+1][y+1].pawn != null && board[x+1][y+1].pawn.isWhite() != whiteTurn) {

                            //TODO attack option
                            // wyslc do innej listy o wyzszym priorytecie
                        }
                        if(x>0 && board[x-1][y+1].pawn != null && board[x-1][y+1].pawn.isWhite() != whiteTurn) {

                            //TODO attack option
                            // wyslc do innej listy o wyzszym priorytecie
                        }

                    }
                }
            }
        }
        if(empty) {
            //TODO stop game
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
        board[x][y].deleteOption();
        board[x][y].deleteHighlight();
        board[x][y].deletePawn();


    }

    private int idxOfPawn(Pawn[] pawns, Pair position)
    {
        for(int i = 0; i< 12; i++)
        {
            if(pawns[i]!= null && position.isEqual(pawns[i].getCurrentPosition()))
                return i;

        }
        return -1;
    }
}







