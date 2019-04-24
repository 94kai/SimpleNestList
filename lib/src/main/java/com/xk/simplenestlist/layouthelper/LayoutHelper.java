package com.xk.simplenestlist.layouthelper;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public abstract class LayoutHelper {

    /**
     * 需要支持的span
     */
    protected int needSpan = 1;

    public abstract int getSpanForPosition(int position, int maxSpanCount);

    public int getNeedSpan() {
        return needSpan;
    }
}
