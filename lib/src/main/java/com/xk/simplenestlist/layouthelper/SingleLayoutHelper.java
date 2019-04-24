package com.xk.simplenestlist.layouthelper;

import com.xk.simplenestlist.SimpleNestListException;

/**
 * 只显示一个组件
 *
 * 还可以用来解决如下问题
 *
 * 三列grid+四列grid
 * | -   -   - |
 * | -   -   - |
 * | -   -   - |
 * | - - - - - |
 * | - - - - - |
 * | - - - - - |
 * 当三列gird最后一行不全时，四列gird的第一个item会顶上去，如下
 * | -   -   - |
 * | -   -   - |
 * | -   - -   | 该行最后一个元素是下面四列grid的模块
 * | - - - - - |
 * | - - - - - |
 * | - - - -   |
 * 此时可以给两个模块中插入一个singleLayoutHelper
 * @author xuekai1
 * @date 2019/4/24
 */
public class SingleLayoutHelper extends LayoutHelper {
    @Override
    public int getSpanForPosition(int position, int maxSpanCount) {
        if (position>0) {
            throw new SimpleNestListException("singleLayoutHelper只支持一个item");
        }
        return maxSpanCount;
    }
}
