package com.xiarui.work.login;


import com.xiarui.base.mvvm.viewmodel.BaseMvvmViewModel;
import com.xiarui.work.login.api.NewsChannelsBean;

public class LoginViewModel extends BaseMvvmViewModel<LoginChannelModel, NewsChannelsBean.ChannelList> {

    @Override
    public LoginChannelModel createModel() {
        return new LoginChannelModel();
    }

}
