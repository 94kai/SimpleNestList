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

    /**
     * 构造
     * @param context
     * @param supportRow 需要支持的列数，比如整个recyclerview中某个模块需要2列，某个3列，某个4列，就输入2，3，4
     */
//    public SimpleNestLayoutManager(Context context, int... supportRow) {
//        // TODO: by xk 2019/4/24 下午1:16 maxSpan为所有列数的最小公倍数
//        super(context, maxSpan);
//    }


}
