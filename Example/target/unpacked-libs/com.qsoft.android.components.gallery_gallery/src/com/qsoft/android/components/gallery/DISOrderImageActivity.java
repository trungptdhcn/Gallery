package com.qsoft.android.components.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import com.qsoft.android.components.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 2/24/14
 * Time: 5:11 PM
 */
public class DISOrderImageActivity extends Activity implements View.OnClickListener
{
    private GridView gridViewImage;
    private OrderImageAdapter orderImageAdapter;
    private Button btCancel;
    private Button btSave;
    List<URLImageModel> urlImageModelAlls= new ArrayList<URLImageModel>();
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dis_order_image);
        gridViewImage = (GridView) findViewById(R.id.dis_order_image_grvImage);
        btCancel = (Button)findViewById(R.id.dis_grid_image_btCancel);
        btSave = (Button)findViewById(R.id.dis_grid_image_btSave);
        urlImageModelAlls =  getIntent().getParcelableArrayListExtra(DISGalleryComponent.URL_IMAGES_ALL_LIST_FLAG);
        orderImageAdapter = new OrderImageAdapter(this,urlImageModelAlls);
        gridViewImage.setAdapter(orderImageAdapter);
        btSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.dis_grid_image_btSave) {
            eventSave();
        }
        else if(i== R.id.dis_grid_image_btCancel){
            eventCancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void eventSave()
    {
        Intent editIntent = new Intent();
        editIntent.putParcelableArrayListExtra(DISGalleryComponent.URL_IMAGES_ALL_LIST_FLAG, (ArrayList) orderImageAdapter.getUrlImageModelAllList());
        editIntent.putExtra(DISGalleryComponent.SHOW_IMAGE_FLAG, DISGalleryComponent.RESULT_SHOW_IMAGE);
        setResult(RESULT_OK, editIntent);
        finish();
    }
    public void eventCancel()
    {

    }

}
