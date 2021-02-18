package com.xiarui.base.mvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.xiarui.base.R;
import com.xiarui.base.loadsir.CustomCallback;
import com.xiarui.base.loadsir.EmptyCallback;
import com.xiarui.base.loadsir.ErrorCallback;
import com.xiarui.base.loadsir.LoadingCallback;
import com.xiarui.base.loadsir.TimeoutCallback;
import com.xiarui.base.mvvm.viewmodel.BaseMvvmViewModel;
import com.xiarui.base.mvvm.viewmodel.ViewStatus;
import com.xiarui.base.utlis.AppManagerUtil;
import com.xiarui.base.utlis.LogUtil;
import com.xiarui.base.utlis.ToastUtil;

import java.util.List;

public abstract class BaseMvvmAppComAty<VIEW extends ViewDataBinding,
        VIEWMODEL extends BaseMvvmViewModel, DATA> extends AppCompatActivity implements Observer {

    protected VIEWMODEL viewmodel;
    protected VIEW viewDataBinding;
    private LoadService mLoadService;

    protected abstract String getAppcommAtyTag();

    public abstract void onNetworkResponded(List<DATA> dataList, boolean isDataUpdated);

    public abstract @LayoutRes
    int getLayoutId();

    public abstract VIEWMODEL getViewModel();

    protected BaseMvvmAppComAty aty;
    public int screenWidth = 0;
    public int screenHeight = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(getAppcommAtyTag(), "onCreate...");

        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        aty=this;
        AppManagerUtil.getInstance().addActivity(aty);
        //获取屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            onGetBundle(bundle);
        }
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .build();
        mLoadService = loadSir.register(getLoadSirView(), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                viewmodel.refresh();
            }
        });
        initData();

        viewmodel = getViewModel();
        getLifecycle().addObserver(viewmodel);
        viewmodel.datalist.observe(this, this);
        viewmodel.viewStatusLiveData.observe(this, this);
        onViewCreated();

    }

    protected abstract void onGetBundle(Bundle bundle);

    protected abstract void initData();

    protected abstract View getLoadSirView();


    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                viewmodel.refresh();
            }
        });
    }


    public View getView() {
        if (aty != null) {
            View cv = getWindow().getDecorView();
            return cv;
        } else {
            return null;
        }

    }

    public void finishResult(Intent intent) {
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void finishResult() {
        setResult(RESULT_OK);
        this.finish();
    }


    /**
     * 覆写startactivity方法，加入切换动画
     */
    public void startActivity(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        atyInAccessAnim();
    }

    public void atyInAccessAnim() {
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public void atyOutAccessAnim() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    /**
     * 覆写startactivity方法，加入切换动画
     */
    public void startActivity(String bundleName, Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtra(bundleName, bundle);
        }
        startActivity(intent);
        atyInAccessAnim();
    }

    /**
     * 带回调的跳转
     */
    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        atyInAccessAnim();
    }


    protected abstract void onViewCreated();

    public void onChangeType(Object ob){
        onChanged(ob);
    }

    @Override
    public void onChanged(Object o) {

        if (o instanceof ViewStatus && mLoadService != null) {
            switch ((ViewStatus) o) {
                case LOADING:
                    mLoadService.showCallback(LoadingCallback.class);
                    break;
                case EMPTY:
                    mLoadService.showCallback(EmptyCallback.class);
                    break;
                case SHOW_COENT_ERROR:
                    mLoadService.showSuccess();
                    onToast(viewmodel.errorMessage.getValue().toString());
                    break;
                case SHOW_CONTENT:
                    mLoadService.showSuccess();
                    break;
                case NO_MORE_DATA:
                    onToast(getString(R.string.no_more_data));
                    break;
                case REFRESH_ERROR:
                    if (((ObservableArrayList) viewmodel.datalist.getValue()).size() == 0) {
                        mLoadService.showCallback(ErrorCallback.class);
                    } else {
                        onToast(viewmodel.errorMessage.getValue().toString());
                    }
                    break;
                case LOAD_MORE_FAILED:
                    onToast(viewmodel.errorMessage.getValue().toString());
                    break;
            }
            onNetworkResponded(null, false);
        } else if (o instanceof List) {
            onNetworkResponded((List<DATA>) o, true);
        }
    }

    protected void onToast(String toast){
        if (aty!=null){
            aty.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(toast);
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName()+ " Fragment:" + this + ": " + "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName()+ " Fragment:" + this + ": " + "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onResume");
    }

    @Override
    public void onDestroy() {
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onDestroy");
        if (aty!=null){
            AppManagerUtil.getInstance().removeActivity(aty);
        }
        super.onDestroy();


    }


    protected void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }
}
