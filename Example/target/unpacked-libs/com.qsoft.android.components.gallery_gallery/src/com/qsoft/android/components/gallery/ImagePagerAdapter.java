package com.qsoft.android.components.gallery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.polites.android.GestureImageView;
import com.qsoft.android.components.R;

import java.util.List;

/**
 * User: trungpt
 * Date: 2/24/14
 * Time: 3:23 PM
 */
public class ImagePagerAdapter extends PagerAdapter
{
    private Context context;
    private List<URLImageModel> urlImageModelShows;
    private LayoutInflater inflater;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;

    public ImagePagerAdapter(Context context)
    {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return urlImageModelShows.size();  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position)
    {
        GestureImageView imgDisplay;
        Button btnClose;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.dis_preview_image_pager_item, container, false);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)

                .build();
        imgDisplay = (GestureImageView) viewLayout.findViewById(R.id.dis_preview_image_pager_item_ivDisplayImage);
        imgDisplay.getGestureImageViewListener();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(urlImageModelShows.get(position).getUrl(), imgDisplay, options);
        ((EnableDisableViewPager) container).addView(viewLayout);
        imgDisplay.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                Drawable drawable = ((GestureImageView) view).getDrawable();
                int height = drawable.getMinimumHeight();
                if (((GestureImageView) view).getScaledHeight() > height)
                {
                    container.setEnabled(false);
                }
                else
                {
                    container.setEnabled(true || ((GestureImageView) view).isLandscape());
                }
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    public List<URLImageModel> getUrlImageModelShows()
    {
        return urlImageModelShows;
    }

    public void setUrlImageModelShows(List<URLImageModel> urlImageModelShows)
    {
        this.urlImageModelShows = urlImageModelShows;
    }
}