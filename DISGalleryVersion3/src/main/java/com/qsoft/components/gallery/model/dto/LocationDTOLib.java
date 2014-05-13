package com.qsoft.components.gallery.model.dto;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * User: trungpt
 * Date: 4/27/14
 * Time: 2:44 PM
 */
public class LocationDTOLib implements Parcelable
{
    @SerializedName("longitude")
    BigDecimal longitude;
    @SerializedName("latitude")
    BigDecimal latitude;
    @SerializedName("addressName")
    String addressName;
    @SerializedName("addressName2")
    String addressName2;
    @SerializedName("street")
    String street;
    @SerializedName("street2")
    String street2;
    @SerializedName("street3")
    String street3;
    @SerializedName("addressCity")
    String addressCity;
    @SerializedName("addressCounty")
    String addressCounty;
    @SerializedName("addressState")
    String addressState;
    @SerializedName("addressCountry")
    String addressCountry;
    @SerializedName("addressPostalCode")
    String addressPostalCode;
    @SerializedName("displayName")
    String displayName;

// --------------------- GETTER / SETTER METHODS ---------------------


    public LocationDTOLib()
    {
    }

    public String getAddressCity()
    {
        return addressCity;
    }

    public void setAddressCity(String addressCity)
    {
        this.addressCity = addressCity;
    }

    public String getAddressCountry()
    {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry)
    {
        this.addressCountry = addressCountry;
    }

    public String getAddressCounty()
    {
        return addressCounty;
    }

    public void setAddressCounty(String addressCounty)
    {
        this.addressCounty = addressCounty;
    }

    public String getAddressName()
    {
        return addressName;
    }

    public void setAddressName(String addressName)
    {
        this.addressName = addressName;
    }

    public String getAddressName2()
    {
        return addressName2;
    }

    public void setAddressName2(String addressName2)
    {
        this.addressName2 = addressName2;
    }

    public String getAddressPostalCode()
    {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode)
    {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressState()
    {
        return addressState;
    }

    public void setAddressState(String addressState)
    {
        this.addressState = addressState;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public BigDecimal getLatitude()
    {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude)
    {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude()
    {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude)
    {
        this.longitude = longitude;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getStreet2()
    {
        return street2;
    }

    public void setStreet2(String street2)
    {
        this.street2 = street2;
    }

    public String getStreet3()
    {
        return street3;
    }

    public void setStreet3(String street3)
    {
        this.street3 = street3;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        if (longitude != null && latitude != null)
        {
            parcel.writeString(longitude.toString());
            parcel.writeString(latitude.toString());
        }
        parcel.writeString(addressName);
        parcel.writeString(addressName2);
        parcel.writeString(street);
        parcel.writeString(street2);
        parcel.writeString(street3);
        parcel.writeString(addressCity);
        parcel.writeString(addressCounty);
        parcel.writeString(addressState);
        parcel.writeString(addressCountry);
        parcel.writeString(addressPostalCode);
        parcel.writeString(displayName);
    }

    public LocationDTOLib(Parcel in)
    {
        longitude = new BigDecimal(in.readString());
        latitude = new BigDecimal(in.readString());
        addressName = in.readString();
        addressName2 = in.readString();
        street = in.readString();
        street2 = in.readString();
        street3 = in.readString();
        addressCity = in.readString();
        addressCounty = in.readString();
        addressState = in.readString();
        addressCountry = in.readString();
        addressPostalCode = in.readString();
        displayName = in.readString();
    }

    public static final Parcelable.Creator<LocationDTOLib> CREATOR = new Parcelable.Creator<LocationDTOLib>()
    {
        public LocationDTOLib createFromParcel(Parcel in)
        {
            return new LocationDTOLib(in);
        }

        public LocationDTOLib[] newArray(int size)
        {
            return new LocationDTOLib[size];
        }
    };

}
