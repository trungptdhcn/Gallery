package com.qsoft.components.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 4/24/14
 * Time: 4:44 PM
 */
public class ImageSlide extends ImageContainer
{
    public ImageSlide()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator<ImageSlide> CREATOR = new Parcelable.Creator<ImageSlide>()
    {
        public ImageSlide createFromParcel(Parcel in)
        {
            return new ImageSlide(in);
        }

        public ImageSlide[] newArray(int size)
        {
            return new ImageSlide[size];
        }
    };

    public ImageSlide(Parcel in)
    {
        super(in);
    }

    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
    }

}
