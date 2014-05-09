package com.qsoft.components.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: trungpt
 * Date: 5/9/14
 * Time: 3:24 PM
 */
public class EquipmentHistoryDTO
{

    @SerializedName("userName")
    String userName;
    @SerializedName("userId")
    Long userId;
    @SerializedName("userImageId")
    String userImageId;
    @SerializedName("time")
    String time;
    @SerializedName("locationDTO")
    LocationDTO locationDTO;
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

    public LocationDTO getLocationDTO()
    {
        return locationDTO;
    }

    public void setLocationDTO(LocationDTO locationDTO)
    {
        this.locationDTO = locationDTO;
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
}
