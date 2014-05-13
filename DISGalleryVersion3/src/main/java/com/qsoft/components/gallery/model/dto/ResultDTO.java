package com.qsoft.components.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * User: trungpt
 * Date: 5/9/14
 * Time: 3:23 PM
 */
public class ResultDTO
{
    @SerializedName("equipmentHistoryDTO")
    private EquipmentHistoryDTOLib equipmentHistoryDTOLib;
    @SerializedName("imageDTO")
    private ImageDTO imageDTO;

    public EquipmentHistoryDTOLib getEquipmentHistoryDTOLib()
    {
        return equipmentHistoryDTOLib;
    }

    public void setEquipmentHistoryDTOLib(EquipmentHistoryDTOLib equipmentHistoryDTOLib)
    {
        this.equipmentHistoryDTOLib = equipmentHistoryDTOLib;
    }

    public ImageDTO getImageDTO()
    {
        return imageDTO;
    }

    public void setImageDTO(ImageDTO imageDTO)
    {
        this.imageDTO = imageDTO;
    }
}
