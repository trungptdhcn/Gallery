package com.qsoft.components.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: trungpt
 * Date: 4/23/14
 * Time: 2:43 PM
 */
public class Image implements ImageContainer,Parcelable
{
    private String url;
    private Boolean isShow = true;

    public void setShow( Boolean isShow)
    {
        this.isShow = isShow;
    }

    public Image(String url, Boolean show)
    {
        this.url = url;
        isShow = show;
    }

    public Image(Parcel in) {
        url = in.readString();
        isShow = (in.readInt() == 0) ? false : true;
    }


    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public Boolean isShow()
    {
        return isShow;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int describeContents()
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(url);
        parcel.writeInt(isShow ? 1 : 0);
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>()
    {
        public Image createFromParcel(Parcel in)
        {
            return new Image(in);
        }
        public Image[] newArray(int size)
        {
            return new Image[size];
        }
    };
}
