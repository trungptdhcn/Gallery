package com.qsoft.components.gallery.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: trungpt
 * Date: 4/26/14
 * Time: 11:19 AM
 */
public class ImageUpload extends ImageContainer
{

    public ImageUpload()
    {

    }
    @Override
    public int describeContents()
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static final Parcelable.Creator<ImageUpload> CREATOR = new Parcelable.Creator<ImageUpload>()
    {
        public ImageUpload createFromParcel(Parcel in)
        {
            return new ImageUpload(in);
        }

        public ImageUpload[] newArray(int size)
        {
            return new ImageUpload[size];
        }
    };

    public ImageUpload(Parcel in)
    {
        super(in);
    }

    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
    }
}
