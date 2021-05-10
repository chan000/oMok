package com.example.omokprj2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BoardView extends View implements View.OnTouchListener {

    private int boardSize = 15;
    private int [][] board;

    private Paint paint;
    private Bitmap grid;
    private Bitmap cursor, blueStone, redStone;

    public final static int NONE = 100;
    public final static int CROSS = 0;
    public final static int NOUGHT = 1;


    protected int player;         // The player whose move is next 다음 차례가 될 선수
    protected int totalLines;     // The number of empty lines left 남은 빈 줄의 수
    protected boolean isWon;      // Set if one of the players has won 플레이어중 한명이 이겼는지 설정
    protected int[][][][] line;   // Number of pieces in each of all possible lines 가능한 모든 라인의 조각 수     //금수를 추가할 수 있어
    protected int[][][] value;    // Value of each square for each player 각 플레이어의 제곱값



    private int curX, curY;
    private int prevX, prevY;

    private double scaleRate;

    public BoardView(Context context){super(context);}

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cursor = BitmapFactory.decodeResource(getResources(), R.drawable.cursor);
        blueStone = BitmapFactory.decodeResource(getResources(), R.drawable.blue_stone);
        redStone = BitmapFactory.decodeResource(getResources(), R.drawable.red_stone);
        setOnTouchListener(this);
    }



    public void resetGame() {
        board = new int[boardSize][boardSize];
        value = new int[boardSize][boardSize][2];
        line = new int[4][boardSize][boardSize][2];

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

    private static Bitmap scaleBitmap(Bitmap original, double rate) {
        int width = (int)(rate * original.getWidth());
        if ( width%2==0)
            width++;
        int height = (int)(rate * original.getHeight());
        if (height%2==0)
            height++;
        return Bitmap.createScaledBitmap(original, width, height, true);
    }

    private Bitmap scaleBitmap(Bitmap original) {
        return scaleBitmap(original, scaleRate);
    }

    private void resizeImages(Canvas canvas) {
        if ( scaleRate != 0.0 )
            return;
        int cellSize = canvas.getHeight() / boardSize;
        double rate = 1.0*cellSize / blueStone.getWidth();
        scaleRate = rate < 1 ? rate : 1;
        if ( scaleRate < 1 ) {

            cursor = scaleBitmap(cursor);
            blueStone = scaleBitmap(blueStone);
            redStone = scaleBitmap(redStone);
        }
    }

    private Bitmap createGrid(int size){
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(150, 255,255,255);
        Paint line = new Paint();
        line.setColor(Color.BLACK);
        line.setStyle(Paint.Style.STROKE);
        line.setStrokeWidth(3);
        int cellSize = size / boardSize;
        int halfSize = cellSize / 2;
        canvas.save();
        for (int i = 0; i < boardSize; i++){
            canvas.drawLine(halfSize, halfSize, halfSize, cellSize * boardSize - halfSize, line);
            canvas.translate(cellSize, 0);
        }
        canvas.restore();
        for (int i = 0; i < boardSize; i++){
            canvas.drawLine(halfSize, halfSize, cellSize * boardSize - halfSize, halfSize, line);
            canvas.translate(0, cellSize);
        }

        return bitmap;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if ( event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_MOVE ) {
            int cellSize = v.getHeight() / boardSize;
            curX = (int) (event.getX() / cellSize);
            curY = (int) (event.getY() / cellSize);
//            Toast.makeText(this.getContext().getApplicationContext(), curX+"", Toast.LENGTH_SHORT).show();
        }
        invalidate();
        return true;

    }
    private void showCursor(Canvas canvas){
        int cellSize = canvas.getHeight() / boardSize;
        int offset = (cellSize - cursor.getWidth()) / 2;
        float posX = cellSize*curX + offset;
        float posY = cellSize*curY + offset;
        canvas.drawBitmap(cursor, posX, posY, paint);

    }
    protected void drawStones(Canvas canvas){
        resetGame();
        int cellSize = canvas.getHeight() / boardSize;
        int offset = (cellSize - blueStone.getWidth()) / 2;
        for (int i = 0; i < boardSize; i++){
            float x = cellSize*i + offset;
            for (int j = 0; j < boardSize; j++){
                if ( board[i][j] == NONE ) {         //2차 행렬안에 돌이 없으면 실행한다.
                    continue;
                }else{
                    Bitmap bmp = board[i][j] == CROSS ? blueStone : redStone;
                float y = cellSize*j + offset;
                canvas.drawBitmap(bmp, x, y, paint);
                }
            }
        }
    }
    private void makeMove(int x, int y){

        board[x][y] = player;
    }
    public void playerMove(int x, int y){
        if (board[x][y] == NONE){
           makeMove(x,y);
        }
    }
    public void playerMove1(){
        playerMove(curX,curY);
    }

    private int getOpponent() {
        return player == CROSS ? NOUGHT : CROSS;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resizeImages(canvas);
        if (grid == null){
            grid = createGrid(getWidth());
        }
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(grid, 0, 0, paint);
        drawStones(canvas);
       showCursor(canvas);

    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(size, size);
    }

    public void setCursor(int x, int y, boolean invalidate) {
        curX = x;
        curY = y;
        if ( invalidate )
            invalidate();
    }

    public void setBoard(int[][] board, int x, int y) {
        this.prevX = x;
        this.prevY = y;
        this.board = board;
        invalidate();
    }




}
