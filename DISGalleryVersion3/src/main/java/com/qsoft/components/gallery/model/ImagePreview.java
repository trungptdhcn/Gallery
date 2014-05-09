package com.qsoft.components.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * User: trungpt
 * Date: 4/24/14
 * Time: 4:45 PM
 */
public class ImagePreview extends ImageContainer
{
    public ImagePreview()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator<ImagePreview> CREATOR = new Parcelable.Creator<ImagePreview>()
    {
        public ImagePreview createFromParcel(Parcel in)
        {
            return new ImagePreview(in);
        }

        public ImagePreview[] newArray(int size)
        {
            return new ImagePreview[size];
        }
    };

    public ImagePreview(Parcel in)
    {
        super(in);
    }

    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
    }

}
