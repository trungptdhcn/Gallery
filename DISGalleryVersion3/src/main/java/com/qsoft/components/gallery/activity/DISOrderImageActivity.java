package com.qsoft.components.gallery.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.components.gallery.R;
import com.qsoft.components.gallery.adapter.DISOrderImageAdapter;
import com.qsoft.components.gallery.adapter.DISUploadImageAdapter;
import com.qsoft.components.gallery.common.ConstantImage;
import com.qsoft.components.gallery.dynamicgrid.DynamicGridView;
import com.qsoft.components.gallery.dynamicgrid.MyGestureDetector;
import com.qsoft.components.gallery.model.ImageBaseModel;
import com.qsoft.components.gallery.model.ImagePreview;
import com.qsoft.components.gallery.model.dto.ImageDTO;
import com.qsoft.components.gallery.model.dto.ImageListDTO;
import com.qsoft.components.gallery.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 4/25/14
 * Time: 7:04 PM
 */
@EActivity(R.layout.dis_order_image)
public class DISOrderImageActivity extends Activity
{
    @ViewById(R.id.dis_order_dynamic_grid)
    public DynamicGridView gridView;
    @ViewById(R.id.dis_order_image_btSave)
    public Button btSave;
    @ViewById(R.id.dis_order_image_btCancel)
    public Button btCancel;


    DISOrderImageAdapter adapter;
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    ImagePreview imagePreview;

    @AfterViews
    public void afterView()
    {
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        imagePreview = getIntent().getParcelableExtra(ConstantImage.PREVIEW_MODEL_IMAGE);

        adapter = new DISOrderImageAdapter(this, imagePreview.getImageList(), 3);
        gridView.setAdapter(adapter);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener()
                {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout()
                    {
                        if (adapter.getNumColumns() == 0)
                        {
                            final int numColumns = (int) Math.floor(
                                    gridView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                            if (numColumns > 0)
                            {
                                final int columnWidth =
                                        (gridView.getWidth() / numColumns) - mImageThumbSpacing;
                                adapter.setNumColumns(numColumns);
                                adapter.setItemHeight(columnWidth);
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

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                gridView.startEditMode();
                return false;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
            }
        });

        gridView.setOnTouchListener(new View.OnTouchListener()
        {
            GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector(getApplicationContext()));

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (gridView.isEditMode())
                {
                    gridView.stopEditMode();
                }
                return gestureDetector.onTouchEvent(event);
            }
        });
        ((DISOrderImageAdapter) gridView.getAdapter()).getmItems();

    }

//    @Override
//    public void onBackPressed()
//    {
//        if (gridView.isEditMode())
//        {
//            gridView.stopEditMode();
//        }
//        else
//        {
//            super.onBackPressed();
//        }
//    }

    @Click(R.id.dis_order_image_btCancel)
    public void eventCancel()
    {

    }

    @Click(R.id.dis_order_image_btSave)
    public void eventSave()
    {

        final Intent orderIntent = new Intent();
        List<Object> objects = ((DISOrderImageAdapter) gridView.getAdapter()).getmItems();
        final List<ImageDTO> imageDTOs = new ArrayList<ImageDTO>();
        for (int i = 0; i < objects.size(); i++)
        {
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setFileId(((ImageBaseModel) objects.get(i)).getId());
            imageDTO.setShown(((ImageBaseModel) objects.get(i)).isShown());
            imageDTO.setIndex(Long.valueOf(i));
            imageDTOs.add(imageDTO);
        }

        new AsyncTask<String, String, String>()
        {
            @Override
            protected String doInBackground(String... strings)
            {
                String result = "";
                try
                {
                    result = Utils.orderImage(imagePreview.getUrlOrderImage(), imageDTOs, imagePreview.getEquipmentId());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result)
            {
                super.onPostExecute(result);
                if (result.contains("SUCCESS"))
                {
                    setResult(RESULT_OK,orderIntent);
                    finish();
                }
            }
        }.execute();


    }
}