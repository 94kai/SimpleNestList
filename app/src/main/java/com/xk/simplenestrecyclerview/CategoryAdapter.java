package com.xk.simplenestrecyclerview;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xk.simplenestlist.AbsSubAdapter;
import com.xk.simplenestlist.BaseViewHolder;
import com.xk.simplenestlist.layouthelper.LayoutHelper;
import com.xk.simplenestrecyclerview.bean.CategoryBean;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public class CategoryAdapter extends AbsSubAdapter<CategoryBean> {

    public CategoryAdapter(LayoutHelper layoutHelper) {
        super(layoutHelper);
    }

    @Override
    public BaseViewHolder onCreateViewHolderForAll(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = null;
        if (viewType == 0) {
            itemView = View.inflate(viewGroup.getContext(), R.layout.layotu_button_1, null);
        } else if (viewType == 1) {
            itemView = View.inflate(viewGroup.getContext(), R.layout.layotu_button_2, null);
        }
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        Button button = (Button) baseViewHolder.itemView.findViewById(R.id.btn);
        button.setText("CategoryAdapter" + i + "  ");
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }
}
