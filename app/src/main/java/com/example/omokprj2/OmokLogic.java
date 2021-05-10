package com.example.omokprj2;

import java.io.Serializable;

public class OmokLogic implements Serializable {

    private int boardSize;

    public final static int NONE = 100;
    public final static int CROSS = 0;
    public final static int NOUGHT = 1;

    protected int[][] board;
    protected int player;         // The player whose move is next 다음 차례가 될 선수
    protected int totalLines;     // The number of empty lines left 남은 빈 줄의 수
    protected boolean isWon;      // Set if one of the players has won 플레이어중 한명이 이겼는지 설정
    protected int[][][][] line;   // Number of pieces in each of all possible lines 가능한 모든 라인의 조각 수     //금수를 추가할 수 있어
    protected int[][][] value;


    public OmokLogic(int boardSize) {
        this.boardSize = boardSize;
        board = new int[boardSize][boardSize];
        value = new int[boardSize][boardSize][2];
        line = new int[4][boardSize][boardSize][2];
        resetGame();
    }

    public void resetGame() {
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {      // Clear tables  비어있는 테이블
                board[i][j] = NONE;                     //예상- 앞의 두개배열은 테이블의 배열 다음 c는 돌의 statues
                for (int c = 0; c < 2; ++c) {           //돌의 구분
                    value[i][j][c] = 0;
                    for (int d = 0; d < 4; ++d) {       //5개가 연속으로 오는 돌을 확인하는 배열
                        line[d][i][j][c] = 0;
                    }
                }
            }
        }
        player = CROSS;      // cross starts
        // Total number of lines
        totalLines = 2 * 2 * (boardSize * (boardSize - 4) + (boardSize - 4) * (boardSize - 4));
        isWon = false;
    }
}
