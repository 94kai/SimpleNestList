package com.xk.simplenestlist.layouthelper;

import com.xk.simplenestlist.SimpleNestListException;

/**
 * 带有title的网格型的
 * | ----- |
 * | - - - |
 * | - - - |
 * | - - - |
 * | - - - |
 *
 * @author xuekai1
 * @date 2019/4/24
 */
public class TitleGridLayoutHelper extends LayoutHelper {

    private final int mRow;

    public TitleGridLayoutHelper(int row) {
        this.mRow = row;
        this.needSpan = row;
    }

    @Override
    public int getSpanForPosition(int position, int maxSpanCount) {
        // TODO: by xk 2019/4/24 下午1:24 有个问题，每个adapter之间可能会在一行，或许可以通过重写getSpanIndex实现
        if (mRow > maxSpanCount) {
            throw new SimpleNestListException("TitleGridLayoutHelper不能指定大于maxSpanCount" + maxSpanCount + "的row" + mRow);
        }
        if (position == 0) {
            return maxSpanCount;
        }
        int span = maxSpanCount / mRow;
        if (span * mRow != maxSpanCount) {
            //校验一下是否整除了
            throw new SimpleNestListException("maxSpanCount设置有误。请设置所有可能存在的列数的最小公倍数");
        }
        return span;
    }
}
