package org.ict.omokprj_1;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public abstract class QuizBaseFragment extends BaseFragment{

    private final static int BOARD_SIZE = 15;

    private Callbacks callbacks;

    protected OmokLogic logic;
    protected int prevX, prevY;
    protected int curX, curY;
    protected boolean isUserMove;

    private Context context;




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        setupLogic();
        setupServiceButtons();

        QuizBoardView view = getBoardView();
        view.setBoardSize(BOARD_SIZE);
        view.setCallbacks(new QuizBoardView.Callbacks() {
            @Override
            public void onCellTouch(int x, int y) {
                if (logic.isGameOver()){
                    makeMove();
                }else{
                    curX = x;
                    curY = y;
                    getBoardView().setCursor(x,y,true);
                }
            }
        });

    }

    protected QuizBoardView getBoardView() {        //바둑판을 연결하는 부분
        return (QuizBoardView) getFragmentView().findViewById(R.id.QuizBoard);
    }

    private void setupServiceButtons(){     //기본적으로 다 들어가는 버튼을 정의하는 메서드
        View view = getFragmentView();
        ImageButton btnput = (ImageButton)view.findViewById(R.id.btnPut);
        btnput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////


    private void setupLogic(){//존나 이해가 안되네 하도 안되던게 여기 이거 if문 추가하니까 떳다 시발 왜그런거냐
        if (logic == null){
            logic = new OmokLogic(BOARD_SIZE);
            resetPositions();

            isUserMove = Math.random() > 0.5;
        }else{

        getBoardView().setCursor(curX, curY, false);
        }

        logic.setDelegate(new OmokLogic.Delegate() {
            @Override
            public void onGameOver(OmokLogic.WinState winState, OmokLogic.WinLineItem[] winLine) {
                getBoardView().setWinLine(winLine);
                if (callbacks != null){
                    callbacks.onGameOver();
                }
            }

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
        if (!logic.isGameOver()){

        logic.playerMove(curX, curY);
        }
    }

    private void resetPositions() {
        curX = curY = BOARD_SIZE / 2;
        prevX = prevY = -1;
        getBoardView().setCursor(curX, curY, false);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////
    public interface Callbacks {
        void onGameOver();
        void onMenu();
    }
    public void restart() {
        isUserMove = Math.random() > 0.5;
        resetPositions();
        logic.resetGame();
        getBoardView().resetBoard(logic.getBoard());
        makeMove();
    }

    protected boolean makeMove(){
        if (logic.isGameOver()) {
            restart();
            return false;
        }
        return true;
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

}
