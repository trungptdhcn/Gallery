package com.qsoft.components.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * User: trungpt
 * Date: 5/9/14
 * Time: 3:23 PM
 */
public class ResponseDTO
{
    @SerializedName("result")
    private ResultDTO resultDTO;

    public ResultDTO getResultDTO()
    {
        return resultDTO;
    }

    public void setResultDTO(ResultDTO resultDTO)
    {
        this.resultDTO = resultDTO;
    }
}
