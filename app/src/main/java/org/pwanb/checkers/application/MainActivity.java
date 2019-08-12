/**
 * ALL DAMN RIGHTS RESERVED. !!!! 2019 (R)
 */
package org.pwanb.checkers.application;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;



/**
 * @author Codename "P.W.A.N.B"
 */

public class MainActivity extends AppCompatActivity {
    ImageView imv;
    boolean click = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
