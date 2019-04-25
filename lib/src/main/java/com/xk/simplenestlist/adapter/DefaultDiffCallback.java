package com.xk.simplenestlist.adapter;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * @author xuekai1
 * @date 2019/4/25
 */
public class DefaultDiffCallback<Data> extends DiffUtil.Callback {
    private List<Data> oldData, newData;

    public DefaultDiffCallback() {

    }

    public void setDatas(List<Data> oldData, List<Data> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData == null ? 0 : oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData == null ? 0 : newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
