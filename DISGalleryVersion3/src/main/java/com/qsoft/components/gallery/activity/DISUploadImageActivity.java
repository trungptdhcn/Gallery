package com.qsoft.components.gallery.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.adapter.DISUploadImageAdapter;
import com.qsoft.components.gallery.common.ConstantImage;
import com.qsoft.components.gallery.common.LocationDTOInterface;
import com.qsoft.components.gallery.model.*;
import com.qsoft.components.gallery.model.dto.EquipmentHistoryDTOLib;
import com.qsoft.components.gallery.model.dto.LocationDTOLib;
import com.qsoft.components.gallery.model.dto.ResponseDTO;
import com.qsoft.components.gallery.utils.GalleryUtils;

import java.io.IOException;
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
    LocationDTOInterface locationDTOInterface;


    @AfterViews
    public void afterView()
    {
        imagePreviewModel = getIntent().getParcelableExtra(ConstantImage.PREVIEW_MODEL_IMAGE);
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
        imageUpload = new ImageUpload();
        try
        {
            locationDTOInterface = GalleryUtils.getLocationDTO(getApplicationContext());
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        imageUpload.setUserId(imagePreviewModel.getUserId());
        imageUpload.setEquipmentId(imagePreviewModel.getEquipmentId());
        imageUpload.setPosition(imagePreviewModel.getPosition());
        imageUpload.setUrlUploadImage(imagePreviewModel.getUrlUploadImage());
        imageUpload.setLocationDTOLib(imagePreviewModel.getLocationDTOLib());
        if (imagePreviewModel.getEquipmentHistoryDTOLib() != null)
        {
            imageUpload.setEquipmentHistoryDTOLib(imagePreviewModel.getEquipmentHistoryDTOLib());
        }
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
                                if (GalleryUtils.hasJellyBean())
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
        btClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
//            case R.id.dis_upload_image_btClose:
//                finish();
//                break;
            case R.id.dis_upload_image_btUpload:
                try
                {
                    if (GalleryUtils.hasConnection(getApplicationContext()))
                    {
                        try
                        {
                            locationDTOInterface = GalleryUtils.getLocationDTO(getApplicationContext());
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                        if (locationDTOInterface != null && locationDTOInterface.getLatitude() != null
                                && locationDTOInterface.getLongitude() != null)
                        {
                            imagePreviewModel.setLocationDTO(locationDTOInterface);
                            uploadImage(imagePreviewModel.getUrlUploadImage(), imagePreviewModel.getEquipmentId(), imagePreviewModel.getUserId());

                        }
                        else
                        {
                            Toast.makeText(this, "Can't load location", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "The internet connection has been interrupted. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }

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
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                asyncTaskUpload.cancel(true);
                dialog.dismiss();
            }
        });
        asyncTaskUpload = new AsyncTask<EquipmentHistoryDTOLib, Integer, EquipmentHistoryDTOLib>()
        {

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected EquipmentHistoryDTOLib doInBackground(EquipmentHistoryDTOLib... equipmentHistoryDTOLibs)
            {
                String result = null;
                long total = 0;
                EquipmentHistoryDTOLib equipmentHistoryDTOLib = null;
                try
                {
                    result = GalleryUtils.multiPart(urlUpload, imageUploads.get(0), equipmentId, userId, null, (LocationDTOInterface) imagePreviewModel.getLocationDTO());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (result.contains("SUCCESS"))
                {
                    total += 1;
                    dialog.incrementProgressBy(1);
                    publishProgress((int) (total));
                    ResponseDTO responseDTO = new Gson().fromJson(result, ResponseDTO.class);
                    equipmentHistoryDTOLib = responseDTO.getResultDTO().getEquipmentHistoryDTOLib();
                    Long equipmentHistoryId = equipmentHistoryDTOLib.getWebId();
                    for (int i = 1; i < imageUploads.size(); i++)
                    {
                        try
                        {
                            String stringResult = GalleryUtils.multiPart(urlUpload, imageUploads.get(i),
                                    equipmentId, userId, equipmentHistoryId, (LocationDTOInterface) imagePreviewModel.getLocationDTO());
                            if (stringResult.contains("SUCCESS"))
                            {
                                total += 1;
                                dialog.incrementProgressBy(1);
                            }
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                return equipmentHistoryDTOLib;
            }

            @Override
            protected void onProgressUpdate(Integer... progress)
            {
                super.onProgressUpdate(progress);
                dialog.setProgress(progress[0]);
            }

            @Override
            protected void onPostExecute(EquipmentHistoryDTOLib equipmentHistoryDTOLib)
            {
                super.onPostExecute(equipmentHistoryDTOLib);
                uploadIntent.putExtra(ConstantImage.EVENT_FLAG, ConstantImage.UPLOAD_IMAGE_FLAG);
                uploadIntent.putExtra(ConstantImage.EQUIPMENT_ID, equipmentId);
                uploadIntent.putExtra(ConstantImage.EQUIPMENT_HISTORY, equipmentHistoryDTOLib);
                uploadIntent.putExtra(ConstantImage.IMAGE_LOCATION_DTO, (Parcelable) imagePreviewModel.getLocationDTO());
                setResult(RESULT_OK, uploadIntent);
                dialog.dismiss();
                finish();
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
                if (GalleryUtils.hasConnection(getApplicationContext()))
                {
                    if (locationDTOInterface != null && locationDTOInterface.getLatitude() != null
                            && locationDTOInterface.getLongitude() != null)
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
                        dialog.setMax(1);
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
                        asyncTaskTakePhoto = new AsyncTask<EquipmentHistoryDTOLib, Integer, EquipmentHistoryDTOLib>()
                        {
                            @Override
                            protected void onPreExecute()
                            {
                                super.onPreExecute();
                                dialog.show();

                            }

                            @Override
                            protected EquipmentHistoryDTOLib doInBackground(EquipmentHistoryDTOLib... equipmentHistoryDTOLibs)
                            {
                                String result = null;
                                EquipmentHistoryDTOLib equipmentHistoryDTOLib = null;
                                long total = 0;
                                try
                                {
                                    result = GalleryUtils.multiPart(imagePreviewModel.getUrlUploadImage(), imageUploadModel, imagePreviewModel.getEquipmentId(), imagePreviewModel.getUserId(), null, (LocationDTOInterface) imagePreviewModel.getLocationDTO());
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                                if (result.contains("SUCCESS"))
                                {
                                    ResponseDTO responseDTO = new Gson().fromJson(result, ResponseDTO.class);
                                    equipmentHistoryDTOLib = responseDTO.getResultDTO().getEquipmentHistoryDTOLib();
                                    total += 1;
                                    publishProgress((int) total);

                                }
                                return equipmentHistoryDTOLib;

                            }

                            @Override
                            protected void onProgressUpdate(Integer... progress)
                            {
                                super.onProgressUpdate(progress);
                                dialog.setProgress(progress[0]);
                            }

                            @Override
                            protected void onPostExecute(EquipmentHistoryDTOLib equipmentHistoryDTOLibs)
                            {
                                super.onPostExecute(equipmentHistoryDTOLibs);
                                if (equipmentHistoryDTOLibs != null)
                                {
                                    Intent takePhotoIntent = new Intent();
                                    takePhotoIntent.putExtra(ConstantImage.EVENT_FLAG, ConstantImage.TAKE_PHOTO_FLAG);
                                    takePhotoIntent.putExtra(ConstantImage.EQUIPMENT_ID, imagePreviewModel.equipmentId);
                                    takePhotoIntent.putExtra(ConstantImage.EQUIPMENT_HISTORY, equipmentHistoryDTOLibs);
                                    takePhotoIntent.putExtra(ConstantImage.IMAGE_LOCATION_DTO, imagePreviewModel.getLocationDTOLib());
                                    setResult(RESULT_OK, takePhotoIntent);
                                    dialog.dismiss();
                                    finish();
                                }
                            }
                        }.execute();
                    }
                    else
                    {
                        Toast.makeText(this, "Can't load location", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "The internet connection has been interrupted. Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
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
