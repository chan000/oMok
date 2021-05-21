package org.ict.omokprj_1;

import java.io.Serializable;

public class OmokLogic implements Serializable {


    public interface Delegate {
        void onMoveComplete(int x, int y);
    }

    private transient Delegate delegate;

    private int boardSize;

    public final static int NONE = 100;             //모든 칸이 비어있을때
    public final static int CROSS = 0;              //1번 플레이어
    public final static int NOUGHT = 1;             //상대 플레이어

    protected int [][] board;         //바둑판 배열
    protected int player;         // 다음 차례가 될 선수
    protected int totalLines;     //  남은 빈 줄의 수
    protected boolean isWon;      //  플레이어중 한명이 이겼는지 설정
    protected int[][][][] line;   //  가능한 모든 라인의 조각 수     //금수를 추가할 수 있어
    protected int[][][] value;      //돌의 속성을 구분하기 위한 배열


    public OmokLogic(int boardSize){            //오목 로직
        this.boardSize = boardSize;
        board = new int[boardSize][boardSize];  //보드판
        value = new int[boardSize][boardSize][2];   //흑백 구분
        line = new int[4][boardSize][boardSize][2];     //5개의 나열을 확인하기 위해
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

    private void makeMove(int x, int y){
        board[x][y] = player;

        player = getOpponent();
        delegate.onMoveComplete(x,y);
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
