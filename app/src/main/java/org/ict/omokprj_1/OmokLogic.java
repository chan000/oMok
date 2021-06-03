package org.ict.omokprj_1;

import android.app.Application;
import android.content.Context;
import android.util.Pair;
import android.widget.Toast;

import java.io.Serializable;

public class OmokLogic implements Serializable {


    public static class WinLineItem extends Pair<Integer, Integer>{
        public WinLineItem(Integer first, Integer second){super(first,second);}
    }

    public enum WinState {

        DRAW,
        CROSS_WIN,
        NOUGHT_WIN,
        CONTINUES

    }

    private enum Win {
        NULL,
        HORIZONTAL,
        DOWN_LEFT,
        DOWN_RIGHT,
        VERTICAL
    }
    public interface Delegate {
        void onGameOver(WinState winState, WinLineItem[] winLine);
        void onMoveComplete(int x, int y);
    }





    private transient Delegate delegate;

    private int boardSize;

    private final static int[] WEIGHT = {0, 0, 4, 20, 100, 500, 0};

    public final static int NONE = 100;             //모든 칸이 비어있을때
    public final static int CROSS = 0;              //1번 플레이어
    public final static int NOUGHT = 1;             //상대 플레이어

    protected int [][] board;         //바둑판 배열
    protected int player;         // 다음 차례가 될 선수
    protected int totalLines;     //  남은 빈 줄의 수
    protected boolean isWon;      //  플레이어중 한명이 이겼는지 설정
    protected int[][][][] line;   //  가능한 모든 라인의 조각 수     //금수를 추가할 수 있어
    protected int[][][] value;      //돌의 속성을 구분하기 위한 배열


    public OmokLogic(int boardSize){
        //오목 로직

        this.boardSize = boardSize;
        board = new int[boardSize][boardSize];  //보드판
        value = new int[boardSize][boardSize][2];   //흑백 구분
        line = new int[4][boardSize][boardSize][2];     //5개의 나열을 확인하기 위해
        resetGame();
       
    }



    public void resetGame(){
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                board[i][j] = NONE;             //모드 안의 모든 값에 100의 값을 넣는다.
                for (int a = 0; a < 2; a++){
                    value[i][j][a] = 0;
                    for (int b = 0; b < 4; b++){
                        line[b][i][j][a] = 0;               //행렬과 배열을 초기화 하는 작업
                    }
                }
            }
        }
        player = CROSS;     //cross 시작
        totalLines = 2 * 2 * (boardSize * (boardSize - 4) + (boardSize - 4) * (boardSize - 4));
        isWon = false;

    }
    public boolean isGameOver(){
        return isWon || totalLines <= 0;//모든 라인이 0이 되거나 iswon이 true면 종료한다.
    }
    private boolean isValidRange(int x, int rate) {
        int val = x - 4*rate;
        int min = Math.min(x, val);
        int max = Math.max(x, val);
        return ( min >= 0 ) && ( max < boardSize);
    }

    private Win calculateWinLine(int x, int xRate, int y, int yRate, int position, Win winDirection, Win winningLine) {
        Win result = winningLine;
        int opponent = getOpponent();
        for (int k = 0; k < 5; ++k ) {
            int x1 = x + xRate*k;
            int y1 = y + yRate*k;
            if ( isValidRange(x1, xRate) && isValidRange(y1, yRate) ) {
                int[] ln = line[position][x1][y1];
                ln[player]++;
                int val = ln[player];
                if ( val == 1 ) {
                    totalLines--;
                } else if ( val == 5 ) {
                    isWon = true;
                }
                if ( isWon && winningLine == Win.NULL ) {
                    result = winDirection;
                }

                for ( int l = 0; l < 5; ++l ) {

                    int vx = x1 - xRate*l;
                    int vy = y1 - yRate*l;
                    if ( ln[opponent] == 0 ) {

                        value[vx][vy][player] += WEIGHT[ln[player]+1] - WEIGHT[ln[player]];
                    } else if ( ln[player] == 1 ) {

                        value[vx][vy][opponent] -= WEIGHT[ln[opponent]+1];
                    }
                }
            }
        }
        return result;
    }



    private void makeMove(int x, int y){
        Win winningLine = Win.NULL;

        winningLine = calculateWinLine(x, -1, y,  0, 0, Win.HORIZONTAL, winningLine);
        winningLine = calculateWinLine(x, -1, y, -1, 1, Win.DOWN_LEFT, winningLine);
        winningLine = calculateWinLine(x,  1, y, -1, 3, Win.DOWN_RIGHT, winningLine);
        winningLine = calculateWinLine(x,  0, y, -1, 2, Win.VERTICAL, winningLine);


        board[x][y] = player;

        if ( isGameOver() ) {
            WinLineItem[] highlightLine = null;
            WinState winState = player == CROSS ? WinState.CROSS_WIN : WinState.NOUGHT_WIN;
            if ( isWon ) {
                highlightLine = getWinningLine(x, y, winningLine);
            } else {
                winState = WinState.DRAW;
            }
            //if ( delegate != null )
            delegate.onGameOver(winState, highlightLine);
        } else {
            player = getOpponent();
            //if ( delegate != null )
            delegate.onMoveComplete(x, y);
        }
    }

    private WinLineItem[] getWinningLine(int x, int y, Win winDirection) {
        int dx = 0, dy = 0;
        switch ( winDirection ) {
            case HORIZONTAL:
                dx = 1;
                dy = 0;
                break;
            case DOWN_LEFT:
                dx = 1;
                dy = 1;
                break;
            case VERTICAL:
                dx = 0;
                dy = 1;
                break;
            case DOWN_RIGHT:
                dx = -1;
                dy = 1;
                break;
        }
        while ( x >= 0 && x < boardSize &&
                y >= 0 && y < boardSize &&
                board[x][y] == player ) {
            x += dx;
            y += dy;
        }
        WinLineItem[] winCells = new WinLineItem[5];
        for ( int i = 0; i < 5; ++i) {
            x-=dx;
            y-=dy;
            winCells[i] = new WinLineItem(x, y);
        }
        return winCells;
    }


    private int getOpponent(){return player == CROSS ? NOUGHT : CROSS;}



    public void playerMove(int x, int y) {
        if ( board[x][y] == NONE ) {
            makeMove(x, y);
        }
    }


    public Delegate getDelegate(){return delegate;}

    public void setDelegate(Delegate delegate){this.delegate = delegate;}

    public int[][] getBoard() {
        return board;
    }
}
