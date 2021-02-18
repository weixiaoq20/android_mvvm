package com.xiarui.work.login.other;

import com.xiangxue.network.TecentNetworkApi;
import com.xiangxue.network.observer.BaseObserver;
import com.xiarui.base.mvvm.model.BaseMvvmModel;
import com.xiarui.base.utlis.LogUtil;
import com.xiarui.common.utlis.MD5Util;
import com.xiarui.work.BuildConfig;
import com.xiarui.work.login.LoginBean;
import com.xiarui.work.login.api.NewsApiInterface;
import java.util.ArrayList;
import java.util.List;

public class LoginMainModel extends BaseMvvmModel<LoginBean, List<LoginBean>> {
    String username, password;

    public LoginMainModel(String username, String password) {
        super(false, null, null);
        this.username = username;
        this.password = password;
    }

    @Override
    public void load() {

        String ddSTr = MD5Util.md5Decode32(password);
        LogUtil.e("password", "====pwd===" + ddSTr);
        TecentNetworkApi.getService(NewsApiInterface.class, BuildConfig.URL_LOGIN)
                .getLoginUserVaild(username, ddSTr)
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<LoginBean>(this, this)));
    }

    @Override
    public void onSuccess(LoginBean login, boolean isFromCache) {
        List<LoginBean> loginBeans=new ArrayList<>();
        if (login!=null){
            loginBeans.add(login);
        }
        notifyResultToListener(login, loginBeans, isFromCache);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }
}
