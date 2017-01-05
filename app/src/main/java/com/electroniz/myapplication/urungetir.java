package com.electroniz.myapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ABDULLAH on 5.01.2017.
 */
public class urungetir {
    @SerializedName("urun_id")
    public int urun_id;
    @SerializedName("urun_ad")
    public String urun_ad;
    @SerializedName("urun_fiyat")
    public String urun_fiyat;
    @SerializedName("urunresim_url")
    public String urunresim_url;

}
