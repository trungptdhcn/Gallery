package com.qsoft.components.gallery.dynamicgrid;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * User: trungpt
 * Date: 5/6/14
 * Time: 10:47 AM
 */
public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
{
    public Context context;

    public MyGestureDetector(Context con)
    {
        this.context = con;
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        return super.onDown(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        return super.onSingleTapUp(e);
    }
}