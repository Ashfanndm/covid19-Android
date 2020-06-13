package com.ashfanndm.covid19srilankanews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class People {

    @SerializedName("data")
    private Data mData;

    public Data getmData() {
        return mData;
    }
}
