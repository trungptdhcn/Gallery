package com.qsoft.components.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * User: trungpt
 * Date: 5/6/14
 * Time: 11:51 AM
 */
public class ImageDTO
{
    @SerializedName("index")
    private Long index;
    @SerializedName("fileId")
    private String fileId;
    @SerializedName("isShown")
    private boolean isShown;

    public Long getIndex()
    {
        return index;
    }

    public void setIndex(Long index)
    {
        this.index = index;
    }

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public boolean isShown()
    {
        return isShown;
    }

    public void setShown(boolean shown)
    {
        isShown = shown;
    }
}
