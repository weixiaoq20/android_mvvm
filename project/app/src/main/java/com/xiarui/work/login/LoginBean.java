package com.xiarui.work.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xiangxue.network.beans.OnlineResult;

public class LoginBean extends OnlineResult {

    @SerializedName("obj")
    @Expose
    public ObjBean obj;

    /**
     * success : true
     * code : 1
     * msg : OK
     * obj : {"userId":"114827","account":"20069618","name":"刘全飞","userType":"1","userKey":"appuserkey8389904140676ba4879323a934638a98"}
     */

    public static class ObjBean {
        /**
         * userId : 114827
         * account : 20069618
         * name : 刘全飞
         * userType : 1
         * userKey : appuserkey8389904140676ba4879323a934638a98
         */
        @SerializedName("userId")
        @Expose
        public String userId;

        @SerializedName("account")
        @Expose
        public String account;

        @SerializedName("name")
        @Expose
        public String name;


        @SerializedName("userType")
        @Expose
        public String userType;



        @SerializedName("userKey")
        @Expose
        public String userKey;

        @Override
        public String toString() {
            return "ObjBean{" +
                    "userId='" + userId + '\'' +
                    ", account='" + account + '\'' +
                    ", name='" + name + '\'' +
                    ", userType='" + userType + '\'' +
                    ", userKey='" + userKey + '\'' +
                    '}';
        }
    }

}
