package org.ict.omokprj_1;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class OmokFragment extends OmokBaseFragment {

    public static OmokFragment getInstance(){return new OmokFragment();}



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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

            return true;
        }
        return false;
    }


    @Override
    public int getFragmentId() {
        return R.layout.fragment_omok;
    }
}
