package com.qsoft.components.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: trungpt
 * Date: 4/25/14
 * Time: 7:23 PM
 */
public class ImageOrder extends ImageContainer
{
    public ImageOrder()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator<ImageOrder> CREATOR = new Parcelable.Creator<ImageOrder>()
    {
        public ImageOrder createFromParcel(Parcel in)
        {
            return new ImageOrder(in);
        }

        public ImageOrder[] newArray(int size)
        {
            return new ImageOrder[size];
        }
    };

    public ImageOrder(Parcel in)
    {
        super(in);
    }

    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
    }

}
