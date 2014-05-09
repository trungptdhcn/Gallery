package com.qsoft.components.gallery.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.qsoft.components.gallery.adapter.DISSlideShowAdapter;
import com.qsoft.components.gallery.component.DISGalleryComponent;
import com.qsoft.components.gallery.model.ImageContainer;
import com.qsoft.components.gallery.model.ModelEvents;

import java.util.ArrayList;
import java.util.List;

public class HelloAndroidActivity extends Activity
{

    private DISGalleryComponent disGalleryComponent;
    private DISSlideShowAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disGalleryComponent = (DISGalleryComponent) findViewById(R.id.activity_main_dis_gallery);
        List<ImageModel> images = new ArrayList<ImageModel>();
        images.add(new ImageModel("https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg", true));
        images.add(new ImageModel("https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%2525" +
                "20Fire.jpg", true));
        images.add(new ImageModel("https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%2525" +
                "20Fire.jpg", true));
        images.add(new ImageModel("https://lh6.googleusercontent.com/-8HO-4vIFnlw/URq" +
                "uZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s1024/Antelope%252520Hallway.jpg", true));
        images.add(new ImageModel("https://lh4.googleusercontent.com/-WIuWgVcU3Qw" +
                "/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg", true));
        images.add(new ImageModel("https://lh3.googleusercontent.com/-s-AFpvgS" +
                "eew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg", true));
        images.add(new ImageModel("https://lh5.googleusercontent.com/-bvmif9a9" +
                "YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s1024/Bee%252520and%252520Flower.jpg", true));
        images.add(new ImageModel("https://lh5.googleusercontent.com/-n7" +
                "mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s1024/Bonzai%252520Rock%252520Sunset.jpg", true));
        images.add(new ImageModel("https://lh6.googleusercontent.com/-4" +
                "CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s1024/Caterpillar.jpg", true));
        images.add(new ImageModel("https://lh3.googleusercontent.com/" +
                "-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s1024/Chess.jpg", true));
        images.add(new ImageModel("https://lh5.googleusercontent.com/" +
                "-WVpRptWH8Yw/URqugh-QmDI/AAAAAAAAAbs/E-MgBgtlUWU/s1024/Chihuly.jpg", true));
        images.add(new ImageModel("https://lh5.googleusercontent.com" +
                "/-0BDXkYmckbo/URquhKFW84I/AAAAAAAAAbs/ogQtHCTk2JQ/s1024/Closed%252520Door.jpg", true));
        images.add(new ImageModel("https://lh3.googleusercontent.com/" +
                "-PyggXXZRykM/URquh-kVvoI/AAAAAAAAAbs/hFtDwhtrHHQ/s1024/Colorado%252520River%252520Sunset.jpg", true));
        images.add(new ImageModel("https://lh3.googleusercontent.com" +
                "/-ZAs4dNZtALc/URquikvOCWI/AAAAAAAAAbs/DXz4h3dll1Y/s1024/Colors%252520of%252520Autumn.jpg", true));
        adapter = new DISSlideShowAdapter(this, images, new ModelEvents()
        {
            @Override
            public void onCLickAction(ImageContainer imageContainer)
            {
                startActivity(new Intent(getApplicationContext(),NewActivity.class));
            }
        });
        disGalleryComponent.setSlideShow(adapter);
    }
}

