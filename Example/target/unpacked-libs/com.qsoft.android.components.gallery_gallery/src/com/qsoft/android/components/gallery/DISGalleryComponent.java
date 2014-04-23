package com.qsoft.android.components.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.qsoft.android.components.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 2/24/14
 * Time: 11:31 AM
 */
public class DISGalleryComponent extends LinearLayout implements AdapterView.OnItemClickListener, View.OnClickListener
{
    private Activity activity;
    private Gallery gallery;
    private int currentImage = -1;
    private ImageView[] imageView;
    private LinearLayout rlContainerGallery;
    private LinearLayout llIndicator;
    private PicAdapter picAdapter;
    public static final String URL_IMAGE_FLAG = "url_image";
    public static final String URL_IMAGES_ALL_LIST_FLAG = "list_all_url";
    public static final String URL_IMAGES_SHOW_LIST_FLAG = "list_show";
    public static final String CURRENT_IMAGE_FLAG = "current_image";
    public static final String SHOW_IMAGE_FLAG = "show_image";

    public static final int RESULT_SHOW_IMAGE = 222;
    public static final int RESULT_ORDER_IMAGE_FLAG = 111;
    public static final int RESULT_PREVIEW_IMAGE = 333;
    public static final int RESULT_TAKE_IMAGE_CAMERA = 444;
    public static final int RESULT_UPLOAD_IMAGE = 555;

    public DISGalleryComponent(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.dis_gallery, this);
        rlContainerGallery = (LinearLayout) findViewById(R.id.dis_gallery_llContainerIndicator);
        gallery = (Gallery) findViewById(R.id.dis_gallery);
        llIndicator = (LinearLayout) findViewById(R.id.dis_gallery_llIndicator);
        gallery.setOnItemClickListener(this);
    }

    //Setter and getter

    public void setPicAdapter(PicAdapter picAdapter,Activity activity)
    {
        this.picAdapter = picAdapter;
        this.activity = activity;
        setImageToGallery();
    }

    private void createDotPanel(Context context, LinearLayout layout, int count)
    {
        imageView = new ImageView[count];
        for (int i = 0; i < count; i++)
        {
            imageView[i] = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.leftMargin = 5;
            params.rightMargin = 5;
            params.gravity = Gravity.CENTER;
            imageView[i].setLayoutParams(params);
            imageView[i].setImageResource(R.drawable.icon_circle_grey);
            layout.addView(imageView[i]);
        }
    }

    private void selectDot(int position)
    {
        for (int i = 0; i < imageView.length; i++)
        {
            if (i == position)
            {
                imageView[i].setImageResource(R.drawable.icon_circle_blue);
            }
            else
            {
                imageView[i].setImageResource(R.drawable.icon_circle_grey);
            }
        }
    }

    public void gallerySelectItem()
    {
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                currentImage = arg2;
                selectDot(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
            }
        });
    }

    public void setImageToGallery()
    {
        createDotPanel(activity, llIndicator, picAdapter.getCount());
        gallerySelectItem();
        gallery.setAdapter(picAdapter);
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(activity, DISPreviewActivity.class);
        intent.putExtra(URL_IMAGE_FLAG, picAdapter.getPicShow(currentImage));
        intent.putExtra(URL_IMAGES_ALL_LIST_FLAG,(ArrayList) picAdapter.getListPath());
        intent.putExtra(URL_IMAGES_SHOW_LIST_FLAG,(ArrayList)picAdapter.getUrlImageModelsShow());
        intent.putExtra(CURRENT_IMAGE_FLAG,currentImage);
        activity.startActivityForResult(intent, RESULT_PREVIEW_IMAGE);
    }

    public void handle(int requestCode, int resultCode, Intent data)
    {
        List<URLImageModel> urlImageModelAlls = new ArrayList<URLImageModel>();
        List<URLImageModel> urlImageModelUploads = new ArrayList<URLImageModel>();
        if (resultCode == Activity.RESULT_OK)
        {
            if(requestCode == RESULT_PREVIEW_IMAGE)
            {
                String order_flag_result = data.getStringExtra("order_image");
                String trash_flag_result = data.getStringExtra("trash");
                String upload_image = data.getStringExtra("upload_image");
                if(order_flag_result != null && order_flag_result.equals("order_image"))
                {
                    urlImageModelAlls = data.getParcelableArrayListExtra(DISGalleryComponent.URL_IMAGES_ALL_LIST_FLAG);
                    picAdapter.getListPath().clear();
                    picAdapter.setListPath(urlImageModelAlls);
                    picAdapter.setUrlImageModelsShow();
                    picAdapter.notifyDataSetChanged();
                    llIndicator.removeAllViews();
                    createDotPanel(activity, llIndicator, picAdapter.getUrlImageModelsShow().size());
                    selectDot(currentImage);
                }
                else if(trash_flag_result != null && trash_flag_result.equals("trash"))
                {
                    currentImage = data.getIntExtra("current_image",-1);
                    picAdapter.deletePic(picAdapter.getPicShow(currentImage));
                    picAdapter.deletePicShow(currentImage);
                    llIndicator.removeAllViews();
                    createDotPanel(activity, llIndicator, picAdapter.getUrlImageModelsShow().size());
                    selectDot(currentImage);
                    picAdapter.notifyDataSetChanged();
                }
                else if(upload_image != null && upload_image.equals("upload_image"))
                {
                    urlImageModelUploads = data.getParcelableArrayListExtra("list_image_upload");
                    picAdapter.getListPath().addAll(urlImageModelUploads);
                    picAdapter.notifyDataSetChanged();
                    picAdapter.setUrlImageModelsShow();
                    picAdapter.notifyDataSetChanged();
                    llIndicator.removeAllViews();
                    createDotPanel(activity, llIndicator, picAdapter.getUrlImageModelsShow().size());
                    selectDot(currentImage);
                }
            }
        }
    }
}
