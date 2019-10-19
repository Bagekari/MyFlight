package com.example.myflight.Activities;

public class TicketListItem {
    private int mImageResource;
    private String mDepListPrice, mDepListDep, mDepListRet, mDepListAirline;
    private String mRetListPrice, mRetListDep, mRetListRet, mRetListAirline;

    public TicketListItem(int imageResource, String depPrice, String depSource, String depDest, String depAirline,
                          String retPrice, String retSource, String retDest, String retAirline) {
        mImageResource = imageResource;
        mDepListAirline = depAirline;
        mDepListDep = depSource;
        mDepListRet = depDest;
        mDepListPrice = depPrice;
        mRetListAirline = retAirline;
        mRetListDep = retSource;
        mRetListRet = retDest;
        mRetListPrice = retPrice;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getDepListPrice() {
        return mDepListPrice;
    }

    public String getDepListDep() {
        return mDepListDep;
    }

    public String getDepListRet() {
        return mDepListRet;
    }

    public String getDepListAirline() {
        return mDepListAirline;
    }

    public String getRetListPrice() {
        return mRetListPrice;
    }

    public String getRetListDep() {
        return mRetListDep;
    }

    public String getRetListRet() {
        return mRetListRet;
    }

    public String getRetListAirline() {
        return mRetListAirline;
    }
}
