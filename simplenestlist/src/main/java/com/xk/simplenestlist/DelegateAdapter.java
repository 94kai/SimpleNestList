package com.xk.simplenestlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.xk.simplenestlist.adapter.AbsSubAdapter;

import java.util.LinkedList;

/**
 * subadapter之间的item的type不共享
 *
 * @author xuekai1
 * @date 2019/4/24
 */
public class DelegateAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final SimpleNestLayoutManager mLayoutManager;

    /**
     * subadapter的集合
     */
    private LinkedList<AbsSubAdapter> mAdapters = new LinkedList<>();

    /**
     * itemCount
     */
    private int total;

    /**
     * 构造
     * 共享type
     *
     * @param layoutManager
     */
    public DelegateAdapter(SimpleNestLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        layoutManager.setSpanSizeLookup(getSpanSizeLookUp());
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AbsSubAdapter absSubAdapter = resolveViewType(viewType);
        if (absSubAdapter != null) {
            Log.i("DelegateAdapter", "onCreateViewHolder-->" + viewType + " adapterIndex:" + absSubAdapter.getIndex());
            return absSubAdapter.onCreateViewHolder(parent, viewType);
        }
        throw new SimpleNestListException();
    }

    /**
     * 根据viewType解析出所属的SubAdapter
     */
    private AbsSubAdapter resolveViewType(int viewType) {
        long[] result = new long[2];
        Cantor.reverseCantor(viewType, result);
        long index = result[1];
        return mAdapters.get((int) index);
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
        return makeViewType(absSubAdapter, position);
        //通过absSubAdapter和position构造出一个viewType
    }

    private int makeViewType(AbsSubAdapter absSubAdapter, int position) {
        int subItemType = absSubAdapter.getItemViewType(position - absSubAdapter.mStartPosition);
        long cantor = Cantor.getCantor(subItemType, absSubAdapter.getIndex());
        return (int) cantor;
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

    /**
     * 设置adapters
     *
     * @param mAdapters
     */
    public void setAdapters(LinkedList<AbsSubAdapter> mAdapters) {
        this.mAdapters = mAdapters;
        simpleNotifyDataSetChanged();
    }

    /**
     * 清空状态
     */
    private void clear() {
        total = 0;
        // TODO: by xk 2019/4/24 上午10:36 任意一个adapter的itemCount改变，都需要更新total
        // TODO: by xk 2019-05-31 14:59 如何可以类似于调用RecyclerView.setAdapter 方法，全部重置，防止出现type和上次的type一样的情况。情况复用池应该可以
    }

    /**
     * 任意一个subAdapter的数据源改变，都可以通过调用该方法来刷新。类似于notifyDataSetChanged
     */
    public void simpleNotifyDataSetChanged() {
        updateIndex();
        notifyDataSetChanged();
        // TODO: by xk 2019/4/24 上午10:39 更新position和subadapter的索引
    }

    public void updateIndex() {
        clear();
        int startPosition = 0;
        int index = 0;
        for (AbsSubAdapter mAdapter : mAdapters) {
            mAdapter.setDelegateAdapter(this);
            mAdapter.mStartPosition = startPosition;
            startPosition += mAdapter.getItemCount();
            mAdapter.setIndex(index);
            index++;
        }
        for (AbsSubAdapter adapter : mAdapters) {
            total += adapter.getItemCount();
        }
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookUp() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                AbsSubAdapter subAdapter = findSubAdapterByPosition(position);
                if (subAdapter == null) {
                    throw new SimpleNestListException(position + "的adapter没找到");
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
    public void setSpanCount(int[] supportSpanCount) {
        if (mLayoutManager == null) {
            throw new SimpleNestListException();
        }
        int spanCount = mLayoutManager.getSpanCount();
        int spanCountTemp = spanCount;
        for (int i : supportSpanCount) {
            spanCountTemp = leastCommonMultiple(spanCountTemp, i);
        }
        if (spanCount != spanCountTemp) {
            mLayoutManager.setSpanCount(spanCountTemp);
        }
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

