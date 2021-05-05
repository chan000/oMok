package org.ict.omokprj_1;

import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class omokBaseFragment extends BaseFragment{

    private final static int BOARD_SIZE = 15;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        BoardView view = getBoardView();
        view.setBoardSize(BOARD_SIZE);
    }

    protected BoardView getBoardView() {
        return (BoardView) getFragmentView().findViewById(R.id.gameBoard);
    }


}
