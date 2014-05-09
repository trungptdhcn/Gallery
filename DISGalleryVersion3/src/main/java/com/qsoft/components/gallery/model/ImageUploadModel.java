package com.qsoft.components.gallery.model;

import android.graphics.Bitmap;
import android.os.Parcel;

/**
 * User: trungpt
 * Date: 4/28/14
 * Time: 11:38 AM
 */
public class ImageUploadModel extends ImageBaseModel
{
    private Boolean selection = false;
    private String thumbnailUri;
    private String realUri;

    public ImageUploadModel()
    {

    }

    public boolean isSelection()
    {
        return selection;
    }

    public void setSelection(boolean selection)
    {
        this.selection = selection;
    }

    public String getThumbnailUri()
    {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri)
    {
        this.thumbnailUri = thumbnailUri;
    }

    public String getRealUri()
    {
        return realUri;
    }

    public void setRealUri(String realUri)
    {
        this.realUri = realUri;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
    public ImageUploadModel(Parcel in)
    {
        super(in);
        selection = (in.readInt() == 0) ? false : true;
        thumbnailUri = in.readString();
        realUri = in.readString();
    }

    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
        out.writeInt(selection ? 1 : 0);
        out.writeString(thumbnailUri);
        out.writeString(realUri);
    }
}
