package com.xk.simplenestlist.layouthelper;

/**
 * 自定义布局继承他
 * @author xuekai1
 * @date 2019/4/24
 */
public abstract class LayoutHelper {

    /**
     * 需要支持的span。如果有多行需要支持3、4、5、6列，就用3 4 5 6
     */
    protected int[] needSpan = {1};

    public abstract int getSpanForPosition(int position, int maxSpanCount);

    public int[] getNeedSpan() {
        return needSpan;
    }
}
