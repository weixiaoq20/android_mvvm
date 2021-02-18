package com.xiarui.work.application;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.StrictMode;


import androidx.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.xiangxue.network.base.NetworkApi;
import com.xiarui.base.BaseApplication;
import com.xiarui.base.preference.PreferencesUtil;
import com.xiarui.base.utlis.AppManagerUtil;
import com.xiarui.base.utlis.ToastUtil;
import com.xiarui.work.MainActivity;

import java.util.List;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class ArchDemoApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initAppLication();
        initAppOperation();
        initBeta();
    }

    private void initAppOperation() {
        NetworkApi.init(new NetworkRequestInfo(this));
        PreferencesUtil.init(this);
        //专为扩展库所用
        AppManagerUtil.getInstance().setApplication(this);
    }

    @Override
    protected void initAppLication() {
        super.initAppLication();
        if (!shouldInit()) {
            return;
        }
        //解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }



    }
    /**
     * 判断主进程是否初始化过
     *
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    //腾讯 bugly
    public void initBeta() {
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.canShowApkInfo = true;
        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                if (strategy != null) {
                    //showDialog(strategy);
                }
            }
        };
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeSuccess(boolean isManual) {
            }

            @Override
            public void onUpgradeFailed(boolean isManual) {
            }

            @Override
            public void onUpgrading(boolean isManual) {
            }

            @Override
            public void onDownloadCompleted(boolean b) {

            }

            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                if (isManual) {
                    ToastUtil.show( "已经是最新版本了!");
                }
            }
        };
        //设置成为开发设备
        Bugly.init(getApplicationContext(), "677df5dda4", isApkDebugable(this));
    }

    /**
     * 判断是开发debug模式，还是发布release模式
     *
     * @param context 上下文
     * @return debug模式 返回true 否则返回false
     */
    public static boolean IS_DEBUG;

    public static boolean isApkDebugable(Context context) {
        IS_DEBUG = false;
        try {
            ApplicationInfo info = context.getApplicationInfo();
            IS_DEBUG = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return IS_DEBUG;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IS_DEBUG;
    }
}
