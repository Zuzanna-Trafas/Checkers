package org.pwanb.checkers.application;

import android.app.Activity;
import android.widget.ImageView;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;

class Board{
    private Pair chosenField;
    private Field[][] board = new Field[8][8];
    private Activity activity;

    class Field implements AppCompatImageView.OnClickListener {

        private ImageView image;
        private int X;
        private int Y;

        Field(int x, int y, ImageView img) {
            X = x;
            Y = y;
            image = img;
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int prevX = chosenField.getX();
            int prevY = chosenField.getY();
            System.out.println(X + "" + Y);
            if (chosenField.isSet()) {
                if (X != prevX || Y != prevY) {

                    board[prevX][prevY].deleteHighlight();
                    this.setHighlight();
                    chosenField.set(X, Y);
                } else {
                    this.deleteHighlight();
                    chosenField.unset();
                }
            } else {
                this.setHighlight();
                chosenField.set(X, Y);
            }
        }

        void deleteHighlight() {
            image.setBackground(null);
        }

        void setHighlight() {
            Drawable highlight = activity.getResources().getDrawable(R.drawable.highlight);
            image.setBackground(highlight);
        }

        void setImage(final int imageID) {
            image.setImageResource(imageID);
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
        for (int i = 0; i < 8; i += 2) {
            board[i][0].setImage(PawnGraphics.WHITE_PAWN.get());
            board[i + 1][1].setImage(PawnGraphics.WHITE_PAWN.get());
            board[i][2].setImage(PawnGraphics.WHITE_PAWN.get());
            board[i + 1][5].setImage(PawnGraphics.BLACK_PAWN.get());
            board[i][6].setImage(PawnGraphics.BLACK_PAWN.get());
            board[i + 1][7].setImage(PawnGraphics.BLACK_PAWN.get());
        }
    }
}







