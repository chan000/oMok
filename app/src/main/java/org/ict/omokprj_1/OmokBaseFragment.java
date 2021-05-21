package org.ict.omokprj_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

public abstract class OmokBaseFragment extends BaseFragment{

    private final static int BOARD_SIZE = 15;

    private Callbacks callbacks;

    protected OmokLogic logic;
    protected int prevX, prevY;
    protected int curX, curY;
    protected boolean isUserMove;




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        setupLogic();
        setupServiceButtons();

        BoardView view = getBoardView();
        view.setBoardSize(BOARD_SIZE);
        view.setCallbacks(new BoardView.Callbacks() {
            @Override
            public void onCellTouch(int x, int y) {
               playerMove();
            }
        });

    }

    protected BoardView getBoardView() {
        return (BoardView) getFragmentView().findViewById(R.id.gameBoard);
    }

    private void setupServiceButtons(){
        View view = getFragmentView();
        ImageButton btnput = (ImageButton)view.findViewById(R.id.btnPut);
        btnput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerMove();
            }
        });
        ImageButton btnMenu = (ImageButton)view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callbacks != null){
                    callbacks.onMenu();
                }
            }
        });

    }




    private void setupLogic(){
        getBoardView().setCursor(curX, curY, false);

        logic.setDelegate(new OmokLogic.Delegate() {
            @Override
            public void onMoveComplete(int x, int y) {
                isUserMove = !isUserMove;
                prevX = x;
                prevY = y;
                makeMove();
            }
        });
    }


    public void playerMove(){
        logic.playerMove(curX, curY);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////
    public interface Callbacks {
        void onGameOver();
        void onMenu();
    }

    protected boolean makeMove(){
        return true;
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

}
