package com.qsoft.android.components.gallery;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridView;
import com.qsoft.android.components.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 2/25/14
 * Time: 10:35 AM
 */
public class DISUploadImageActivity extends Activity
{
    private GridView gridViewImage;
    private UploadImageAdapter uploadImageAdapter;
    private List<URLImageModel> urlImageModelListUpload = new ArrayList<URLImageModel>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dis_upload_image);
        gridViewImage = (GridView) findViewById(R.id.dis_upload_image_grvImage);
        uploadImageAdapter = new UploadImageAdapter(this);
        uploadImageAdapter.setUrlImageModelsSDcard();
        gridViewImage.setAdapter(uploadImageAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            String pathImage = null;
            Intent uploadIntent = new Intent();
            if (requestCode == DISGalleryComponent.RESULT_TAKE_IMAGE_CAMERA)
            {
//                Uri uriImage = data.getData();
//                if (uriImage != null)
//                {
//                    pathImage = uriImage.getPath();
//                }
//                else
//                {
                pathImage = getRealPathFromURI();
//                }
            }
            URLImageModel urlImageModelTakePic = new URLImageModel();
            urlImageModelTakePic.setUrl(pathImage);
            uploadImageAdapter.notifyDataSetChanged();
            urlImageModelListUpload = getUrlImageModelListUpload(uploadImageAdapter.getUrlImageModelsSDcard());
            urlImageModelListUpload.add(urlImageModelTakePic);
            uploadIntent.putParcelableArrayListExtra("list_image_upload", (ArrayList) urlImageModelListUpload);
            setResult(RESULT_OK, uploadIntent);
            finish();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        uploadImageAdapter.destroyCursor();
    }

    public String getRealPathFromURI()
    {
        List<URLImageModel> urlImageModelList = new ArrayList<URLImageModel>();
        int columnIndex;
        String[] imgColumnID = {MediaStore.Images.Thumbnails._ID};
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor imageCursor = this.managedQuery(uri, imgColumnID, null, null,
                MediaStore.Images.Thumbnails.IMAGE_ID);
        columnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);

        String[] dataLocation = {MediaStore.Images.Media.DATA};
        imageCursor = this.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                dataLocation, null, null, null);
        columnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        imageCursor.moveToLast();
        String imgPath = imageCursor.getString(columnIndex);
        imageCursor.close();
        return ("file://" + imgPath);
    }

    private List<URLImageModel> getUrlImageModelListUpload(List<URLImageModel> urlImageModelList)
    {
        List<URLImageModel> urlImageModelListUpload = new ArrayList<URLImageModel>();
        for (URLImageModel urlImageModel : urlImageModelList)
        {
            if (urlImageModel.getFlag() == 1)
            {
                urlImageModelListUpload.add(urlImageModel);
            }
        }
        return urlImageModelListUpload;
    }
}
