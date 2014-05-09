package com.qsoft.components.gallery.adapter;

/**
 * Author: alex askerov
 * Date: 9/9/13
 * Time: 10:52 PM
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.dynamicgrid.BaseDynamicGridAdapter;
import com.qsoft.components.gallery.model.*;
import com.qsoft.components.gallery.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: alex askerov
 * Date: 9/7/13
 * Time: 10:56 PM
 */
public class DISOrderImageAdapter extends BaseDynamicGridAdapter
{
    public ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    Context context;
    private LayoutInflater mInflater;
    private LinearLayout.LayoutParams mImageViewLayoutParams;
    private int mItemHeight = 0;
    private int mNumColumns = 0;

    public DISOrderImageAdapter(Context context, List<?> items, int columnCount)
    {
        super(context, items, columnCount);
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageViewLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.take_photo_frame)
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(1000))
                .build();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (position == 0)
        {
            ((ImageBaseModel) getItem(0)).setShown(true);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dis_order_image_item, null);
            final ImageView imageView = (ImageView)view.findViewById(R.id.dis_order_image_item_ivImage);
            ImageView cbImage = (ImageView)view.findViewById(R.id.dis_order_image_item_cbShowImage);
            LinearLayout llUpload = (LinearLayout)view.findViewById(R.id.dis_order_image_item_llUploading);
            RelativeLayout  rlImageContainer = (RelativeLayout)view.findViewById(R.id.dis_order_image_item_rlContainer);
            cbImage.setImageResource(R.drawable.icon_check);
            cbImage.setEnabled(false);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage(((ImageBaseModel) getItem(position)).getUrl(), imageView, options);
            imageLoader.displayImage(((ImageBaseModel) getItem(position)).getUrl(), imageView, options, new ImageLoadingListener()
                    {
                        @Override
                        public void onLoadingStarted(String imageUri, View view)
                        {
                            imageView.setImageResource(R.drawable.take_photo_frame);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason)
                        {
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                        {
                            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
                            int width = display.getWidth();
                            int height = display.getHeight();
                            imageView.setImageBitmap(Utils.scaleCenterCrop(loadedImage, width / 3, width / 3));
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
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            rlImageContainer.setLayoutParams(mImageViewLayoutParams);
            if (rlImageContainer.getLayoutParams().height != mItemHeight)
            {
                rlImageContainer.setLayoutParams(mImageViewLayoutParams);
            }
            if(((ImageBaseModel) getItem(position)).getUrl().contains("file://"))
            {
                llUpload.setVisibility(View.VISIBLE);
            }
            return view;
        }
        else
        {


            final ViewHolder holder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.dis_order_image_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//            imageLoader.displayImage(((ImageBaseModel) getItem(position)).getUrl(), holder.ivImage, options);
            imageLoader.displayImage(((ImageBaseModel) getItem(position)).getUrl(), holder.ivImage, options, new ImageLoadingListener()
                    {
                        @Override
                        public void onLoadingStarted(String imageUri, View view)
                        {
                            holder.ivImage.setImageResource(R.drawable.take_photo_frame);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason)
                        {
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                        {
                            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
                            int width = display.getWidth();
                            int height = display.getHeight();
                            holder.ivImage.setImageBitmap(Utils.scaleCenterCrop(loadedImage, width / 3, width / 3));
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
            holder.ivImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            if (((ImageBaseModel) getItem(position)).getUrl().contains("file://"))
            {
                holder.llUpload.setVisibility(View.VISIBLE);
            }
            holder.rlImageContainer.setLayoutParams(mImageViewLayoutParams);
            if (holder.rlImageContainer.getLayoutParams().height != mItemHeight)
            {
                holder.rlImageContainer.setLayoutParams(mImageViewLayoutParams);
            }
            if (((ImageBaseModel) getItem(position)).isShown())
            {
                holder.cbShow.setImageResource(R.drawable.icon_check);
            }
            else if (!((ImageBaseModel) getItem(position)).isShown())
            {
                holder.cbShow.setImageResource(R.drawable.icon_uncheck);
            }
            holder.cbShow.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (((ImageBaseModel) getItem(position)).isShown())
                    {
                        ((ImageBaseModel) getItem(position)).setShown(false);
                        holder.cbShow.setImageResource(R.drawable.icon_uncheck);
                    }
                    else
                    {
                        ((ImageBaseModel) getItem(position)).setShown(true);
                        holder.cbShow.setImageResource(R.drawable.icon_check);
                    }
                }
            });
        }
        return convertView;
    }


    @Override
    public int getItemViewType(int position)
    {
        return (position == 0) ? 1 : 0;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    private class ViewHolder
    {
        ImageView ivImage;
        RelativeLayout rlImageContainer;
        ImageView cbShow;
        LinearLayout llUpload;

        private ViewHolder(View view)
        {
            ivImage = (ImageView) view.findViewById(R.id.dis_order_image_item_ivImage);
            cbShow = (ImageView) view.findViewById(R.id.dis_order_image_item_cbShowImage);
            rlImageContainer = (RelativeLayout) view.findViewById(R.id.dis_order_image_item_rlContainer);
            llUpload = (LinearLayout)view.findViewById(R.id.dis_order_image_item_llUploading);
        }
    }

    public void setItemHeight(int height)
    {
        if (height == mItemHeight)
        {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        notifyDataSetChanged();
    }

    public void setNumColumns(int numColumns)
    {
        mNumColumns = numColumns;
    }

    public int getNumColumns()
    {
        return mNumColumns;
    }

}