package com.xiangxue.news.homefragment.newslist;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.xiarui.base.customview.BaseCustomViewModel;
import com.xiarui.base.recycleview.BaseViewHolder;
import com.xiarui.common.views.picturetitleview.PictureTitleView;
import com.xiarui.common.views.picturetitleview.PictureTitleViewModel;
import com.xiarui.common.views.titleview.TitleView;

import java.util.List;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_TYPE_PICTURE_TITLE = 1;
    private final int VIEW_TYPE_TITLE = 2;
    private List<BaseCustomViewModel> mItems;
    private Context mContext;

    NewsListRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    void setData(List<BaseCustomViewModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems != null && mItems.get(position) instanceof PictureTitleViewModel) {
            return VIEW_TYPE_PICTURE_TITLE;
        }
        return VIEW_TYPE_TITLE;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PICTURE_TITLE) {
            return new BaseViewHolder(new PictureTitleView(parent.getContext()));
        } else if (viewType == VIEW_TYPE_TITLE) {
            return new BaseViewHolder(new TitleView(parent.getContext()));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }
}
