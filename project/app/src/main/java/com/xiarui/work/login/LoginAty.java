package com.xiarui.work.login;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.xiarui.base.mvvm.view.BaseMvvmAppComAty;
import com.xiarui.base.utlis.LogUtil;
import com.xiarui.work.R;
import com.xiarui.work.databinding.ActivityLoginBinding;
import com.xiarui.work.login.other.LoginViewMainModel;

import java.util.List;


public class LoginAty extends BaseMvvmAppComAty<ActivityLoginBinding, LoginViewMainModel, LoginBean> {
    private EventListener eventListener = null;
    LoginViewMainModel loginMainModel=null;

    @Override
    protected String getAppcommAtyTag() {
        return "loginAty";
    }

    @Override
    public void onNetworkResponded(List<LoginBean> data, boolean isDataUpdated) {
        if (data != null) {
            for (LoginBean o : data) {
                String dayin=o.msg+o.success;
                LogUtil.e(getAppcommAtyTag(), dayin);
            }
        }

    }
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        Log.e("xiarui","============onRetainCustomNonConfigurationInstance===========");
        return super.onRetainCustomNonConfigurationInstance();
    }


    @Override
    public Object getLastCustomNonConfigurationInstance() {
        Log.e("xiarui","============getLastCustomNonConfigurationInstance===========");
        return super.getLastCustomNonConfigurationInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewMainModel getViewModel() {
        if (loginMainModel==null){
            loginMainModel=new LoginViewMainModel("20069618","11yy32");;
        }
        return loginMainModel;
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected View getLoadSirView() {
        return viewDataBinding.activityLogin;
    }

    @Override
    protected void onViewCreated() {
        if (eventListener == null) {
            eventListener = new EventListener();
            eventListener.name = "20069618";
            eventListener.pwd = "11yy33";
        }
        viewDataBinding.setEventlistener(eventListener);
        showLoading();
    }


    public class EventListener implements LifecycleOwner, Observer<List<LoginBean>> {
        public String name;
        public String pwd;


        public EventListener() {
            this.name = viewDataBinding.abEditUser.getText().toString();
            this.pwd = viewDataBinding.abEditPwd.getText().toString();
        }

        public void btnClick(View view) {
            loginMainModel.setUsername(name);
            loginMainModel.setPwd(pwd);
            viewmodel.refresh();
        }


        public TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s.toString())) {
                    name = s.toString();
                } else {
                    name = "";
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        public TextWatcher pwdtextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s.toString())) {
                    pwd = s.toString();
                } else {
                    pwd = "";
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return LoginAty.this.getLifecycle();
        }

        @Override
        public void onChanged(List<LoginBean> objBeans) {
            onNetworkResponded(objBeans,false);
        }
    }


}
