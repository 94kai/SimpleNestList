package com.xk.simplenestlist.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xk.simplenestlist.BaseViewHolder;
import com.xk.simplenestlist.layouthelper.LayoutHelper;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public class SingleAdapter<Data> extends AbsSubAdapter<Data> {
    public SingleAdapter(LayoutHelper layoutHelper) {
        super(layoutHelper);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView;
        itemView = new TextView(viewGroup.getContext());
        ((TextView) itemView).setText("我是一条分割线，你可以把我高度设为0.");
        itemView.setBackgroundColor(Color.RED);

        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
    }

    @Override
    public final int getItemCount() {
        return 1;
    }
}
