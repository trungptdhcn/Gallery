package com.qsoft.android.components.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qsoft.android.components.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PicAdapter extends BaseAdapter
{
    int defaultItemBackground;
    private Context context;
    Bitmap placeholder;
    List<URLImageModel> urlImageModels;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private List<URLImageModel> urlImageModelsShow;

    public PicAdapter(Context context)
    {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        urlImageModels = new ArrayList<URLImageModel>();
        urlImageModelsShow = new ArrayList<URLImageModel>();
        this.context = context;
        placeholder = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
    }

    public int getCount()
    {
        return urlImageModelsShow.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Map<String, Integer> imageSizeMap = ImageUtils.getSizeImage(context, R.drawable.bg_unit_photo);
        int height = imageSizeMap.get("HEIGHT");
        ImageView imageView = new ImageView(context);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        if (urlImageModelsShow.get(position).getFlag() == 1)
        {
            if (urlImageModelsShow.get(position).getUrl().contains("jpg"))
            {
                imageLoader.displayImage(urlImageModelsShow.get(position).getUrl(), imageView, options);
            }
            else
            {
                Bitmap bm = ThumbnailUtils.createVideoThumbnail(getUrlImageModelsShow().get(position).getUrl(), MediaStore.Images.Thumbnails.MINI_KIND);
                imageView.setImageBitmap(bm);
            }
        }
        else
        {
        }

        Gallery.LayoutParams layoutParams = new Gallery.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
    }

    public void addPic(URLImageModel urlImageModel)
    {
        urlImageModels.add(0, urlImageModel);
    }

    public void addPicShow(URLImageModel urlImageModel)
    {
        urlImageModelsShow.add(0, urlImageModel);
    }

    public void deletePic(URLImageModel urlImageModel)
    {
        urlImageModels.remove(urlImageModel);
    }

    public void deletePicShow(int position)
    {
        if (position <= urlImageModelsShow.size())
        {
            urlImageModelsShow.remove(position);

        }
    }

    public URLImageModel getPic(int position)
    {
        return urlImageModels.get(position);
    }

    public URLImageModel getPicShow(int position)
    {
        return urlImageModelsShow.get(position);
    }

    public boolean isEmptyList()
    {
        return urlImageModels.size() == 0;
    }

    public List<URLImageModel> getListPath()
    {
        return urlImageModels;
    }

    public void setListPath(List<URLImageModel> urlImageModels)
    {
        this.urlImageModels = urlImageModels;
    }

    public List<URLImageModel> getUrlImageModelsShow()
    {
        List<URLImageModel> urlImageModelShowTemp = new ArrayList<URLImageModel>();
        for (URLImageModel urlImageModel : urlImageModels)
        {
            if (urlImageModel.getFlag() == 1)
            {
                urlImageModelShowTemp.add(urlImageModel);
            }
        }
        return urlImageModelShowTemp;
    }

    public void setUrlImageModelsShow()
    {

        this.urlImageModelsShow = getUrlImageModelsShow();
    }



}
