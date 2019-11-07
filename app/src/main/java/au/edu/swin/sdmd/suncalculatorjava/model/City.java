package au.edu.swin.sdmd.suncalculatorjava.model;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {

    private String name;
    private String latitude;
    private String longitude;
    private String timezone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public City() {}

    protected City(Parcel in) {
        name = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        timezone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(timezone);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}