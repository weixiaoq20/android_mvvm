package com.xiangxue.news.homefragment.newslist;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiangxue.news.R;
import com.xiangxue.news.databinding.FragmentNewsBinding;
import com.xiarui.base.customview.BaseCustomViewModel;
import com.xiarui.base.mvvm.view.BaseMvvmFragment;
import java.util.List;
/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NewsListFragment extends BaseMvvmFragment<FragmentNewsBinding,NewsListViewModel,BaseCustomViewModel> {
    private NewsListRecyclerViewAdapter mAdapter;

    protected final static String BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name";


    public static NewsListFragment newInstance(String channelId, String channelName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId);
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getFragmentTag() {
        return getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME);
    }

    @Override
    public void onNetworkResponded(List<BaseCustomViewModel> modelList, boolean isDataUpdated) {
        viewDataBinding.refreshLayout.finishRefresh();
        viewDataBinding.refreshLayout.finishLoadMore();
        if(isDataUpdated) {
            mAdapter.setData(modelList);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public NewsListViewModel getViewModel() {
        return new NewsListViewModel(getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID),getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME));
    }

    @Override
    protected View getLoadSirView() {
        return viewDataBinding.refreshLayout;
    }


    protected void onViewCreated() {
        mAdapter = new NewsListRecyclerViewAdapter(getContext());
        viewDataBinding.listview.setHasFixedSize(true);
        viewDataBinding.listview.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.listview.setAdapter(mAdapter);
        viewDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewmodel.refresh();
            }
        });
        viewDataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewmodel.loadNextPage();
            }
        });

        showLoading();
    }



}
