package com.xiangxue.news.homefragment.headlinenews;

import com.xiangxue.news.homefragment.api.NewsChannelsBean;
import com.xiarui.base.mvvm.viewmodel.BaseMvvmViewModel;


public class HeadlineNewsViewModel extends BaseMvvmViewModel<NewsChannelModel,NewsChannelsBean.ChannelList> {

    @Override
    public NewsChannelModel createModel() {
        return new NewsChannelModel();
    }

}
