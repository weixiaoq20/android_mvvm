package com.xiarui.common.views.titleview;

import android.content.Context;
import android.view.View;

import com.xiangxue.webview.WebviewActivity;
import com.xiarui.base.customview.BaseCustomView;
import com.xiarui.common.R;
import com.xiarui.common.databinding.TitleViewBinding;

public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void onRootClicked(View view) {
        WebviewActivity.startCommonWeb(getContext(), "News", data.jumpUri);
    }

    @Override
    protected void setDataToView(TitleViewModel titleViewModel) {
        mBinding.setViewModel(titleViewModel);
    }
}
