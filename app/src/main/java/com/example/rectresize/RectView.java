package com.example.rectresize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class RectView extends View {
    int left=200,right=500,top=200,bottom=500;
    int horizontalDir = 0, verticalDir = 0;
    int detectThickness = 10,interactionWidth=50;
    boolean moveMode = false;
    public RectView(Context context) {
        super(context);
        init(null);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attributeSet){







    }

    @Override
    protected void onDraw(Canvas canvas) {

        Rect rect = new Rect(left,top,right,bottom);
        Paint paint = new Paint();
        paint.setStrokeWidth(5f);

        paint.setColor(Color.BLUE);

        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mouseX = event.getX();
        float mouseY = event.getY();
        int width = Math.abs(right - left);
        int height = Math.abs(top - bottom);
        int centerX = (left+right)/2;
        int centerY = (top+bottom)/2;
        switch(event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                int smaller, larger;
                moveMode = Math.abs(mouseX-centerX)/width<0.3f && Math.abs(mouseY-centerY)/height<0.3f;
                smaller = Math.min(left, right);
                larger = Math.max(left, right);
                horizontalDir = 0;
                if ((mouseX > smaller - 20 && mouseX < larger + 20)) {
                    if (Math.abs(mouseX - left) < Math.abs(mouseX - right)) {
                        if(Math.abs(mouseX-left)<30)
                        horizontalDir = -1;
                    } else {
                        if(Math.abs(mouseX-right)<30)
                        horizontalDir = 1;
                    }
                }
                smaller = Math.min(top, bottom);
                larger = Math.max(top, bottom);
                verticalDir = 0;
                if ((mouseY > smaller - 20 && mouseY < larger + 20)) {
                    if (Math.abs(mouseY - top) < Math.abs(mouseY - bottom)) {
                        if(Math.abs(mouseY-top)<30)
                        verticalDir = 1;
                    } else {
                        if(Math.abs(mouseY-bottom)<30)
                        verticalDir = -1;
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:


              if(moveMode){
                  left =(int) mouseX - width/2;
                  right =(int) mouseX + width/2;
                  top =(int) mouseY + height/2;
                  bottom =(int) mouseY - height/2;
              }else {
                  if (horizontalDir == -1) {
                      left = (int) mouseX;
                  } else if (horizontalDir == 1) {
                      right = (int) mouseX;
                  }

                  if (verticalDir == 1) {
                      top = (int) mouseY;
                  } else if (verticalDir == -1) {
                      bottom = (int) mouseY;
                  }
              }
              invalidate();
              return true;

        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
