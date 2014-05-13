package com.qsoft.components.gallery.model.dto;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.qsoft.components.gallery.common.ConstantImage;
import com.qsoft.components.gallery.model.ImageBaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: trungpt
 * Date: 5/9/14
 * Time: 3:24 PM
 */
public class EquipmentHistoryDTOLib implements Parcelable
{

    @SerializedName("userName")
    String userName;
    @SerializedName("userId")
    Long userId;
    @SerializedName("userImageId")
    String userImageId;
    @SerializedName("time")
    String time;
    @SerializedName("listEquipmentImageId")
    List<String> listEquipmentImageId;
    @SerializedName("internalId")
    String internalId;
    // new logic scanIn & scanOut
    @SerializedName("hourMeter")
    String hourMeter;
    @SerializedName("fuelLevel")
    String fuelLevel;
    @SerializedName("historyType")
    String historyType;
    @SerializedName("estimatedReturn")
    String estimatedReturn;
    @SerializedName("note")
    String note;
    @SerializedName("contactId")
    Long contactId;
    // fileId of signature & scan out history's pdf file
    @SerializedName("signatureFileId")
    String signatureFileId;
    @SerializedName("pdfFileId")
    String pdfFileId;
    @SerializedName("equipmentId")
    Long equipmentId;

    // fields for word order
    @SerializedName("outSiteServiceDocumentNumber")
    String outSiteServiceDocumentNumber;
    @SerializedName("outSiteServiceDate")
    String outSiteServiceDate;
    @SerializedName("outSiteServicePrice")
    String outSiteServicePrice;
    @SerializedName("outSiteServiceProvider")
    String outSiteServiceProvider;
    @SerializedName("webId")
    Long webId;

    public Long getContactId()
    {
        return contactId;
    }

    public void setContactId(Long contactId)
    {
        this.contactId = contactId;
    }

    public Long getEquipmentId()
    {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public String getEstimatedReturn()
    {
        return estimatedReturn;
    }

    public void setEstimatedReturn(String estimatedReturn)
    {
        this.estimatedReturn = estimatedReturn;
    }

    public String getFuelLevel()
    {
        return fuelLevel;
    }

    public void setFuelLevel(String fuelLevel)
    {
        this.fuelLevel = fuelLevel;
    }

    public String getHistoryType()
    {
        return historyType;
    }

    public void setHistoryType(String historyType)
    {
        this.historyType = historyType;
    }

    public String getHourMeter()
    {
        return hourMeter;
    }

    public void setHourMeter(String hourMeter)
    {
        this.hourMeter = hourMeter;
    }

    public String getInternalId()
    {
        return internalId;
    }

    public void setInternalId(String internalId)
    {
        this.internalId = internalId;
    }

    public List<String> getListEquipmentImageId()
    {
        return listEquipmentImageId;
    }

    public void setListEquipmentImageId(List<String> listEquipmentImageId)
    {
        this.listEquipmentImageId = listEquipmentImageId;
    }


    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getOutSiteServiceDate()
    {
        return outSiteServiceDate;
    }

    public void setOutSiteServiceDate(String outSiteServiceDate)
    {
        this.outSiteServiceDate = outSiteServiceDate;
    }

    public String getOutSiteServiceDocumentNumber()
    {
        return outSiteServiceDocumentNumber;
    }

    public void setOutSiteServiceDocumentNumber(String outSiteServiceDocumentNumber)
    {
        this.outSiteServiceDocumentNumber = outSiteServiceDocumentNumber;
    }

    public String getOutSiteServicePrice()
    {
        return outSiteServicePrice;
    }

    public void setOutSiteServicePrice(String outSiteServicePrice)
    {
        this.outSiteServicePrice = outSiteServicePrice;
    }

    public String getOutSiteServiceProvider()
    {
        return outSiteServiceProvider;
    }

    public void setOutSiteServiceProvider(String outSiteServiceProvider)
    {
        this.outSiteServiceProvider = outSiteServiceProvider;
    }

    public String getPdfFileId()
    {
        return pdfFileId;
    }

    public void setPdfFileId(String pdfFileId)
    {
        this.pdfFileId = pdfFileId;
    }

    public String getSignatureFileId()
    {
        return signatureFileId;
    }

    public void setSignatureFileId(String signatureFileId)
    {
        this.signatureFileId = signatureFileId;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserImageId()
    {
        return userImageId;
    }

    public void setUserImageId(String userImageId)
    {
        this.userImageId = userImageId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Long getWebId()
    {
        return webId;
    }

    public void setWebId(Long webId)
    {
        this.webId = webId;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(userName);
        if (userId != null)
        {
            parcel.writeLong(userId);
        }
        parcel.writeString(userImageId);
        parcel.writeString(time);
        parcel.writeStringList(listEquipmentImageId);
        parcel.writeString(internalId);
        parcel.writeString(hourMeter);
        parcel.writeString(fuelLevel);
        parcel.writeString(historyType);
        parcel.writeString(estimatedReturn);
        parcel.writeString(note);
        if (contactId != null)
        {
            parcel.writeLong(contactId);
        }
        parcel.writeString(signatureFileId);
        parcel.writeString(pdfFileId);
        if (equipmentId != null)
        {
            parcel.writeLong(equipmentId);
        }
        parcel.writeString(outSiteServiceDocumentNumber);
        parcel.writeString(outSiteServiceDate);
        parcel.writeString(outSiteServicePrice);
        parcel.writeString(outSiteServiceProvider);
        if (equipmentId != null)
        {
            parcel.writeLong(webId);
        }
    }

    public EquipmentHistoryDTOLib(Parcel in)
    {
        userName = in.readString();
        userId = in.readLong();
        userImageId = in.readString();
        time = in.readString();
        if(listEquipmentImageId != null)
        {
            in.readStringList(listEquipmentImageId);
        }
        internalId = in.readString();
        hourMeter = in.readString();
        fuelLevel = in.readString();
        historyType = in.readString();
        estimatedReturn = in.readString();
        note = in.readString();
        contactId = in.readLong();
        signatureFileId = in.readString();
        equipmentId = in.readLong();
        outSiteServiceDocumentNumber = in.readString();
        outSiteServiceDate = in.readString();
        outSiteServicePrice = in.readString();
        outSiteServiceProvider = in.readString();
        webId = in.readLong();
    }

    public static final Parcelable.Creator<EquipmentHistoryDTOLib> CREATOR = new Parcelable.Creator<EquipmentHistoryDTOLib>()
    {
        public EquipmentHistoryDTOLib createFromParcel(Parcel in)
        {
            return new EquipmentHistoryDTOLib(in);
        }

        public EquipmentHistoryDTOLib[] newArray(int size)
        {
            return new EquipmentHistoryDTOLib[size];
        }
    };

}
