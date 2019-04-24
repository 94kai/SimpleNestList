package com.xk.simplenestlist;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

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

    protected LayoutHelper mLayoutHelper;
    protected DelegateAdapter mDelegateAdapter;

    public AbsSubAdapter(LayoutHelper layoutHelper) {
        this.mLayoutHelper = layoutHelper;
    }

    public void setDelegateAdapter(DelegateAdapter delegateAdapter) {
        this.mDelegateAdapter = delegateAdapter;
        mDelegateAdapter.setSpanCount(mLayoutHelper.getNeedSpan());
    }

    @NonNull
    @Override
    public final BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean shareTypeAll = true;
        Log.i("AbsSubAdapter", "onCreateViewHolder-->" + viewType + " " + getClass().getSimpleName());
        if (shareTypeAll) {
            return onCreateViewHolderForAll(parent, viewType);
        } else {
            return onCreateViewHolderForSubAdapter(parent, viewType);
        }
    }

    /**
     * 不同subAdapter，这个实现要一致。
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    public abstract BaseViewHolder onCreateViewHolderForAll(@NonNull ViewGroup viewGroup, int viewType);

    /**
     * 由于viewType在SubAdapter内部复用，所以不同的subAdapter可以重写他，可以具有不同的实现
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    private final BaseViewHolder onCreateViewHolderForSubAdapter(@NonNull ViewGroup viewGroup, int viewType) {
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
}
