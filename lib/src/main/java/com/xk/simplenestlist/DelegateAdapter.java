package com.xk.simplenestlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * 1.subadapter之间的item的type不共享
 * 2.只要孩子的itemtype相同，就共享
 *
 * @author xuekai1
 * @date 2019/4/24
 */
public class DelegateAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    /**
     * 只要孩子的itemtype相同，就可复用。
     * 注意：这种情况要保证，不同的subAdapter，如果viewType相同，那么onCreateViewHolder要一致
     * 共享的时候需要注意，onbindviewholder最好所有的adapter都一致，并且type定义不要有重复，即type相同，布局一定一致。
     */
    public static final int ITEM_SHARE_TYPE_ALL = 0;
    /**
     * 仅在subAdapter内部的item之间复用
     */
    public static final int ITEM_SHARE_TYPE_SUBADAPTER = 1;
    private final SimpleNestLayoutManager mLayoutManager;
    /**
     * item复用方式
     */
    private int itemShareType = ITEM_SHARE_TYPE_ALL;

    /**
     * subadapter的集合
     */
    private LinkedList<AbsSubAdapter> mAdapters = new LinkedList<>();

    /**
     * viewType和adapter的键值对集合，仅用于ITEM_SHARE_TYPE_ALL模式，因为该模式下，对于相同的viewType，
     * 每个subAdapter的onCreateViewHolder的行为一致，所以可以通过viewType来决定如果CreateViewHolder
     */
    private SparseArray<AbsSubAdapter> mItemTypeAry = new SparseArray<>();

    private int total;

    public DelegateAdapter(SimpleNestLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        layoutManager.setSpanSizeLookup(getSpanSizeLookUp());
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (itemShareType == ITEM_SHARE_TYPE_ALL) {
            AbsSubAdapter absSubAdapter = mItemTypeAry.get(viewType);
            if (absSubAdapter != null) {
                return absSubAdapter.onCreateViewHolder(parent, viewType);
            }
        } else {
            throw new SimpleNestListException("ITEM_SHARE_TYPE_SUBADAPTER暂未实现");
        }
        throw new SimpleNestListException();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        AbsSubAdapter absSubAdapterByPosition = findSubAdapterByPosition(position);
        if (absSubAdapterByPosition == null) {
            throw new SimpleNestListException();
        }
        absSubAdapterByPosition.onBindViewHolder(holder, position - absSubAdapterByPosition.mStartPosition);
    }

    @Override
    public int getItemCount() {
        return total;
    }

    @Override
    public int getItemViewType(int position) {
        AbsSubAdapter absSubAdapter = findSubAdapterByPosition(position);
        if (absSubAdapter == null) {
            throw new SimpleNestListException();
        }
        if (itemShareType == ITEM_SHARE_TYPE_ALL) {
            int subItemType = absSubAdapter.getItemViewType(position - absSubAdapter.mStartPosition);
            mItemTypeAry.put(subItemType, absSubAdapter);
            //所有subAdapter之间item共享
            return subItemType;
        } else {
            throw new SimpleNestListException("ITEM_SHARE_TYPE_SUBADAPTER暂未实现");
        }
    }

    /**
     * 通过position寻找他所属的SubAdapter
     *
     * @return AbsSubAdapter
     */
    private AbsSubAdapter findSubAdapterByPosition(int position) {
        final int count = mAdapters.size();
        if (count == 0) {
            return null;
        }
        int s = 0, e = count - 1, m;
        AbsSubAdapter result = null;
        while (s <= e) {
            m = (s + e) / 2;
            result = mAdapters.get(m);
            int endPosition = result.mStartPosition + result.getItemCount() - 1;
            if (result.mStartPosition > position) {
                e = m - 1;
            } else if (endPosition < position) {
                s = m + 1;
            } else if (result.mStartPosition <= position && endPosition >= position) {
                break;
            }
            result = null;
        }

        return result;
    }


    public void setAdapters(LinkedList<AbsSubAdapter> mAdapters) {
        int startPosition = 0;
        for (AbsSubAdapter mAdapter : mAdapters) {
            mAdapter.setDelegateAdapter(this);
            mAdapter.mStartPosition = startPosition;
            startPosition += mAdapter.getItemCount();
        }
        this.mAdapters = mAdapters;
        clear();
        update();
        notifyDataSetChanged();
    }

    private void clear() {
        total = 0;
    }

    // TODO: by xk 2019/4/24 上午10:36 任意一个adapter的itemCount改变，都需要更新total
    private void update() {
        total = 0;
        for (AbsSubAdapter adapter : mAdapters) {
            total += adapter.getItemCount();
        }
        // TODO: by xk 2019/4/24 上午10:39 更新position和subadapter的索引
    }


    public GridLayoutManager.SpanSizeLookup getSpanSizeLookUp() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                AbsSubAdapter subAdapter = findSubAdapterByPosition(position);
                if (subAdapter == null) {
                    throw new SimpleNestListException();
                }
                return subAdapter.getSpanByPosition(position - subAdapter.mStartPosition, mLayoutManager.getSpanCount());
            }
        };
    }

    /**
     * 每增加一个layouthelper，就要刷新一下spancount
     *
     * @param supportSpanCount 需要支持的列数
     */
    public void setSpanCount(int supportSpanCount) {
        if (mLayoutManager == null) {
            throw new SimpleNestListException();
        }
        int spanCount = mLayoutManager.getSpanCount();
        mLayoutManager.setSpanCount(leastCommonMultiple(spanCount, supportSpanCount));
    }

    /**
     * 求最小公倍数
     */
    public int leastCommonMultiple(int a, int b) {
        int c = a * b;
        if (a < b) {
            int r;
            r = a;
            a = b;
            b = r;
        }
        while (true) {
            int r = a % b;
            if (r == 0) {
                return c / b;
            } else {
                a = b;
                b = r;
            }
        }
    }

}

