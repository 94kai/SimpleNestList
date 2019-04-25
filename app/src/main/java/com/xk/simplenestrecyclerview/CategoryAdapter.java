package com.xk.simplenestrecyclerview;

import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.xk.simplenestlist.BaseViewHolder;
import com.xk.simplenestlist.adapter.AbsSubAdapter;
import com.xk.simplenestlist.layouthelper.LayoutHelper;
import com.xk.simplenestlist.layouthelper.TitleGridLayoutHelper;
import com.xk.simplenestrecyclerview.bean.CategoryBean;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public class CategoryAdapter extends AbsSubAdapter<CategoryBean> {

    /**
     * 实现类似二级菜单展开、收起的功能
     */
    boolean isClose = false;

    public CategoryAdapter(LayoutHelper layoutHelper, int adapterId) {
        super(layoutHelper, adapterId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (mLayoutHelper instanceof TitleGridLayoutHelper) {
            if (i != 0) {
                Button button = (Button) baseViewHolder.itemView;
                button.setText(mData.get(i).name + "category:" + i + "  ");
            } else {
                ((TextView) baseViewHolder.itemView).setText("我是category的title");
            }
        } else {
            Button button = (Button) baseViewHolder.itemView;
            button.setText(mData.get(i).name + "category:" + i);
        }
    }

    public void setCloseState(boolean isClose) {
        this.isClose = isClose;
    }


    @Override
    public int getItemCount() {
        if (isClose) {
            //关闭后只显示3个item。这块注意，数字至少也要大于mData.size()，否则onbindviewholder里会越界
            return super.getItemCount() / 2;
        } else {
            //打开后显示全部
            return super.getItemCount();
        }
    }
}
