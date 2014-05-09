package com.qsoft.components.gallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.polites.android.GestureImageView;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.activity.DISPreviewImageActivity;
import com.qsoft.components.gallery.activity.DISPreviewImageActivity_;
import com.qsoft.components.gallery.common.ConstantImage;
import com.qsoft.components.gallery.custom_view.EnableDisableViewPager;
import com.qsoft.components.gallery.model.*;
import com.qsoft.components.gallery.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 4/23/14
 * Time: 2:39 PM
 */
public class DISSlideShowAdapter<C extends ImageContainer, B extends ImageBaseModel> extends PagerAdapter
{
    private C modelImage;
    private Context context;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected List<B> listShowInSlide = new ArrayList<B>();
    DisplayImageOptions options;
    private ArrayList<View> views = new ArrayList<View>();

    public DISSlideShowAdapter(Context context, C modelImage)
    {
        this.context = context;
        this.modelImage = modelImage;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.take_photo_frame)
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        List<B> imageList = modelImage.getImageList();
        for(B image : imageList)
        {
            if (image.isShown())
            {
                listShowInSlide.add(image);
            }
        }
    }

    @Override
    public int getCount()
    {
//        int count = 0;
//        List<B> imageList = modelImage.getImageList();
//        for (B image : imageList)
//        {
//            if (image.isShown())
//            {
//                count++;
//            }
//        }
        return listShowInSlide.size();
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
    public int getItemPosition(Object object)
    {
        int index = views.indexOf(object);
        if (index == -1)
        {
            return POSITION_NONE;
        }
        else
        {
            return index;
        }
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position)
    {
        ImageView imageView = null;
        if (modelImage instanceof ImageSlide)
        {
//            imageView = new ImageView(context);
//            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//            if (modelImage.getImageList().get(position) instanceof ImageViewModel)
//            {
//                imageLoader.displayImage(((ImageViewModel) modelImage.getImageList().get(position)).getUrl(), imageView, options);
//            }
//
//            ((ViewPager) container).addView(imageView, 0);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            modelImage.setPosition(position);
//            imageView.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View view)
//                {
//                    modelImage.setPosition(position);
//                    Intent i = new Intent(context, DISPreviewImageActivity_.class);
//                    i.putExtra(ConstantImage.SLIDE_MODEL_IMAGE, (Parcelable) modelImage);
//                    ((Activity) context).startActivityForResult(i, ConstantImage.REQUEST_CODE_PREVIEW_IMAGE);
//
//                }
//            });
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.dis_slide_show_item, container, false);
            ImageView imageView1 = (ImageView) viewLayout.findViewById(R.id.dis_slide_show_item_imageView);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            if (listShowInSlide.get(position) instanceof ImageViewModel)
            {
                imageLoader.displayImage(((B) listShowInSlide.get(position)).getUrl(), imageView1, options);
            }
            imageView1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            modelImage.setPosition(position);
            ((ViewPager) container).addView(viewLayout, 0);
            imageView1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    modelImage.setPosition(position);
                    Intent i = new Intent(context, DISPreviewImageActivity_.class);
                    i.putExtra(ConstantImage.SLIDE_MODEL_IMAGE, (Parcelable) modelImage);
                    ((Activity) context).startActivityForResult(i, ConstantImage.REQUEST_CODE_PREVIEW_IMAGE);

                }
            });
            return viewLayout;
        }
        else if (modelImage instanceof ImagePreview)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.dis_preview_image_pager_item, container, false);
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .build();
            imageView = (GestureImageView) viewLayout.findViewById(R.id.dis_preview_image_pager_item_ivDisplayImage);
            ((GestureImageView) imageView).getGestureImageViewListener();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            final ImageView finalImageView = imageView;
            imageLoader.displayImage(((B) listShowInSlide.get(position)).getUrl(), imageView, options, new ImageLoadingListener()
                    {
                        @Override
                        public void onLoadingStarted(String imageUri, View view)
                        {
                            ((GestureImageView) finalImageView).setImageResource(R.drawable.take_photo_frame);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason)
                        {
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                        {
                            finalImageView.setImageBitmap(loadedImage);
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view)
                        {
                        }
                    }, new ImageLoadingProgressListener()
                    {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total)
                        {
                        }
                    }
            );
//            imageLoader.displayImage(((B) modelImage.getImageList().get(position)).getUrl(), imageView, options);
            ((EnableDisableViewPager) container).addView(viewLayout);
            imageView.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    float startScale = ((GestureImageView) view).getStartingScale();
                    if (((GestureImageView) view).getGestureImageViewTouchListener().getLastScale() > startScale)
                    {
                        container.setEnabled(false);
                    }
                    else
                    {
                        container.setEnabled(true);

                    }
                    return false;
                }
            });
            return viewLayout;
        }
        return imageView;

    }

    @Override
    public boolean isViewFromObject(final View view, final Object object)
    {
        if (modelImage instanceof ImagePreview)
        {
            return view == ((LinearLayout) object);
        }
        else
        {
            return view == ((RelativeLayout) object);
        }
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object)
    {
        if (modelImage instanceof ImageSlide)
        {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }
        else if (modelImage instanceof ImagePreview)
        {
            ((ViewPager) container).removeView((LinearLayout) object);
        }
    }

    public int removeView(ViewPager pager, View v)
    {
        return removeView(pager, views.indexOf(v));
    }

    public int removeView(ViewPager pager, int position)
    {
        pager.setAdapter(null);
        views.remove(position);
        pager.setAdapter(this);
        return position;
    }

    public View getView(int position)
    {
        return views.get(position);
    }


    public C getModelImage()
    {
        return modelImage;
    }

    public void setModelImage(C modelImage)
    {
        this.modelImage = modelImage;
    }

    public int addView(View v)
    {
        return addView(v, views.size());
    }

    public int addView(View v, int position)
    {
        views.add(position, v);
        return position;
    }

    @Override
    public void notifyDataSetChanged()
    {
        List<B> imageList = modelImage.getImageList();
        listShowInSlide.clear();
        for(B image : imageList)
        {
            if (image.isShown())
            {
                listShowInSlide.add(image);
            }
        }
        super.notifyDataSetChanged();
    }

}
