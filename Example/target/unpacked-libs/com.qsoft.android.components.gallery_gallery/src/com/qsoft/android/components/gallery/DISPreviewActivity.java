package com.qsoft.android.components.gallery;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.DiscCacheUtil;
import com.qsoft.android.components.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 2/24/14
 * Time: 2:14 PM
 */
public class DISPreviewActivity extends Activity implements View.OnClickListener
{
    private EnableDisableViewPager vpImage;
    private ImagePagerAdapter imagePagerAdapter;
    private ImageView btOrderImage;
    private ImageView btTrash;
    private Button btClose;
    private ImageView btUpload;
    private ImageView btEdit;
    private List<URLImageModel> urlImageModelShows;
    private List<URLImageModel> urlImageModelUploads;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dis_preview_image);
        vpImage = (EnableDisableViewPager) findViewById(R.id.dis_preview_image_vpImage);
        btOrderImage = (ImageView) findViewById(R.id.dis_preview_image_btOrderPhoto);
        btTrash = (ImageView) findViewById(R.id.dis_preview_image_btTrash);
        btClose = (Button) findViewById(R.id.dis_preview_image_btClose);
        btUpload = (ImageView) findViewById(R.id.dis_preview_image_btUploadPhoto);
        btEdit = (ImageView) findViewById(R.id.dis_preview_image_btEditImage);
        urlImageModelShows = getIntent().getParcelableArrayListExtra(DISGalleryComponent.
                URL_IMAGES_SHOW_LIST_FLAG);
        int current_image = getIntent().getIntExtra(DISGalleryComponent.CURRENT_IMAGE_FLAG, 0);
        imagePagerAdapter = new ImagePagerAdapter(this);
        imagePagerAdapter.setUrlImageModelShows(urlImageModelShows);
        vpImage.setAdapter(imagePagerAdapter);
        vpImage.setCurrentItem(current_image);
        btOrderImage.setOnClickListener(this);
        btTrash.setOnClickListener(this);
        btClose.setOnClickListener(this);
        btUpload.setOnClickListener(this);
        btEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.dis_preview_image_btOrderPhoto)
        {
            eventOrderImage();

        }
        else if (i == R.id.dis_preview_image_btTrash)
        {
            eventTrash();

        }
        else if (i == R.id.dis_preview_image_btClose)
        {
            eventClose();

        }
        else if (i == R.id.dis_preview_image_btUploadPhoto)
        {
            eventUpload();

        }
        else if (i == R.id.dis_preview_image_btEditImage)
        {
            eventEdit();

        }
    }

    private void eventEdit()
    {
//        Intent editIntent = new Intent(Intent.ACTION_EDIT);
//        editIntent.setType("image/*");
//        imagePagerAdapter.notifyDataSetChanged();
        Uri uri = Uri.parse(imagePagerAdapter.getUrlImageModelShows().get(vpImage.getCurrentItem()).getUrl());
        File discCache = DiscCacheUtil.findInCache(uri.toString(), ImageLoader.getInstance().getDiscCache());
//        editIntent.setData(uri);
//        editIntent.putExtra("return-data", true);
//
//        Intent i = new Intent(editIntent);
////        editIntent.setAction(Intent.ACTION_EDIT);
//        setResult(RESULT_OK, i);
//        startActivityForResult(i,999);

        doCrop(Uri.fromFile(discCache));


    }

    public void eventOrderImage()
    {
        List<URLImageModel> urlImageModelAllList = getIntent().getParcelableArrayListExtra(DISGalleryComponent.URL_IMAGES_ALL_LIST_FLAG);
        Intent orderIntent = new Intent(this, DISOrderImageActivity.class);
        orderIntent.putParcelableArrayListExtra(DISGalleryComponent.URL_IMAGES_ALL_LIST_FLAG, (ArrayList) urlImageModelAllList);
        startActivityForResult(orderIntent, DISGalleryComponent.RESULT_ORDER_IMAGE_FLAG);
    }

    public void eventTrash()
    {
        Intent trashIntent = new Intent();
        trashIntent.putExtra("trash", "trash");
        trashIntent.putExtra("current_image", vpImage.getCurrentItem());
        setResult(RESULT_OK, trashIntent);
        finish();
    }

    public void eventClose()
    {
        finish();
    }

    public void eventUpload()
    {
        Intent uploadIntent = new Intent(this, DISUploadImageActivity.class);
        startActivityForResult(uploadIntent, DISGalleryComponent.RESULT_UPLOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == DISGalleryComponent.RESULT_ORDER_IMAGE_FLAG)
            {
                urlImageModelShows = data.getParcelableArrayListExtra(DISGalleryComponent.URL_IMAGES_ALL_LIST_FLAG);
                Intent orderIntent = new Intent();
                orderIntent.putParcelableArrayListExtra(DISGalleryComponent.URL_IMAGES_ALL_LIST_FLAG, (ArrayList) urlImageModelShows);
                orderIntent.putExtra("order_image", "order_image");
                setResult(RESULT_OK, orderIntent);
                finish();
            }
            if (requestCode == DISGalleryComponent.RESULT_UPLOAD_IMAGE)
            {
                urlImageModelUploads = data.getParcelableArrayListExtra("list_image_upload");
                Intent uploadIntent = new Intent();
                uploadIntent.putParcelableArrayListExtra("list_image_upload", (ArrayList) urlImageModelUploads);
                uploadIntent.putExtra("upload_image", "upload_image");
                setResult(RESULT_OK, uploadIntent);
                finish();
            }
            if (requestCode == 2)
            {
                ;
            }
            {
//                List<URLImageModel> urlImageModelList = new ArrayList<URLImageModel>();
//                urlImageModelList = imagePagerAdapter.getUrlImageModelShows();
//                urlImageModelList.remove()
                imagePagerAdapter.notifyDataSetChanged();
            }
        }
    }

    private void doCrop(final Uri uri)
    {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0)
        {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

            return;
        }
        else
        {
            intent.setData(uri);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra("output", uri);
            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            setResult(RESULT_OK, intent);
            startActivityForResult(i, 2);
        }
    }
}
