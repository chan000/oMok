package org.ict.omokprj_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class OmokQuizFragment extends QuizBaseFragment {

    public static OmokQuizFragment getInstance(){return new OmokQuizFragment();}

    @Override
    public int getFragmentId() {
        return R.layout.fragment_quiz_omok;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getFragmentView();
    }

    @Override
    public void onResume(Preferences preferences) {
        super.onResume(preferences);
        makeMove();

    }

    @Override
    protected boolean makeMove() {
        if (super.makeMove()) {
            getBoardView().setBoard(logic.getBoard(), prevX, prevY);
            updatePutButtonStates();
            return true;
        }
        return false;
    }
    private void updatePutButtonStates() {
        View view = getFragmentView();
        ImageButton btnFirst = (ImageButton)view.findViewById(R.id.btnPut);

    }


}
