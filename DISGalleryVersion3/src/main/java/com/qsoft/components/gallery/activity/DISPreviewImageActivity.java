package com.qsoft.components.gallery.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.*;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.adapter.DISSlideShowAdapter;
import com.qsoft.components.gallery.common.ConstantImage;
import com.qsoft.components.gallery.custom_view.EnableDisableViewPager;
import com.qsoft.components.gallery.model.ImageBaseModel;
import com.qsoft.components.gallery.model.ImagePreview;
import com.qsoft.components.gallery.model.ImageSlide;
import com.qsoft.components.gallery.model.ImageViewModel;
import com.qsoft.components.gallery.utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 4/23/14
 * Time: 3:03 PM
 */
@EActivity(R.layout.dis_preview_image)
public class DISPreviewImageActivity extends Activity implements View.OnClickListener
{
    @ViewById(R.id.dis_preview_image_vpImage)
    public EnableDisableViewPager vpImage;
    @ViewById(R.id.dis_preview_image_btUploadPhoto)
    public ImageView btUpload;
    @ViewById(R.id.dis_preview_image_btClose)
    public Button btClose;
    @ViewById(R.id.dis_preview_image_btTrash)
    public ImageView btTrash;
    @ViewById(R.id.dis_preview_image_btOrderPhoto)
    public ImageView btOrder;
    @ViewById(R.id.dis_preview_image_btDowloadImage)
    public ImageView btDownload;
    @ViewById(R.id.dis_preview_image_rlContainerButtonEditImage)
    public RelativeLayout rlContainerButton;

    private Long equipmentId;
    private int position;
    private Long userId;
    private ImageSlide imageSlide;
    private ImagePreview imagePreviewModel;
    private DISSlideShowAdapter adapter;
    private ProgressDialog dialog;

    @AfterViews
    public void afterView()
    {
        imageSlide = getIntent().getParcelableExtra(ConstantImage.SLIDE_MODEL_IMAGE);
        List<ImageBaseModel> imageBaseModels = imageSlide.getImageList();
        equipmentId = imageSlide.getEquipmentId();
        position = imageSlide.getPosition();
        userId = imageSlide.getUserId();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Downloading...");
        dialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);

        imagePreviewModel = new ImagePreview();
        imagePreviewModel.setImageList(imageBaseModels);
        imagePreviewModel.setPosition(position);
        imagePreviewModel.setEquipmentId(equipmentId);
        imagePreviewModel.setUserId(userId);
        imagePreviewModel.setUrlDeleteImage(imageSlide.getUrlDeleteImage());
        imagePreviewModel.setUrlUploadImage(imageSlide.getUrlUploadImage());
        imagePreviewModel.setUrlDownloadImage(imageSlide.getUrlDownloadImage());
        imagePreviewModel.setUrlOrderImage(imageSlide.getUrlOrderImage());

        adapter = new DISSlideShowAdapter(this, imagePreviewModel);
        vpImage.setAdapter(adapter);
        if (imagePreviewModel.getPosition() >= 0)
        {
            vpImage.setCurrentItem(imagePreviewModel.getPosition());
        }

        btClose.setOnClickListener(this);
        btTrash.setOnClickListener(this);
        btUpload.setOnClickListener(this);
        btOrder.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.dis_preview_image_btUploadPhoto:
                uploadImage();
                break;
            case R.id.dis_preview_image_btClose:
                finish();
                break;
            case R.id.dis_preview_image_btTrash:
                try
                {
                    deleteImage();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.dis_preview_image_btOrderPhoto:
                orderImage();
                break;
        }
    }

    public void deleteImage() throws IOException
    {
        Intent trashIntent = new Intent();
        trashIntent.putExtra(ConstantImage.EVENT_FLAG, ConstantImage.TRASH_IMAGE_FLAG);
        trashIntent.putExtra(ConstantImage.POSITION, vpImage.getCurrentItem());
        DISSlideShowAdapter adapter = (DISSlideShowAdapter) vpImage.getAdapter();
        List<ImageBaseModel> imageModel = adapter.getModelImage().getImageList();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpclient = new DefaultHttpClient();
        String url = imagePreviewModel.getUrlDeleteImage() + (imageModel.get(vpImage.getCurrentItem())).getId();
        HttpGet httpget = new HttpGet(url);
        String result = "";
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            InputStream instream = entity.getContent();
            result = Utils.convertStreamToString(instream);
            instream.close();
        }
        if (result.contains("SUCCESS"))
        {
            trashIntent.putExtra(ConstantImage.EVENT_FLAG, ConstantImage.TRASH_IMAGE_FLAG);
            trashIntent.putExtra(ConstantImage.EQUIPMENT_ID,equipmentId);
            setResult(RESULT_OK, trashIntent);
            finish();
        }

    }

    public void orderImage()
    {
        Intent orderIntent = new Intent(this, DISOrderImageActivity_.class);
        orderIntent.putExtra(ConstantImage.PREVIEW_MODEL_IMAGE, imagePreviewModel);
        startActivityForResult(orderIntent, ConstantImage.REQUEST_CODE_ORDER_IMAGE);
    }

    public void uploadImage()
    {
        Intent uploadIntent = new Intent(DISPreviewImageActivity.this, DISUploadImageActivity_.class);
        uploadIntent.putExtra(ConstantImage.PREVIEW_MODEL_IMAGE, (Parcelable) imagePreviewModel);
        startActivityForResult(uploadIntent, ConstantImage.REQUEST_CODE_UPLOAD_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == ConstantImage.REQUEST_CODE_ORDER_IMAGE)
            {
                data.putExtra(ConstantImage.EVENT_FLAG, ConstantImage.ORDER_IMAGE_FLAG);

            }
            data.putExtra(ConstantImage.EQUIPMENT_ID, equipmentId);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Click(R.id.dis_preview_image_btDowloadImage)
    public void downloadImage()
    {
        new AsyncTask<String, String, String>()
        {

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings)
            {
                BitmapFactory.Options bmOptions;
                bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 1;
                Bitmap bm = Utils.LoadImage(((ImageBaseModel) adapter.getModelImage().getImageList().get(vpImage.getCurrentItem())).getUrl(), bmOptions);
                OutputStream outStream = null;
                File outputFile = Utils.getOutputMediaFile(Utils.MEDIA_TYPE_IMAGE);
                try
                {
                    outStream = new FileOutputStream(outputFile);
                }
                catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }

                try
                {
                    bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(outputFile)));
                return null;
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                dialog.dismiss();
            }
        }.execute();
        Toast.makeText(this, "Saved image", Toast.LENGTH_LONG).show();

    }

}
