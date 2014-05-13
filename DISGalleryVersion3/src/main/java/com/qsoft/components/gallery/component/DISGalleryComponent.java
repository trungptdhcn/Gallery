package com.qsoft.components.gallery.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.adapter.DISSlideShowAdapter;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * User: trungpt
 * Date: 4/23/14
 * Time: 2:17 PM
 */
public class DISGalleryComponent extends LinearLayout implements View.OnClickListener
{
    private ViewPager vpImage;
    private CirclePageIndicator circleIndicator;

    public DISGalleryComponent(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.dis_slide_show, this);
        vpImage = (ViewPager) findViewById(R.id.dis_slide_show_view_pager);
        circleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        Drawable d = getResources().getDrawable(R.drawable.bg_unit_photo);
        ViewGroup.LayoutParams p = vpImage.getLayoutParams();
        p.width = d.getIntrinsicWidth();
        p.height = d.getIntrinsicHeight();

    }

    public void setSlideShow(DISSlideShowAdapter adapter)
    {
        vpImage.setAdapter(adapter);
        circleIndicator.setViewPager(vpImage);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

        }
    }

    public ViewPager getVpImage()
    {
        return vpImage;
    }

    public void setVpImage(ViewPager vpImage)
    {
        this.vpImage = vpImage;
    }

    public CirclePageIndicator getCircleIndicator()
    {
        return circleIndicator;
    }

    public void setCircleIndicator(CirclePageIndicator circleIndicator)
    {
        this.circleIndicator = circleIndicator;
    }

    public void removeView (View defunctPage)
    {
        int pageIndex = ((DISSlideShowAdapter)vpImage.getAdapter()).removeView (vpImage, defunctPage);
        if (pageIndex == ((DISSlideShowAdapter)vpImage.getAdapter()).getCount())
            pageIndex--;
        vpImage.setCurrentItem(pageIndex);
    }

    public View getCurrentPage ()
    {
        return  ((DISSlideShowAdapter)vpImage.getAdapter()).getView (vpImage.getCurrentItem());
    }

    public void setCurrentPage (View pageToShow)
    {
        vpImage.setCurrentItem (((DISSlideShowAdapter)vpImage.getAdapter()).getItemPosition (pageToShow), true);
    }

    public void addView (View newPage)
    {
        int pageIndex = ((DISSlideShowAdapter)vpImage.getAdapter()).addView (newPage);
        // You might want to make "newPage" the currently displayed page:
        vpImage.setCurrentItem (pageIndex, true);
    }

}
