package com.xk.simplenestrecyclerview;

import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.xk.simplenestlist.BaseViewHolder;
import com.xk.simplenestlist.adapter.AbsSubAdapter;
import com.xk.simplenestlist.layouthelper.LayoutHelper;
import com.xk.simplenestlist.layouthelper.TitleGridLayoutHelper;
import com.xk.simplenestrecyclerview.bean.BrandBean;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public class BrandAdapter extends AbsSubAdapter<BrandBean> {

    public BrandAdapter(LayoutHelper layoutHelper, int adapterId) {
        super(layoutHelper, adapterId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (mLayoutHelper instanceof TitleGridLayoutHelper) {
            if (i != 0) {
                Button button = (Button) baseViewHolder.itemView;
                button.setText("brand:" + i + "  ");
            } else {
                ((TextView) baseViewHolder.itemView).setText("我是brand的title");
            }
        } else {
            Button button = (Button) baseViewHolder.itemView;
            button.setText("brand:" + i + "  ");
        }
    }
}