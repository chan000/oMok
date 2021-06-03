package org.ict.omokprj_1;

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

public class QuizBoardView extends View implements View.OnTouchListener {
    private int boardSize = 15;     //바둑판 줄의 갯수
    private int[][] board;          //바둑판의 2차행렬
    private OmokLogic.WinLineItem[] winLine;


    private Paint paint;
    private Callbacks callbacks;
    private Bitmap grid;
    private Bitmap blueStone, redStone, cursor;

    private int prevX, prevY;
    private int curX, curY;

    private double scaleRate;

    public QuizBoardView(Context context) {
        super(context);
    }

    public QuizBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scaleRate = 0.0;
        blueStone = BitmapFactory.decodeResource(getResources(), R.drawable.blue_stone);
        redStone = BitmapFactory.decodeResource(getResources(), R.drawable.red_stone);
        cursor = BitmapFactory.decodeResource(getResources(),R.drawable.cursor);
        setOnTouchListener(this);
    }

    private static Bitmap scaleBitmap(Bitmap original, double rate) {   //바둑돌의 이미지를 조정하는 메서드
        int width = (int) (rate * original.getWidth());
        if (width % 2 == 0)
            width++;
        int height = (int) (rate * original.getHeight());
        if (height % 2 == 0)
            height++;
        return Bitmap.createScaledBitmap(original, width, height, true);
    }


    private Bitmap scaleBitmap(Bitmap original) {
        return scaleBitmap(original, scaleRate);
    }

    private void resizeImages(Canvas canvas) {
        if (scaleRate != 0.0)
            return;
        int cellSize = canvas.getHeight() / boardSize;
        double rate = 1.0 * cellSize / blueStone.getWidth();
        scaleRate = rate < 1 ? rate : 1;
        if (scaleRate < 1) {
            blueStone = scaleBitmap(blueStone);
            redStone = scaleBitmap(redStone);
            cursor = scaleBitmap(cursor);

        }
    }

    private Bitmap createGrid(int size) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);   //바둑돌의 크기를 정하는 이미지를 만든다.
        Canvas canvas = new Canvas(bitmap);                                         //그 비트맵 이미지를 캔버스 안에 넣는다(drawLine을 사용하기 위해서)
        canvas.drawARGB(150, 255, 255, 255);                            //흰색 배경에 대충 투명하단 뜻
        Paint pnt = new Paint();                            //선을 만들기 위해 paint선언
        pnt.setColor(Color.BLACK);                          //선의 색을 검정색으로
        pnt.setStyle(Paint.Style.STROKE);                   //선의 스타일
        pnt.setStrokeWidth(3);                              //스타일된 선의 굵기
        int cellSize = size / boardSize;                    //오목판 안의 작은 사각형 크기
        int halfSize = cellSize/2;                          //선들을 절반의 값만큼 옮겨야 오목돌이 라인 사이에 들어간다.
        canvas.save();                                  //translate또는 rotate와 같은 캔버스 자체를 변경시키는 메소드의 효과를 저장하는것이 canvas.save()메소드
        for ( int i = 0; i < boardSize; ++i ) {
            canvas.drawLine(halfSize, halfSize, halfSize, cellSize* boardSize -halfSize, pnt);
            canvas.translate(cellSize, 0);      // x선을 평행이동시키는 구문이지만 옆으로 canvas자체가 움직이는 것
        }
        canvas.restore();                           //다시 save 상태로 되돌리는 메서드 y지점을 다시 찍어야 하기 때문
        for ( int i = 0; i < boardSize; ++i ) {
            canvas.drawLine(halfSize, halfSize, cellSize* boardSize -halfSize, halfSize, pnt);
            canvas.translate(0, cellSize); // y선을 평행이동 시키는 구문
        }
        return bitmap;                  //완성된 비트맵을 리턴한다.
    }

    protected void drawStones(Canvas canvas){       //바둑판의 규격에 맞게 돌을 그릴 수 있게합니다.
        int cellSize = canvas.getHeight() / boardSize;

        for (int i = 0; i < boardSize; i++){
            float x = cellSize*i;
            for (int j = 0; j < boardSize; j++){
                if(board[i][j] == OmokLogic.NONE){
                    continue;
                }
                Bitmap bmp = board[i][j] == OmokLogic.CROSS ? blueStone : redStone;
                float y = cellSize*j;
                canvas.drawBitmap(bmp, x, y, paint);

            }
        }

    }
    protected void quizStones(Canvas canvas){
        int cellSize = canvas.getHeight() / boardSize;


//        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
        Bitmap bmp1 = blueStone;
        Bitmap bmp2 = redStone;
        canvas.drawBitmap(bmp1, cellSize*6,cellSize*7,paint);
        canvas.drawBitmap(bmp1, cellSize*9,cellSize*6,paint);
        canvas.drawBitmap(bmp1, cellSize*9,cellSize*7,paint);
        canvas.drawBitmap(bmp1, cellSize*9,cellSize*8,paint);
        canvas.drawBitmap(bmp1, cellSize*8,cellSize*8,paint);
        canvas.drawBitmap(bmp1, cellSize*7,cellSize*8,paint);
        canvas.drawBitmap(bmp1, cellSize*7,cellSize*9,paint);

        ////////////////////////////////////////////////////////////
        canvas.drawBitmap(bmp2, cellSize*7,cellSize*7,paint);
        canvas.drawBitmap(bmp2, cellSize*9,cellSize*9,paint);
        canvas.drawBitmap(bmp2, cellSize*6,cellSize*8,paint);
        canvas.drawBitmap(bmp2, cellSize*6,cellSize*9,paint);
        canvas.drawBitmap(bmp2, cellSize*6,cellSize*10,paint);
        canvas.drawBitmap(bmp2, cellSize*8,cellSize*9,paint);
        canvas.drawBitmap(bmp2, cellSize*10,cellSize*6,paint);
        for (int i = 0; i < boardSize; i++){
            float x = cellSize*i;
            for (int j = 0; j < boardSize; j++){


                float y = cellSize*j;

            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resizeImages(canvas);               //이미지를 넣을때 변화된 이미지를 넣게한다.

        if (grid == null)
            grid = createGrid(getWidth());
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(grid, 0, 0, paint);
        drawStones(canvas);
        quizStones(canvas);
        showCursor(canvas);
    }



    private void showCursor(Canvas canvas){
        int cellSize = canvas.getHeight() / boardSize;
        int offset = (cellSize - cursor.getWidth()) / 2;
        float posX = cellSize*curX + offset;
        float posY = cellSize*curY + offset;
        canvas.drawBitmap(cursor, posX, posY, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { //view의 크기를 정하는 메서드이다
        int size = Math.min(widthMeasureSpec, heightMeasureSpec);
        //widthMeasureSpec은 부모컨테이너의 가로, height는 세로를 의미한다. 이 크기의 값으로 ondraw에서 그린다.
        super.onMeasure(size, size);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if ( event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_MOVE ) {
            int cellSize = v.getHeight() / boardSize;
            int x = (int) (event.getX() / cellSize);
            int y = (int) (event.getY() / cellSize);
            if (callbacks!=null && x < boardSize && y < boardSize && x >= 0 && y >= 0 )
                callbacks.onCellTouch(x,y);

        }
        return false;
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

    public void resetBoard(int[][] board){
        setBoard(board, -1, -1);
    }

    public void setWinLine(OmokLogic.WinLineItem[] winLine){
        this.winLine = winLine;
        invalidate();
    }

    public void setCallbacks(int boardSize){this.boardSize = boardSize;}

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    public void setCallbacks(Callbacks callbacks){
        this.callbacks = callbacks;
    }

    public interface Callbacks{
        void onCellTouch(int x, int y);
    }
}
