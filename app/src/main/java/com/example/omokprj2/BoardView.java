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
    private Bitmap cursor, blueStone;


    private int curX, curY;

    private double scaleRate;

    public BoardView(Context context){super(context);}

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cursor = BitmapFactory.decodeResource(getResources(), R.drawable.cursor);
        blueStone = BitmapFactory.decodeResource(getResources(), R.drawable.blue_stone);
        setOnTouchListener(this);
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
            Toast.makeText(this.getContext().getApplicationContext(), curX+"", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resizeImages(canvas);
        if (grid == null){
            grid = createGrid(getWidth());
        }
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(grid, 0, 0, paint);
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





}
