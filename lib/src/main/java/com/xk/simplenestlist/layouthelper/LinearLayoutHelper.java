package com.xk.simplenestlist.layouthelper;

import com.xk.simplenestlist.SimpleNestListException;

/**
 * 线性的
 * | - |
 * | - |
 * | - |
 * | - |
 * | - |
 * | - |
 *
 * @author xuekai1
 * @date 2019/4/24
 */
public class LinearLayoutHelper extends LayoutHelper {

    @Override
    public int getSpanForPosition(int position, int maxSpanCount) {
        if ((maxSpanCount<1)) {
            throw new SimpleNestListException("spanCount要大于1");
        }
        return 1;
    }
}