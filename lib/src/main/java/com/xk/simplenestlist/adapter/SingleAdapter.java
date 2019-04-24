package com.xk.simplenestlist.adapter;

import android.support.annotation.NonNull;

import com.xk.simplenestlist.BaseViewHolder;
import com.xk.simplenestlist.layouthelper.LayoutHelper;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public class SingleAdapter<Data> extends AbsSubAdapter<Data>{
    public SingleAdapter(LayoutHelper layoutHelper, int adapterId) {
        super(layoutHelper, adapterId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
    }

    @Override
    public final int getItemCount() {
        return 1;
    }
}
