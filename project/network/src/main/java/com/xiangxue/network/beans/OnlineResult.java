package com.xiangxue.network.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineResult {
    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("msg")
    @Expose
    public String msg;

    @Override
    public String toString() {
        return "OnlineResult{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
