package com.qsoft.components.gallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.activity.DISPreviewImageActivity;
import com.qsoft.components.gallery.model.Image;
import com.qsoft.components.gallery.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * User: trungpt
 * Date: 4/23/14
 * Time: 2:39 PM
 */
public class DISSlideShowAdapter extends PagerAdapter
{
    private List<Image> imageList = new ArrayList<Image>();
    private Context context;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;

    public DISSlideShowAdapter(Context context, List<Image> imageList)
    {
        this.context = context;
        this.imageList = imageList;
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount()
    {
        int count = 0;
        for (Image image : imageList)
        {
            if (image.isShow())
            {
                count++;
            }
        }
        return count;
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position)
    {
        ImageView imageView = new ImageView(context);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(imageList.get(position).getUrl(), imageView, options);
        ((ViewPager) container).addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((Activity) context).startActivity(new Intent(context, DISPreviewImageActivity.class));
            }
        });
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object)
    {
        return view == ((ImageView) object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object)
    {
        ((ViewPager) container).removeView((ImageView) object);
    }


}
