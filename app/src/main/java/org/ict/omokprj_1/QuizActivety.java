package org.ict.omokprj_1;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

public class QuizActivety extends Activity {

    private final static String GAME_MODE = "game.mode";
    private QuizBaseFragment quizBaseFragment;

    ImageButton lv1,lv2,lv3;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_quiz);
        lv1 = (ImageButton)findViewById(R.id.level1);
        lv2 = (ImageButton)findViewById(R.id.level2);
        lv3 = (ImageButton)findViewById(R.id.level3);



        lv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }





}
