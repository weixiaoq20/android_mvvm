package com.xiarui.common.views.picturetitleview;

import android.content.Context;
import android.view.View;
import com.xiangxue.webview.WebviewActivity;
import com.xiarui.base.customview.BaseCustomView;
import com.xiarui.common.R;
import com.xiarui.common.databinding.PictureTitleViewBinding;

public class PictureTitleView extends BaseCustomView<PictureTitleViewBinding,PictureTitleViewModel>{

    public PictureTitleView(Context context) {
        super(context);
    }

    @Override
    public void onRootClicked(View view) {
        WebviewActivity.startCommonWeb(getContext(), "News", data.jumpUri);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.picture_title_view;
    }

    @Override
    protected void setDataToView(PictureTitleViewModel pictureTitleViewModel) {
        mBinding.setViewModel(pictureTitleViewModel);

    }
}


