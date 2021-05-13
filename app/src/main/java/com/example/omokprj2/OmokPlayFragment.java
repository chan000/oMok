package com.example.omokprj2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class OmokPlayFragment extends BaseFragment{

    public static OmokPlayFragment getInstance() {
        return new OmokPlayFragment();
    }

    protected BoardView logic;
    @Override
    public int getFragmentId() {
        return R.layout.activity_main;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getFragmentView();
        ImageButton btnput = (ImageButton) view.findViewById(R.id.btnput);

        btnput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.playerMove1();
                Toast.makeText(getContext().getApplicationContext(),"버튼은 먹습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
