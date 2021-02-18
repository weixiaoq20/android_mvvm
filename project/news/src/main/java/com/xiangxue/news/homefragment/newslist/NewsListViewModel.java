package com.xiangxue.news.homefragment.newslist;


import com.xiarui.base.customview.BaseCustomViewModel;
import com.xiarui.base.mvvm.viewmodel.BaseMvvmViewModel;

public class NewsListViewModel extends BaseMvvmViewModel<NewsListModel, BaseCustomViewModel> {
    private String mChannelId;
    private String mChannelName;


    public NewsListViewModel(String ChannelId,String ChannelName){
        this.mChannelId=ChannelId;
        this.mChannelName=ChannelName;
    }

    @Override
    public NewsListModel createModel() {
        return new NewsListModel(mChannelId,mChannelName);
    }
}
