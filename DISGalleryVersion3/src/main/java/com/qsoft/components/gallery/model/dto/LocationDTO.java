package com.qsoft.components.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * User: trungpt
 * Date: 4/27/14
 * Time: 2:44 PM
 */
public class LocationDTO
{
    @SerializedName("longitude")
    private BigDecimal longitude;
    @SerializedName("latitude")
    private BigDecimal latitude;
    @SerializedName("street")
    private String street;

    public BigDecimal getLongitude()
    {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude)
    {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude()
    {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude)
    {
        this.latitude = latitude;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }
}
