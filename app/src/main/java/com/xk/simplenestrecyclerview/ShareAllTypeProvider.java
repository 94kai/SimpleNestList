package com.xk.simplenestrecyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xk.simplenestlist.BaseViewHolder;
import com.xk.simplenestlist.IShareAllTypeProvider;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public class ShareAllTypeProvider implements IShareAllTypeProvider {
    public static final int ADAPTER_ID_SINGLE_DIVIDER = 0;
    public static final int ADAPTER_ID_TITLE_GRID = 1;
    public static final int ADAPTER_ID_GRID = 2;
    public static final int ADAPTER_ID_LIST = 3;


    public static final int VIEW_TYPE_DIVIDER = 0;
    public static final int VIEW_TYPE_TITLE = 1;
    public static final int VIEW_TYPE_BUTTON = 2;


    @Override
    public int getItemViewType(int position, int adapterId) {
        if (adapterId == ADAPTER_ID_SINGLE_DIVIDER) {
            return VIEW_TYPE_DIVIDER;
        } else if (adapterId == ADAPTER_ID_TITLE_GRID) {
            if (position == 0) {
                return VIEW_TYPE_TITLE;
            } else {
                return VIEW_TYPE_BUTTON;
            }
        } else if (adapterId == ADAPTER_ID_GRID) {
            return VIEW_TYPE_BUTTON;
        } else if (adapterId == ADAPTER_ID_LIST) {
            return VIEW_TYPE_BUTTON;
        }
        return 0;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_DIVIDER:
                itemView = new TextView(parent.getContext());
                ((TextView) itemView).setText("我是一条分割线，你可以把我高度设为0.");
                break;
            case VIEW_TYPE_TITLE:
                itemView = new TextView(parent.getContext());
                break;
            case VIEW_TYPE_BUTTON:
                itemView = new Button(parent.getContext());
                break;
            default:
                itemView = new View(parent.getContext());
                break;
        }
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new BaseViewHolder(itemView);
    }
}
