package com.example.manoj.musicplayer;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Manoj on 7/28/2017.
 */

public class OnSwipeActionPerform implements View.OnTouchListener {

    public interface OnswipePerformListner{
        //1 for rightSwipe 0 for left Swipe
        public void OnSwipePerformed(int leftoright);
    }

    private GestureDetector gestureDetector;
    public OnswipePerformListner onswipePerformListner;

    public OnSwipeActionPerform(Context context,OnswipePerformListner onswipePerformListner)
    {
        gestureDetector = new GestureDetector(context, new GestureListner());
        this.onswipePerformListner = onswipePerformListner;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    private final class GestureListner extends GestureDetector.SimpleOnGestureListener{

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            boolean result =false;
            try{
                float diffy = e2.getY() - e1.getY();
                float diffx = e2.getX() - e1.getX();
                if(Math.abs(diffx)>Math.abs(diffy))
                {
                    if(Math.abs(diffx)>SWIPE_THRESHOLD && Math.abs(velocityX)>SWIPE_VELOCITY_THRESHOLD)
                    {
                        if(diffx>0)
                        {
                            onswipePerformListner.OnSwipePerformed(1);
                        }
                        else
                        {
                            onswipePerformListner.OnSwipePerformed(0);
                        }
                    }
                }
            }catch (Exception exception)
            {
                exception.printStackTrace();
            }
            return result;
        }
    }
}
