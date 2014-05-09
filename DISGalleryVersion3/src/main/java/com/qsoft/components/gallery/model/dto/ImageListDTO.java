package com.qsoft.components.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: trungpt
 * Date: 5/6/14
 * Time: 6:05 PM
 */
public class ImageListDTO
{
    @SerializedName("imageDTOList")
    private List<ImageDTO> imageDTOs;

    public ImageListDTO(List<ImageDTO> imageDTOs)
    {
        this.imageDTOs = imageDTOs;
    }

    public List<ImageDTO> getImageDTOs()
    {
        return imageDTOs;
    }

    public void setImageDTOs(List<ImageDTO> imageDTOs)
    {
        this.imageDTOs = imageDTOs;
    }
}
