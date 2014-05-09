package com.qsoft.components.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: trungpt
 * Date: 4/28/14
 * Time: 11:42 AM
 */
public class ImageViewModel extends ImageBaseModel
{

    public ImageViewModel(String urlOnline,Boolean isShown)
    {
        super(urlOnline,isShown);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

//    public static final Parcelable.Creator<ImageViewModel> CREATOR = new Parcelable.Creator<ImageViewModel>()
//    {
//        public ImageViewModel createFromParcel(Parcel in)
//        {
//            return new ImageViewModel(in);
//        }
//
//        public ImageViewModel[] newArray(int size)
//        {
//            return new ImageViewModel[size];
//        }
//    };

    public ImageViewModel(Parcel in)
    {
        super(in);
    }

    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
    }
}
