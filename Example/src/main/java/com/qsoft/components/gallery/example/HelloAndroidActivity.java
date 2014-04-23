package com.qsoft.components.gallery.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.qsoft.android.components.gallery.DISGalleryComponent;
import com.qsoft.android.components.gallery.PicAdapter;
import com.qsoft.android.components.gallery.URLImageModel;

import java.util.ArrayList;
import java.util.List;

public class HelloAndroidActivity extends Activity {

    private DISGalleryComponent disGalleryComponent;
    PicAdapter picAdapter;
    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disGalleryComponent = (DISGalleryComponent) findViewById(R.id.activity_main_dis_gallery);
        List<URLImageModel> urlImageModels = new ArrayList<URLImageModel>();
        urlImageModels.add(new URLImageModel("https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg", 1));
        urlImageModels.add(new URLImageModel("https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%2525" +
                "20Fire.jpg", 1));
        //
        urlImageModels.add(new URLImageModel("https://lh6.googleusercontent.com/-8HO-4vIFnlw/URq" +
                "uZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s1024/Antelope%252520Hallway.jpg", 1));
        urlImageModels.add(new URLImageModel("https://lh4.googleusercontent.com/-WIuWgVcU3Qw" +
                "/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg", 1));
        urlImageModels.add(new URLImageModel("https://lh3.googleusercontent.com/-s-AFpvgS" +
                "eew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg", 1));
        urlImageModels.add(new URLImageModel("https://lh5.googleusercontent.com/-bvmif9a9" +
                "YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s1024/Bee%252520and%252520Flower.jpg",1));
        urlImageModels.add(new URLImageModel("https://lh5.googleusercontent.com/-n7" +
                "mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s1024/Bonzai%252520Rock%252520Sunset.jpg",1));
        urlImageModels.add(new URLImageModel("https://lh6.googleusercontent.com/-4" +
                "CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s1024/Caterpillar.jpg", 1));
        urlImageModels.add(new URLImageModel("https://lh3.googleusercontent.com/" +
                "-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s1024/Chess.jpg", 1));
        urlImageModels.add(new URLImageModel("https://lh5.googleusercontent.com/" +
                "-WVpRptWH8Yw/URqugh-QmDI/AAAAAAAAAbs/E-MgBgtlUWU/s1024/Chihuly.jpg", 1));
        urlImageModels.add(new URLImageModel( "https://lh5.googleusercontent.com" +
                "/-0BDXkYmckbo/URquhKFW84I/AAAAAAAAAbs/ogQtHCTk2JQ/s1024/Closed%252520Door.jpg", 1));
        urlImageModels.add(new URLImageModel("https://lh3.googleusercontent.com/" +
                "-PyggXXZRykM/URquh-kVvoI/AAAAAAAAAbs/hFtDwhtrHHQ/s1024/Colorado%252520River%252520Sunset.jpg",1));
        urlImageModels.add(new URLImageModel("https://lh3.googleusercontent.com" +
                "/-ZAs4dNZtALc/URquikvOCWI/AAAAAAAAAbs/DXz4h3dll1Y/s1024/Colors%252520of%252520Autumn.jpg",1));
        picAdapter = new PicAdapter(this);
        picAdapter.setListPath(urlImageModels);
        picAdapter.setUrlImageModelsShow();
        disGalleryComponent.setPicAdapter(picAdapter, this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        disGalleryComponent.handle(requestCode,resultCode,data);
    }

}

