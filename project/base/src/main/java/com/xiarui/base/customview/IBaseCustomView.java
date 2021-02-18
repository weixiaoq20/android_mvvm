package com.xiarui.base.customview;

import androidx.databinding.ViewDataBinding;

public interface IBaseCustomView<AV extends BaseCustomViewModel> {
    void setData(AV data);
}
