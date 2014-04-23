package com.qsoft.android.components.gallery;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.qsoft.android.components.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 2/25/14
 * Time: 10:41 AM
 */
public class UploadImageAdapter extends BaseAdapter
{
    private Context context;
    private List<URLImageModel> urlImageModelsSDcard = new ArrayList<URLImageModel>();
    private List<URLImageModel> urlImageModelsChoseUpload;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private Cursor imageCursor;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
    private String mImageUri;

    public UploadImageAdapter(Context context)
    {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheOnDisc(false)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
        this.context = context;
    }

//    @Override
//    public int getItemViewType(int position)
//    {
//        if(position == 0)
//        {
//            return TYPE_ITEM;
//        }
//        return TYPE_SEPARATOR;
//    }

//    @Override
//    public int getViewTypeCount()
//    {
//        return TYPE_MAX_COUNT;
//    }

    public List<URLImageModel> getUrlImageModelsSDcard()
    {
        return urlImageModelsSDcard;
    }

    public void setUrlImageModelsSDcard()
    {
        this.urlImageModelsSDcard = getUrlImageModelListFromSDCard();
    }

    @Override
    public int getCount()
    {
        return urlImageModelsSDcard.size();
    }

    @Override
    public Object getItem(int position)
    {
        return urlImageModelsSDcard.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.dis_upload_image_item, null);
            ImageView ivImage = (ImageView) convertView.findViewById(R.id.dis_upload_image_item_ivImage);
            RelativeLayout rlImageContainer = (RelativeLayout) convertView.findViewById(R.id.dis_upload_image_item_rlContainer);
            CheckBox cbShow = (CheckBox) convertView.findViewById(R.id.dis_upload_image_item_cbShowImage);
            holder = new ViewHolder();

            holder.ivImage = ivImage;
            holder.cbShow = cbShow;
            holder.rlImageContainer = rlImageContainer;
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();

        }
        if (position == 0)
        {
            holder.ivImage.setImageResource(R.drawable.take_photo_frame);
            holder.cbShow.setVisibility(View.GONE);
            holder.ivImage.setTag(0);
            holder.ivImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    imageCursor.close();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try
                    {
                        intent.putExtra("return-data", true);
                        ((Activity) context).startActivityForResult(intent, DISGalleryComponent.RESULT_TAKE_IMAGE_CAMERA);
                    }
                    catch (ActivityNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        else
        {
            holder.cbShow.setVisibility(View.VISIBLE);
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage(urlImageModelsSDcard.get(position).getUrl(), holder.ivImage, options);
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            holder.ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.rlImageContainer.setLayoutParams(new LinearLayout.LayoutParams(width / 3, width / 3));
            if (urlImageModelsSDcard.get(position).getFlag() == 0)
            {
                holder.cbShow.setChecked(false);
                holder.cbShow.setButtonDrawable(R.drawable.icon_uncheck);
            }
            else
            {
                holder.cbShow.setChecked(true);
                holder.cbShow.setButtonDrawable(R.drawable.icon_check);
            }
            holder.cbShow.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (holder.cbShow.isChecked())
                    {
                        urlImageModelsSDcard.get(position).setFlag(1);
                        holder.cbShow.setButtonDrawable(R.drawable.icon_check);
                    }
                    else
                    {
                        urlImageModelsSDcard.get(position).setFlag(0);
                        holder.cbShow.setButtonDrawable(R.drawable.icon_uncheck);
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder
    {
        ImageView ivImage;
        RelativeLayout rlImageContainer;
        CheckBox cbShow;
    }

    public List<URLImageModel> getUrlImageModelListFromSDCard()
    {
        List<URLImageModel> urlImageModelList = new ArrayList<URLImageModel>();
        int columnIndex;
        String[] imgColumnID = {MediaStore.Images.Thumbnails._ID};
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        imageCursor = ((Activity) context).managedQuery(uri, imgColumnID, null, null,
                MediaStore.Images.Thumbnails.IMAGE_ID);
        columnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);

        File[] file = Environment.getExternalStorageDirectory().listFiles(new FilenameFilter()
        {

            @Override
            public boolean accept(File dir, String filename)
            {

                return filename.contains(".png");
            }
        });
        String[] dataLocation = {MediaStore.Images.Media.DATA};
        imageCursor = ((Activity) context).managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                dataLocation, null, null, null);
        columnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (imageCursor.moveToFirst())
        {
            while (imageCursor.moveToNext())
            {
                URLImageModel urlImageModel = new URLImageModel();
                String imgPath = imageCursor.getString(columnIndex);
                urlImageModel.setUrl("file://" + imgPath);
                urlImageModel.setFlag(0);
                urlImageModelList.add(urlImageModel);
            }
        }
        imageCursor.close();
        return urlImageModelList;

//        urlImageModelsSDcard.clear();
//        final String[] columns = {MediaStore.Images.Thumbnails._ID};
//        final String orderBy = MediaStore.Images.Media._ID;
//        Cursor imagecursor = ((Activity)context).managedQuery(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
//                null, null, orderBy);
//        int columnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
//        if (imagecursor != null)
//        {
//            int image_column_index = imagecursor
//                    .getColumnIndex(MediaStore.Images.Media._ID);
//            int count = imagecursor.getCount();
//            for (int i = 0; i < count; i++)
//            {
//                imagecursor.moveToPosition(i);
//                int id = imagecursor.getInt(image_column_index);
//                URLImageModel urlImageModel = new URLImageModel();
//                urlImageModel.setUrl(imageCursor.getString(columnIndex));
//                urlImageModel.setFlag(0);
//                urlImageModelList.add(urlImageModel);
//            }
//            imagecursor.close();
//        }
//        notifyDataSetChanged();
//        return urlImageModelList;

    }

    public void destroyCursor()
    {
        if (imageCursor != null)
        {
            imageCursor.close();
        }
    }

    class ImageItem
    {
        boolean selection;
        int id;
        Bitmap img;
    }

//    public void initialize()
//    {
//        urlImageModelsSDcard.clear();
//        final String[] columns = {MediaStore.Images.Thumbnails._ID};
//        final String orderBy = MediaStore.Images.Media._ID;
//        Cursor imagecursor = ((Activity)context).managedQuery(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
//                null, null, orderBy);
//        if (imagecursor != null)
//        {
//            int image_column_index = imagecursor
//                    .getColumnIndex(MediaStore.Images.Media._ID);
//            int count = imagecursor.getCount();
//            for (int i = 0; i < count; i++)
//            {
//                imagecursor.moveToPosition(i);
//                int id = imagecursor.getInt(image_column_index);
//                URLImageModel urlImageModel = new URLImageModel();
//                urlImageModel.id = id;
//                lastId = id;
//                imageItem.img = MediaStore.Images.Thumbnails.getContentUri(
//                        context.getContentResolver(), id,
//                        MediaStore.Images.Thumbnails.MICRO_KIND, null);
//                images.add(imageItem);
//            }
//            imagecursor.close();
//        }
//        notifyDataSetChanged();
//    }
}
