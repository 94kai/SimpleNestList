package com.xk.simplenestlist;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public class SimpleNestLayoutManager extends GridLayoutManager {
    public SimpleNestLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SimpleNestLayoutManager(Context context) {
        super(context, 1);
    }

    public SimpleNestLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

}
