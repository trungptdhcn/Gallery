package com.qsoft.components.gallery.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import com.google.gson.Gson;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.adapter.DISUploadImageAdapter;
import com.qsoft.components.gallery.common.ConstantImage;
import com.qsoft.components.gallery.model.*;
import com.qsoft.components.gallery.model.dto.ImageDTO;
import com.qsoft.components.gallery.model.dto.ImageListDTO;
import com.qsoft.components.gallery.utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 4/26/14
 * Time: 10:47 AM
 */
@EActivity(R.layout.dis_upload_image)
public class DISUploadImageActivity extends Activity implements View.OnClickListener
{
    @ViewById(R.id.dis_upload_image_grvImage)
    public GridView gridView;
    @ViewById(R.id.dis_upload_image_btUpload)
    public Button btUpload;
    @ViewById(R.id.dis_upload_image_btClose)
    public Button btClose;

    public DISUploadImageAdapter imageAdapter;
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImagePreview imagePreviewModel;
    private Uri fileUri;
    public static Object objLock = new Object();
    ProgressDialog dialog;
    ImageUpload imageUpload;
    AsyncTask asyncTaskTakePhoto;
    AsyncTask asyncTaskUpload;


    @AfterViews
    public void afterView()
    {
        imagePreviewModel = getIntent().getParcelableExtra(ConstantImage.PREVIEW_MODEL_IMAGE);
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        imageUpload = new ImageUpload();
        imageUpload.setUserId(imagePreviewModel.getUserId());
        imageUpload.setEquipmentId(imagePreviewModel.getEquipmentId());
        imageUpload.setPosition(imagePreviewModel.getPosition());
        imageUpload.setUrlUploadImage(imagePreviewModel.getUrlUploadImage());

        imageAdapter = new DISUploadImageAdapter(this, imageUpload);
        imageAdapter.initializeThumbnail(imageAdapter.getListIdRealImage());
        gridView.setAdapter(imageAdapter);
        fileUri = imageAdapter.getFileUri();
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener()
                {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout()
                    {
                        if (imageAdapter.getNumColumns() == 0)
                        {
                            final int numColumns = (int) Math.floor(
                                    gridView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                            if (numColumns > 0)
                            {
                                final int columnWidth =
                                        (gridView.getWidth() / numColumns) - mImageThumbSpacing;
                                imageAdapter.setNumColumns(numColumns);
                                imageAdapter.setItemHeight(columnWidth);
                                if (Utils.hasJellyBean())
                                {
                                    gridView.getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                }
                                else
                                {
                                    gridView.getViewTreeObserver()
                                            .removeGlobalOnLayoutListener(this);
                                }
                            }
                        }
                    }
                });

        boolean pauseOnScroll = false; // or true
        boolean pauseOnFling = true; // or false
        PauseOnScrollListener listener = new PauseOnScrollListener(((DISUploadImageAdapter) gridView.getAdapter()).getImageLoader(), pauseOnScroll, pauseOnFling);
        gridView.setOnScrollListener(listener);


        btUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.dis_upload_image_btClose:
                break;
            case R.id.dis_upload_image_btUpload:
                try
                {
                    uploadImage(imagePreviewModel.getUrlUploadImage(), imagePreviewModel.getEquipmentId(), imagePreviewModel.getUserId());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void uploadImage(final String urlUpload, final Long equipmentId, final Long userId) throws IOException
    {
        final Intent uploadIntent = new Intent();
        DISUploadImageAdapter adapter = ((DISUploadImageAdapter) gridView.getAdapter());
        adapter.notifyDataSetChanged();
        final List<ImageUploadModel> imageList = adapter.getImageModels().getImageList();
        final List<ImageUploadModel> imageUploads = new ArrayList<ImageUploadModel>();
        for (ImageUploadModel imageUploadModel : imageList)
        {
            if (imageUploadModel.isSelection())
            {
                imageUploadModel.setUrl("file://" + imageUploadModel.getRealUri());
                imageUploads.add(imageUploadModel);
            }
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading..");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(imageUploads.size());
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                asyncTaskUpload.cancel(true);
                dialog.dismiss();
            }
        });
        asyncTaskUpload = new AsyncTask<String, String, String>()
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
                String result = null;
                long total = 0;
                try
                {
                    result = Utils.multiPart(urlUpload, imageUploads.get(0), equipmentId, userId, null);
                    total +=1;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (result.contains("SUCCESS"))
                {
                    for (int i = 1; i < imageUploads.size(); i++)
                    {
                        try
                        {
                            Utils.multiPart(urlUpload, imageUploads.get(i), equipmentId, userId, null);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(String result)
            {
                super.onPostExecute(result);
                if (result.contains("SUCCESS"))
                {
                    List<ImageViewModel> imageViewModels = new ArrayList<ImageViewModel>();
                    for (ImageUploadModel imageUploadModel : imageUploads)
                    {
                        ImageViewModel imageViewModel = new ImageViewModel(imageUploadModel.getUrl(), false);
                        imageViewModel.setEquipmentID(equipmentId);
                        imageViewModel.setIndex(imageUploadModel.getIndex());
                        imageViewModel.setId(imageUploadModel.getId());
                        imageViewModels.add(imageViewModel);
                    }
                    uploadIntent.putParcelableArrayListExtra(ConstantImage.RESULT_LIST, (ArrayList<? extends Parcelable>) imageViewModels);
                    uploadIntent.putExtra(ConstantImage.EVENT_FLAG, ConstantImage.UPLOAD_IMAGE_FLAG);
                    setResult(RESULT_OK, uploadIntent);
                    dialog.dismiss();
                    finish();
                }
            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == ConstantImage.REQUEST_CODE_CAMERA)
            {
                fileUri = imageAdapter.getFileUri();
                Log.d("dfs", fileUri.toString());
                final ImageUploadModel imageUploadModel = new ImageUploadModel();
                imageUploadModel.setRealUri(fileUri.toString().substring(7, fileUri.toString().length()));
                imageUploadModel.setUrl(fileUri.toString());
                imageUploadModel.setSelection(true);
                imageUploadModel.setShown(false);
                imageUploadModel.setEquipmentID(imageUpload.getEquipmentId());
                dialog = new ProgressDialog(this);
                dialog.setMessage("Uploading..");
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setMax(2);
                dialog.setCancelable(false);
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        asyncTaskTakePhoto.cancel(true);
                        dialog.dismiss();
                    }
                });
                asyncTaskTakePhoto = new AsyncTask<String, Integer, String>()
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
                        String result = null;
                        long total = 0;
                        try
                        {
                            result = Utils.multiPart(imagePreviewModel.getUrlUploadImage(), imageUploadModel, imagePreviewModel.getEquipmentId(), imagePreviewModel.getUserId(), null);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        if (result.contains("SUCCESS"))
                        {
                            total += 1;
                            publishProgress((int) total);
                            return result;
                        }
                        return null;

                    }

                    @Override
                    protected void onProgressUpdate(Integer... progress)
                    {
                        super.onProgressUpdate(progress);
                        dialog.setProgress(progress[0]);
                    }

                    @Override
                    protected void onPostExecute(String result)
                    {
                        super.onPostExecute(result);
                        if (result != null)
                        {
                            Intent takePhotoIntent = new Intent();
                            takePhotoIntent.putExtra(ConstantImage.EVENT_FLAG, ConstantImage.TAKE_PHOTO_FLAG);
                            setResult(RESULT_OK, takePhotoIntent);
                            dialog.dismiss();
                            finish();
                        }
                    }
                }.execute();

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (fileUri != null)
        {
            outState.putParcelable("file_uri", fileUri);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

}
