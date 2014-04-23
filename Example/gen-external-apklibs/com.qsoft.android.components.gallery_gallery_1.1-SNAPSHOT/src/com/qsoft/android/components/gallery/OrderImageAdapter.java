package com.qsoft.android.components.gallery;

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
import com.qsoft.android.components.R;

import java.util.List;

/**
 * User: trungpt
 * Date: 2/24/14
 * Time: 5:32 PM
 */
public class OrderImageAdapter extends BaseAdapter
{

    private Context context;
    private List<URLImageModel> urlImageModelAllList;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;

    public OrderImageAdapter(Context context, List<URLImageModel> urlImageModelAllList)
    {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        this.context = context;
        this.urlImageModelAllList = urlImageModelAllList;
    }

    @Override
    public int getCount()
    {
        return urlImageModelAllList.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int position)
    {
        return urlImageModelAllList.get(position);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int position)
    {
        return position;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.dis_order_image_item, null);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.dis_order_image_item_ivImage);
            viewHolder.rlImageContainer = (RelativeLayout) convertView.findViewById(R.id.dis_grid_image_item_rlContainer);
            viewHolder.cbShow = (CheckBox) convertView.findViewById(R.id.dis_order_image_item_cbShowImage);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null)
                    .build();
            imageLoader.init(config);
            imageLoader.displayImage(urlImageModelAllList.get(position).getUrl(), viewHolder.ivImage, options);
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            int width = display.getWidth();  // deprecated
            int height = display.getHeight();  // deprecated
            viewHolder.ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewHolder.rlImageContainer.setLayoutParams(new LinearLayout.LayoutParams(width / 3, width / 3));
            if (urlImageModelAllList.get(position).getFlag() == 0)
            {
                viewHolder.cbShow.setChecked(false);
                viewHolder.cbShow.setButtonDrawable(R.drawable.icon_uncheck);
            }
            else
            {
                viewHolder.cbShow.setChecked(true);
                viewHolder.cbShow.setButtonDrawable(R.drawable.icon_check);
            }
            viewHolder.cbShow.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (viewHolder.cbShow.isChecked())
                    {
                        urlImageModelAllList.get(position).setFlag(1);
                        viewHolder.cbShow.setButtonDrawable(R.drawable.icon_check);
                    }
                    else
                    {
                        urlImageModelAllList.get(position).setFlag(0);
                        viewHolder.cbShow.setButtonDrawable(R.drawable.icon_uncheck);
                    }
                    notifyDataSetChanged();
                }
            });
        return convertView;
    }

    class ViewHolder
    {
        ImageView ivImage;
        RelativeLayout rlImageContainer;
        CheckBox cbShow;
    }

    public List<URLImageModel> getUrlImageModelAllList()
    {
        return urlImageModelAllList;
    }

    public void setUrlImageModelAllList(List<URLImageModel> urlImageModelAllList)
    {
        this.urlImageModelAllList = urlImageModelAllList;
    }
}
