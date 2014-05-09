package com.qsoft.components.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * User: trungpt
 * Date: 4/23/14
 * Time: 2:44 PM
 */
public class ImageBaseModel implements Parcelable
{
    public String id;
    public String url;
    public Boolean isShown;
    public Long equipmentID;
    public Long index;

    public ImageBaseModel()
    {
    }

    public ImageBaseModel(String url,Boolean isShown)
    {
        this.isShown = isShown;
        this.url = url;
    }

    public ImageBaseModel(Parcel in)
    {
        id = in.readString();
        url = in.readString();
        isShown = (in.readInt() == 0) ? false : true;
        equipmentID = in.readLong();
        index = in.readLong();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Boolean isShown()
    {
        return isShown;
    }


    public void setShown(Boolean show)
    {
        isShown = show;
    }

    public Long getEquipmentID()
    {
        return equipmentID;
    }

    public void setEquipmentID(Long equipmentID)
    {
        this.equipmentID = equipmentID;
    }

    public Long getIndex()
    {
        return index;
    }

    public void setIndex(Long index)
    {
        this.index = index;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(id);
        parcel.writeString(url);
        parcel.writeInt(isShown ? 1 : 0);
        parcel.writeLong(equipmentID);
        parcel.writeLong(index);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public ImageBaseModel createFromParcel(Parcel in)
        {
            return new ImageBaseModel(in);
        }

        public ImageBaseModel[] newArray(int size)
        {
            return new ImageBaseModel[size];
        }
    };
}
