package com.xk.simplenestlist;

import android.view.ViewGroup;

/**
 * 当使用ITEM_SHARE_TYPE_ALL模式时，使用该接口定义viewtype、创建viewholder
 * @author xuekai1
 * @date 2019/4/24
 */
public interface IShareAllTypeProvider {
    /**
     * ITEM_SHARE_TYPE_ALL模式下通过该方法获取viewType，保证了统一性
     * @param position item在某一个子Adatper中的position
     * @param adapterId 所属的子adapter的id，该id自己定义，用来区分不同的子adapter，甚至可以定义为相同的id，但是
     *                  在设置viewType的时候可能就会有障碍了。
     */
    int getItemViewType(int position, int adapterId);

    /**
     * ITEM_SHARE_TYPE_ALL模式下通过该方法创建viewholder，防止相同的viewtype创建不同的view
     */
    BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
}
