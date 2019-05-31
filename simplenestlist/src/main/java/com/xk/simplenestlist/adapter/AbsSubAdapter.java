package com.xk.simplenestlist.adapter;


import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xk.simplenestlist.BaseViewHolder;
import com.xk.simplenestlist.Cantor;
import com.xk.simplenestlist.DelegateAdapter;
import com.xk.simplenestlist.layouthelper.LayoutHelper;

import java.util.HashMap;
import java.util.List;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public abstract class AbsSubAdapter<Data> extends RecyclerView.Adapter<BaseViewHolder> {
    private RecyclerView.RecycledViewPool pool;
    /**
     * 子adapter第一个元素在整个recyclerview中的起始position
     */
    public int mStartPosition;

    protected List<Data> mData;
    //位于adapter的集合第几位
    private int index = -1;

    protected LayoutHelper mLayoutHelper;
    protected DelegateAdapter mDelegateAdapter;

    @Override
    public abstract BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i);

    @Override
    public abstract void onBindViewHolder(BaseViewHolder baseViewHolder, int i);

    public AbsSubAdapter(LayoutHelper layoutHelper) {
        this.mLayoutHelper = layoutHelper;
    }

    public AbsSubAdapter(LayoutHelper layoutHelper, RecyclerView.RecycledViewPool pool) {
        this.mLayoutHelper = layoutHelper;
        this.pool = pool;
    }

    public void setDelegateAdapter(DelegateAdapter delegateAdapter) {
        this.mDelegateAdapter = delegateAdapter;
        mDelegateAdapter.setSpanCount(mLayoutHelper.getNeedSpan());
    }


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

    HashMap<Integer, Integer> recycledViewTypeCache = new HashMap<>();

    /**
     * 设置viewtype的缓存
     *
     * @param viewType
     * @param max
     */
    public void setMaxRecycledViews(int viewType, int max) {
        if (index != -1) {
            int index = getIndex();
            //在总的adapter中的真实的viewType
            long realViewType = Cantor.getCantor(viewType, index);
            if (pool != null) {
                pool.setMaxRecycledViews((int) realViewType, max);
            }
        } else {
            //缓存起来，在setIndex的时候使用
            recycledViewTypeCache.put(viewType, max);
        }

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        for (Integer viewType : recycledViewTypeCache.keySet()) {
            //在总的adapter中的真实的viewType
            long realViewType = Cantor.getCantor(viewType, index);
            if (pool != null) {
                pool.setMaxRecycledViews((int) realViewType, recycledViewTypeCache.get(viewType));
            }
        }
        recycledViewTypeCache.clear();
    }

    public List<Data> getData() {
        return mData;
    }

    public void simpleNotifyItemChanged(int position) {
        mDelegateAdapter.notifyItemChanged(mStartPosition + position);
    }

    public void simpleNotifyItemRemoved(int position) {
        mDelegateAdapter.updateIndex();
        mDelegateAdapter.notifyItemRemoved(mStartPosition + position);
    }

    public void simpleNotifyItemMoved(int fromPosition, int toPosition) {
        mDelegateAdapter.notifyItemMoved(mStartPosition + fromPosition, mStartPosition + toPosition);
    }

    public void simpleNotifyItemInserted(int position) {
        mDelegateAdapter.updateIndex();
        mDelegateAdapter.notifyItemInserted(mStartPosition + position);
    }

    public void simpleNotifyItemRangeChanged(int positionStart, int count) {
        mDelegateAdapter.notifyItemRangeChanged(mStartPosition + positionStart, count);
    }

    public void simpleNotifyItemRangeRemoved(int positionStart, int count) {
        mDelegateAdapter.updateIndex();
        mDelegateAdapter.notifyItemRangeRemoved(mStartPosition + positionStart, count);
    }

    public void simpleNotifyItemRangeInserted(int positionStart, int count) {
        mDelegateAdapter.updateIndex();
        mDelegateAdapter.notifyItemRangeInserted(mStartPosition + positionStart, count);
    }

    public void simpleNotifyDataSetChanged(List<Data> oldData, List<Data> newData, DefaultDiffCallback<Data> callback) {
        callback.setDatas(oldData, newData);
        //虽然是刷subAdapter的所有，但在整个recyclerview中还是一小部分，所以只能根据数据源的变化来调用delegateAdapter的各种notify方法
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback, true);
        diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                simpleNotifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                simpleNotifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                simpleNotifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                simpleNotifyItemRangeChanged(position, count);
            }
        });

    }

    public void simpleNotifyDataSetChanged(List<Data> oldData, List<Data> newData) {
        DefaultDiffCallback<Data> callback = new DefaultDiffCallback<>();
        simpleNotifyDataSetChanged(oldData, newData, callback);
    }


}
