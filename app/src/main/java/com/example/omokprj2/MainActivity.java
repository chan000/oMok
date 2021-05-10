package com.example.omokprj2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnput;
    protected BoardView logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnput = (ImageButton)findViewById(R.id.btnput);

        btnput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.playerMove1();
            }
        });
    }
}