package com.xiarui.work.login.api;

import com.xiarui.base.preference.BaseConstant;
import com.xiarui.work.login.LoginBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public interface NewsApiInterface {
    @GET("release/news")
    Observable<NewsListBean> getNewsList(@Query("channelId") String channelId,
                                         @Query("channelName") String channelName,
                                         @Query("page") String page);

    @GET("release/channel")
    Observable<NewsChannelsBean> getNewsChannels();


    //  mHashMap.put("username", phone);
    //        mHashMap.put("password", MD5Util.md5Decode32(secyrity));

    @FormUrlEncoded
    @POST(BaseConstant.APP_ADDR+"login/uservalid")
    Observable<LoginBean> getLoginUserVaild(@Field("username") String username,@Field("password") String password);
}
