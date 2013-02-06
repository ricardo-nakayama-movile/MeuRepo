package com.example.androidjsontest.interfaces;

import com.example.androidjsontest.bean.parcelable.ChannelContentsResponseParcel;

public interface AsyncTaskInterface {
    void processFinish(ChannelContentsResponseParcel output);
}