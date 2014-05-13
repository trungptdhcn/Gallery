package com.qsoft.components.gallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.*;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.common.ConstantImage;
import com.qsoft.components.gallery.model.ImageBaseModel;
import com.qsoft.components.gallery.model.ImageContainer;
import com.qsoft.components.gallery.model.ImageUploadModel;
import com.qsoft.components.gallery.utils.GalleryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 4/26/14
 * Time: 10:57 AM
 */
public class DISUploadImageAdapter<C extends ImageContainer, B extends ImageBaseModel> extends BaseAdapter
{
    private Context context;
    private C imageModel;
    private LayoutInflater mInflater;
    private LinearLayout.LayoutParams mImageViewLayoutParams;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    List<String> strUrl = new ArrayList<String>();
    public ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private Uri fileUri;

    public DISUploadImageAdapter(Context context, C imageModel)
    {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageViewLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.take_photo_frame)
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheOnDisc(false)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(1000))
                .build();
        this.imageModel = imageModel;
    }

    @Override
    public int getCount()
    {
        return imageModel.getImageList().size();
    }

    @Override
    public Object getItem(int position)
    {
        return imageModel.getImageList().get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return ((ImageBaseModel) imageModel.getImageList().get(position)).getIndex();
    }

    public void initializeThumbnail(List<Long> imageId)
    {
        imageModel.getImageList().clear();
        for (Long id : imageId)
        {
            Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(context.getContentResolver(), id
                    , MediaStore.Images.Thumbnails.MINI_KIND, null);
            String uri = "";
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                uri = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            }
            else
            {
                uri = strUrl.get(imageId.indexOf(id));
            }
            cursor.close();
            ImageUploadModel imageUpload = new ImageUploadModel();
            imageUpload.setThumbnailUri("file://" + uri);
            imageUpload.setSelection(false);
            imageUpload.setId(id.toString());
            imageUpload.setRealUri(strUrl.get(imageId.indexOf(id)));
            imageUpload.setShown(true);
            imageUpload.setEquipmentID(imageModel.getEquipmentId());
            imageUpload.setIndex(1l);
            imageModel.getImageList().add(imageUpload);
        }
    }

    public List<Long> getListIdRealImage()
    {
        Cursor imageCursor = MediaStore.Images.Media.query(context.getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null);
        List<Long> listImageId = new ArrayList<Long>();
        if (imageCursor.moveToFirst())
        {
            do
            {
                Long image_id = imageCursor.getLong(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
                String filePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                listImageId.add(image_id);
                strUrl.add(filePath);
            } while (imageCursor.moveToNext());
            imageCursor.close();
        }
        return listImageId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup)
    {
        if (position == 0)
        {
            ImageView imageView;
            if (convertView == null)
            {
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            else
            {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(R.drawable.take_photo_frame);
            imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    fileUri = GalleryUtils.getOutputMediaFileUri(GalleryUtils.MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    ((Activity) context).startActivityForResult(intent, ConstantImage.REQUEST_CODE_CAMERA);
                }
            });
            return imageView;
        }
        else
        {
            final ViewHolder holder;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.dis_upload_image_item, viewGroup, false);
                holder.imageview = (ImageView) convertView
                        .findViewById(R.id.dis_upload_image_item_ivImage);
                holder.checkbox = (CheckBox) convertView
                        .findViewById(R.id.dis_upload_image_item_cbShowImage);
                holder.relativeLayout = (RelativeLayout) convertView
                        .findViewById(R.id.dis_upload_image_item_rlContainer);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//            imageLoader.displayImage(((ImageUploadModel) imageModel.getImageList().get(position)).getThumbnailUri(), holder.imageview, options);
//            imageLoader.loadImage(((ImageUploadModel) imageModel.getImageList().get(position)).getThumbnailUri()
//                    , new SimpleImageLoadingListener()
//            {
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
//                {
//                    Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
//                    int width = display.getWidth();
//                    int height = display.getHeight();
//                    holder.imageview.setImageBitmap(scaleCenterCrop(loadedImage,width/3,width/3));
//
//                }
//            });
            imageLoader.displayImage(((ImageUploadModel) imageModel.getImageList().get(position)).getThumbnailUri(), holder.imageview, options, new ImageLoadingListener()
                    {
                        @Override
                        public void onLoadingStarted(String imageUri, View view)
                        {
                            holder.imageview.setImageResource(R.drawable.take_photo_frame);
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
                            holder.imageview.setImageBitmap(GalleryUtils.scaleCenterCrop(loadedImage, width / 3, width / 3));
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
            holder.imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.relativeLayout.setLayoutParams(mImageViewLayoutParams);
            if (holder.relativeLayout.getLayoutParams().height != mItemHeight)
            {
                holder.relativeLayout.setLayoutParams(mImageViewLayoutParams);
            }

            if (!((ImageUploadModel) imageModel.getImageList().get(position)).isSelection())
            {
                holder.checkbox.setChecked(false);
                holder.checkbox.setButtonDrawable(R.drawable.icon_uncheck);
            }
            else
            {
                holder.checkbox.setChecked(true);
                holder.checkbox.setButtonDrawable(R.drawable.icon_check);
            }

            holder.checkbox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (holder.checkbox.isChecked())
                    {
                        ((ImageUploadModel) imageModel.getImageList().get(position)).setSelection(true);
                        holder.checkbox.setButtonDrawable(R.drawable.icon_check);
                    }
                    else
                    {
                        ((ImageUploadModel) imageModel.getImageList().get(position)).setSelection(false);
                        holder.checkbox.setButtonDrawable(R.drawable.icon_uncheck);
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

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    class ViewHolder
    {
        ImageView imageview;
        CheckBox checkbox;
        RelativeLayout relativeLayout;
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

    public C getImageModels()
    {
        return imageModel;
    }

    public void setImageModels(C imageModels)
    {
        this.imageModel = imageModels;
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader)
    {
        this.imageLoader = imageLoader;
    }

    public Uri getFileUri()
    {
        return fileUri;
    }

    public void setFileUri(Uri fileUri)
    {
        this.fileUri = fileUri;
    }
}
