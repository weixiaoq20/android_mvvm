package com.xiangxue.news.homefragment.newslist;

import com.xiangxue.network.TecentNetworkApi;
import com.xiangxue.network.observer.BaseObserver;
import com.xiangxue.news.homefragment.api.NewsApiInterface;
import com.xiangxue.news.homefragment.api.NewsListBean;
import com.xiarui.base.BuildConfig;
import com.xiarui.base.customview.BaseCustomViewModel;
import com.xiarui.base.mvvm.model.BaseMvvmModel;
import com.xiarui.common.views.picturetitleview.PictureTitleViewModel;
import com.xiarui.common.views.titleview.TitleViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsListModel extends BaseMvvmModel<NewsListBean,List<BaseCustomViewModel>> {
    private String channelId;
    private String channelName;

    int mPage=1;
    public NewsListModel(String channelId,String chanelName){
        super(true,channelId+chanelName+"_preference_key",null,1);
        this.channelId=channelId;
        this.channelName=chanelName;

    }


    @Override
    public void load() {
        TecentNetworkApi.getService(NewsApiInterface.class,BuildConfig.URL_NEWKEY)
                .getNewsList(channelId,
                        channelName, String.valueOf(mPage))
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<>(this,this)));
    }

    @Override
    public void onSuccess(NewsListBean newsChannelsBean, boolean isFromCache) {
        List<BaseCustomViewModel> viewModels=new ArrayList<>();
        for(NewsListBean.Contentlist contentlist:newsChannelsBean.showapiResBody.pagebean.contentlist){
            if(contentlist.imageurls != null && contentlist.imageurls.size() > 0){
                PictureTitleViewModel pictureTitleViewModel = new PictureTitleViewModel();
                pictureTitleViewModel.pictureUrl = contentlist.imageurls.get(0).url;
                pictureTitleViewModel.jumpUri = contentlist.link;
                pictureTitleViewModel.title = contentlist.title;
                viewModels.add(pictureTitleViewModel);
            } else {
                TitleViewModel titleViewModel = new TitleViewModel();
                titleViewModel.jumpUri = contentlist.link;
                titleViewModel.title = contentlist.title;
                viewModels.add(titleViewModel);
            }
        }
        notifyResultToListener(newsChannelsBean,viewModels,false);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }
}
