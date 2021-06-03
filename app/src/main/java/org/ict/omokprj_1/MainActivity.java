package org.ict.omokprj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {


    private final static String BUNDLE_FRAGMENT_ID = "OmokFragment";
    private final static String GAME_MODE = "game.mode";
   private OmokBaseFragment omokbaseFragment;
   private QuizBaseFragment quizBaseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateActiveFragment();
    }

    private void setQuizMode(long mode){
        Preferences pref = new Preferences(this);
        pref.putLong(GAME_MODE, mode);
        updateActiveQuizFragment();
    }

    private void updateActiveQuizFragment() {
        Preferences pref = new Preferences(this);
        long mode = pref.getLong(GAME_MODE, 0);

        QuizBaseFragment fragment1 = OmokQuizFragment.getInstance();


        replaceQuizFragment(fragment1);


    }

    private void replaceQuizFragment(QuizBaseFragment fragment) {
        this.quizBaseFragment = fragment;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container12, fragment)
                .commit();

            quizBaseFragment.setCallbacks(new QuizBaseFragment.Callbacks() {
                @Override
                public void onGameOver() {
                    //
                }

                @Override
                public void onMenu() {
                    showMenuDialog();
                }
            });
    }



    private void setActiveMode(long mode) {
        Preferences pref = new Preferences(this);
        pref.putLong(GAME_MODE, mode);
        updateActiveFragment();
    }



    private void updateActiveFragment() {
        Preferences pref = new Preferences(this);
        long mode = pref.getLong(GAME_MODE, 0);
        OmokBaseFragment fragment =
                OmokFragment.getInstance();


        replaceFragment(fragment);



    }




    private void replaceFragment(OmokBaseFragment fragment){
        this.omokbaseFragment = fragment;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container12, fragment)
                .commit();

        omokbaseFragment.setCallbacks(new OmokBaseFragment.Callbacks() {
            @Override
            public void onGameOver() {
                //
            }

            @Override
            public void onMenu() {
                showMenuDialog();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }




    private void showMenuDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_mode, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        {
            Button btnTwoPlayers = (Button) view.findViewById(R.id.buttonTwoPlayers);
            btnTwoPlayers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setActiveMode(0);
                    dialog.dismiss();
                }
            });

            Button btnQuiz = (Button) view.findViewById(R.id.buttonQuiz);
            btnQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(dialog.getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View view1 = inflater.inflate(R.layout.fragment_quiz, null);
                    builder.setView(view1);
                    Dialog dialog1 = builder.create();
                    {
                        ImageButton lv1 = (ImageButton) view1.findViewById(R.id.level1);
                        lv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                setQuizMode(0);
                                dialog1.dismiss();
                            }
                        });
                    }
                    dialog1.show();
                    dialog.dismiss();
                }
            });
        }
        Button btnAddAccount = (Button) view.findViewById(R.id.addPlayer);
        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddAccount.class);
                startActivity(i);
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}