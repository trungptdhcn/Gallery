package com.qsoft.android.components.gallery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: trungpt
 * Date: 2/21/14
 * Time: 3:33 PM
 */
public class URLImageModel implements Parcelable
{
    private String url;
    private Integer flag = 1;


    public URLImageModel()
    {
    }

    public URLImageModel(String url, Integer flag)
    {
        this.url = url;
        this.flag = flag;
    }

    public URLImageModel(Parcel in) {
        url = in.readString();
        flag = in.readInt();
    }

    public URLImageModel(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Integer getFlag()
    {
        return flag;
    }

    public void setFlag(Integer flag)
    {
        this.flag = flag;
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
        parcel.writeInt(flag);
    }

    public static final Parcelable.Creator<URLImageModel> CREATOR = new Parcelable.Creator<URLImageModel>()
    {
        public URLImageModel createFromParcel(Parcel in)
        {
            return new URLImageModel(in);
        }
        public URLImageModel[] newArray(int size)
        {
            return new URLImageModel[size];
        }
    };
}
