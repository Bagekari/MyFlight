package com.example.myflight.Activities;

public class FlightItem {
    private String mTextPrice, mTextDep, mTextRet, mTextAirline;

    public FlightItem(String textPrice, String textDep, String textRet, String textAirline) {
        mTextPrice = textPrice;
        mTextDep = textDep;
        mTextRet = textRet;
        mTextAirline = textAirline;
    }

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
}
