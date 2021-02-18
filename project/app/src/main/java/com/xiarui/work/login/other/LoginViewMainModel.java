package com.xiarui.work.login.other;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.xiarui.base.mvvm.viewmodel.BaseMvvmViewModel;
import com.xiarui.work.login.LoginBean;


public class LoginViewMainModel extends BaseMvvmViewModel<LoginMainModel, LoginBean.ObjBean> {

    private String username;
    private String pwd;
    private LoginMainModel loginModel = null;

    public LoginViewMainModel(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;

    }

    public void setUsername(String username) {
        this.username = username;
        if (loginModel!=null){
            loginModel.username=this.username;
        }
    }


    public void setPwd(String pwd) {
        this.pwd = pwd;
        if (loginModel!=null){
            loginModel.password=this.pwd;
        }
    }




    @Override
    public LoginMainModel createModel() {
        if (loginModel == null) {
            loginModel = new LoginMainModel(username, pwd);
        }
        return loginModel;
    }

}
