package com.xk.simplenestlist.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xk.simplenestlist.BaseViewHolder;
import com.xk.simplenestlist.DelegateAdapter;
import com.xk.simplenestlist.IShareAllTypeProvider;
import com.xk.simplenestlist.layouthelper.LayoutHelper;

import java.util.List;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public abstract class AbsSubAdapter<Data> extends RecyclerView.Adapter<BaseViewHolder> {
    /**
     * 子adapter第一个元素在整个recyclerview中的起始position
     */
    public int mStartPosition;

    private List<Data> mData;
    private int mAdapterId;

    protected LayoutHelper mLayoutHelper;
    protected DelegateAdapter mDelegateAdapter;

    public AbsSubAdapter(LayoutHelper layoutHelper, int adapterId) {
        this.mLayoutHelper = layoutHelper;
        this.mAdapterId = adapterId;
    }

    public void setDelegateAdapter(DelegateAdapter delegateAdapter) {
        this.mDelegateAdapter = delegateAdapter;
        mDelegateAdapter.setSpanCount(mLayoutHelper.getNeedSpan());
    }

    @NonNull
    @Override
    public final BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mDelegateAdapter.itemShareType == DelegateAdapter.ITEM_SHARE_TYPE_ALL) {
            IShareAllTypeProvider shareAllTypeProvider = mDelegateAdapter.getShareAllTypeProvider();
            return shareAllTypeProvider.onCreateViewHolder(parent, viewType);
        } else {
            return onCreateViewHolderForSubAdapter(parent, viewType);
        }
    }

    /**
     * ITEM_SHARE_TYPE_SUBADAPTER模式下重写该方法创建viewholder
     */
    protected BaseViewHolder onCreateViewHolderForSubAdapter(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public abstract void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Data> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public int getSpanByPosition(int position, int maxSpanCount) {
        return mLayoutHelper.getSpanForPosition(position, maxSpanCount);
    }

    @Override
    public final int getItemViewType(int position) {
        if (mDelegateAdapter.itemShareType == DelegateAdapter.ITEM_SHARE_TYPE_ALL) {
            IShareAllTypeProvider itemTypeProvider = mDelegateAdapter.getShareAllTypeProvider();
            return itemTypeProvider.getItemViewType(position, mAdapterId);
        } else {
            return getItemViewTypeForSubAdapter(position);
        }
    }

    /**
     * ITEM_SHARE_TYPE_SUBADAPTER模式下重写该方法，为每个item指定viewtype
     */
    protected int getItemViewTypeForSubAdapter(int position) {
        return 0;
    }
}
