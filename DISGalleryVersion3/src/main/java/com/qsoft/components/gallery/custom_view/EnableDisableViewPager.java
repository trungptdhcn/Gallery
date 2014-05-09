package com.qsoft.components.gallery.custom_view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * User: trungpt
 * Date: 4/7/14
 * Time: 10:44 AM
 */
public class EnableDisableViewPager extends ViewPager
{
    private boolean enabled = true;

    public EnableDisableViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0)
    {
        if (enabled)
        {
            return super.onInterceptTouchEvent(arg0);
        }

        return false;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

}
