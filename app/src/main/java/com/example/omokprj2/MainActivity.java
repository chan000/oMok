package com.example.omokprj2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class MainActivity extends Activity {

    private final static String BUNDLE_FRAGMENT_ID = "gomokuFragment";
    ImageButton btnput;
    protected BoardView logic;

    private OmokPlayFragment omokFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateActiveFragment();



    }
        private void updateActiveFragment(){


            replaceFragment(OmokPlayFragment.getInstance());

        }

     private void replaceFragment(OmokPlayFragment fragment){
        this.omokFragment = fragment;
     }


}