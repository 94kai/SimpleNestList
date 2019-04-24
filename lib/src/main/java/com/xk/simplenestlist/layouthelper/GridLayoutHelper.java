package com.xk.simplenestlist.layouthelper;

import com.xk.simplenestlist.SimpleNestListException;

/**
 * 网格型的
 * | - - - |
 * | - - - |
 * | - - - |
 * | - - - |
 * | - - - |
 *
 * @author xuekai1
 * @date 2019/4/24
 */
public class GridLayoutHelper extends LayoutHelper {

    private final int mColumn;

    public GridLayoutHelper(int column) {
        this.mColumn = column;
        this.needSpan = column;
    }

    @Override
    public int getSpanForPosition(int position, int maxSpanCount) {
        //有个问题，每个adapter之间可能会在一行。可以通过插入一个0px的singleLayoutHelper实现
        if (mColumn > maxSpanCount) {
            throw new SimpleNestListException("gridLayoutHelper不能指定大于maxSpanCount的column");
        }
        int span = maxSpanCount / mColumn;
        if (span * mColumn != maxSpanCount) {
            //校验一下是否整除了
            throw new SimpleNestListException("maxSpanCount设置有误。请设置所有可能存在的列数的最小公倍数");
        }
        return span;
    }
}
