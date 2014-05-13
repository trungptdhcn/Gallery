package com.qsoft.components.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.qsoft.components.gallery.model.dto.EquipmentHistoryDTOLib;
import com.qsoft.components.gallery.model.dto.LocationDTOLib;

import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 4/24/14
 * Time: 4:41 PM
 */
public abstract class ImageContainer<T extends ImageBaseModel, L> implements Parcelable
{
    public String urlDeleteImage;
    public String urlUploadImage;
    public String urlGetImage;
    public String urlOrderImage;
    public String urlDownloadImage;
    public List<T> imageList = new ArrayList<T>();
    public int position;
    public Long equipmentId;
    public Long userId;
    public LocationDTOLib locationDTOLib;
    public EquipmentHistoryDTOLib equipmentHistoryDTOLib;
    public L locationDTO;

    protected ImageContainer()
    {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeTypedList(imageList);
        parcel.writeInt(position);
        parcel.writeLong(equipmentId);
        parcel.writeLong(userId);
        parcel.writeString(urlDeleteImage);
        parcel.writeString(urlUploadImage);
        parcel.writeString(urlGetImage);
        parcel.writeString(urlOrderImage);
        parcel.writeString(urlDownloadImage);
        if(locationDTO != null)
        {
            parcel.writeParcelable((Parcelable) locationDTO, i);
        }
        if (locationDTOLib != null)
        {
            parcel.writeParcelable(locationDTOLib, i);
        }
        if (equipmentHistoryDTOLib != null)
        {
            parcel.writeParcelable(equipmentHistoryDTOLib, i);
        }

    }

    public ImageContainer(Parcel in)
    {
        imageList = new ArrayList<T>();
        in.readTypedList(imageList, ImageBaseModel.CREATOR);
        position = in.readInt();
        equipmentId = in.readLong();
        userId = in.readLong();
        urlDeleteImage = in.readString();
        urlUploadImage = in.readString();
        urlGetImage = in.readString();
        urlOrderImage = in.readString();
        urlDownloadImage = in.readString();
        locationDTOLib = in.readParcelable(LocationDTOLib.class.getClassLoader());
        equipmentHistoryDTOLib = in.readParcelable(EquipmentHistoryDTOLib.class.getClassLoader());

    }


    public List<T> getImageList()
    {
        return imageList;
    }

    public void setImageList(List<T> imageList)
    {
        this.imageList = imageList;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public Long getEquipmentId()
    {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUrlDeleteImage()
    {
        return urlDeleteImage;
    }

    public void setUrlDeleteImage(String urlDeleteImage)
    {
        this.urlDeleteImage = urlDeleteImage;
    }

    public String getUrlUploadImage()
    {
        return urlUploadImage;
    }

    public void setUrlUploadImage(String urlUploadImage)
    {
        this.urlUploadImage = urlUploadImage;
    }

    public String getUrlGetImage()
    {
        return urlGetImage;
    }

    public void setUrlGetImage(String urlGetImage)
    {
        this.urlGetImage = urlGetImage;
    }

    public String getUrlOrderImage()
    {
        return urlOrderImage;
    }

    public void setUrlOrderImage(String urlOrderImage)
    {
        this.urlOrderImage = urlOrderImage;
    }

    public String getUrlDownloadImage()
    {
        return urlDownloadImage;
    }

    public void setUrlDownloadImage(String urlDownloadImage)
    {
        this.urlDownloadImage = urlDownloadImage;
    }

    public LocationDTOLib getLocationDTOLib()
    {
        return locationDTOLib;
    }

    public void setLocationDTOLib(LocationDTOLib locationDTOLib)
    {
        this.locationDTOLib = locationDTOLib;
    }

    public EquipmentHistoryDTOLib getEquipmentHistoryDTOLib()
    {
        return equipmentHistoryDTOLib;
    }

    public void setEquipmentHistoryDTOLib(EquipmentHistoryDTOLib equipmentHistoryDTOLib)
    {
        this.equipmentHistoryDTOLib = equipmentHistoryDTOLib;
    }

    public L getLocationDTO()
    {
        return locationDTO;
    }

    public void setLocationDTO(L locationDTO)
    {
        this.locationDTO = locationDTO;
    }
}
