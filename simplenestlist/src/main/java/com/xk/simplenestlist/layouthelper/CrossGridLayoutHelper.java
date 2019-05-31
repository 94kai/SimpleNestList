package com.xk.simplenestlist.layouthelper;

/**
 * 一个交叉型的网格布局的实例。也可以模仿实现其他自定义各种交叉规则，或者实现一个通用的helper。
 * 由于该类型不定性太大，完全取决于
 * 业务需求，所以我不会实现一个通用的，毕竟越通用，使用门槛越高。大家自己跟着定制自己的规则即可。
 * | -|-|- |
 * | -|--- |
 * | ---|- |
 * | --|-- |
 * | - - - |
 * | - - - |
 *
 * @author xuekai1
 * @date 2019/4/24
 */
public class CrossGridLayoutHelper extends LayoutHelper {


    public CrossGridLayoutHelper() {
        //第一行3列，第二行可以认为是4列。所以是3 4
        this.needSpan = new int[]{3, 4};
    }

    @Override
    public int getSpanForPosition(int position, int maxSpanCount) {
        //一行需要分成几列，就用maxSpanCount除以几。
        if (position < 3) {
            return maxSpanCount / 3;
        } else if (position == 3) {
            return maxSpanCount / 4;
        } else if (position == 4) {
            return maxSpanCount / 4 * 3;
        } else if (position == 5) {
            return maxSpanCount / 4 * 3;
        } else if (position == 6) {
            return maxSpanCount / 4;
        } else if (position < 9) {
            return maxSpanCount / 2;
        } else {
            return maxSpanCount / 3;
        }
    }
}
