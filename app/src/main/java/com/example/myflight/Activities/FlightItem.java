package com.example.myflight.Activities;

import android.os.Parcel;
import android.os.Parcelable;

public class FlightItem implements Parcelable {
    private String mTextPrice, mTextDep, mTextRet, mTextAirline;

    public FlightItem(String textPrice, String textDep, String textRet, String textAirline) {
        mTextPrice = textPrice;
        mTextDep = textDep;
        mTextRet = textRet;
        mTextAirline = textAirline;
    }

    protected FlightItem(Parcel in) {
        mTextPrice = in.readString();
        mTextDep = in.readString();
        mTextRet = in.readString();
        mTextAirline = in.readString();
    }

    public static final Creator<FlightItem> CREATOR = new Creator<FlightItem>() {
        @Override
        public FlightItem createFromParcel(Parcel in) {
            return new FlightItem(in);
        }

        @Override
        public FlightItem[] newArray(int size) {
            return new FlightItem[size];
        }
    };

    public String getTextPrice() {
        return mTextPrice;
    }

    public String getTextDep() {
        return mTextDep;
    }

    public String getTextRet() {
        return mTextRet;
    }

    public String getTextAirline() {
        return mTextAirline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTextPrice);
        dest.writeString(mTextDep);
        dest.writeString(mTextRet);
        dest.writeString(mTextAirline);
    }
}
