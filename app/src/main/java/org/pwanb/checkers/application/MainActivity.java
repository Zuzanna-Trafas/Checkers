/**
 * ALL DAMN RIGHTS RESERVED. !!!! 2019 (R)
 */
package org.pwanb.checkers.application;



import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


/**
 * @author Codename "P.W.A.N.B"
 */

public class MainActivity extends AppCompatActivity {

        private ImageView[][] boardMain = new ImageView[8][8];
        protected Activity activity;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    String fieldID = "Field_" + i + "_" + j;
                    int resID = getResources().getIdentifier(fieldID, "id", getPackageName());
                    boardMain[i][j] = findViewById(resID);
                }
            }
            activity = this;
            Board ourBoard = new Board(boardMain, activity);

        }

}
