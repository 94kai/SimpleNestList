# 一款仿VLayout的可嵌套RecyclerView框架

# 鸣谢 ^_^
https://github.com/alibaba/vlayout

## 一个demo
 ![demo](https://github.com/kaikaixue/)

```java
BrandAdapter jdService = new BrandAdapter(new TitleGridLayoutHelper(3), ShareAllTypeProvider.ADAPTER_ID_TITLE_GRID);
jdService.setData(brandBeans);

CategoryAdapter category = new CategoryAdapter(new GridLayoutHelper(4), ShareAllTypeProvider.ADAPTER_ID_GRID);
category.setData(categoryBeans);


BrandAdapter jdServiceList = new BrandAdapter(new LinearLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_LIST);
jdServiceList.setData(brandBeans);

BrandAdapter jdServiceList2 = new BrandAdapter(new TitleGridLayoutHelper(2), ShareAllTypeProvider.ADAPTER_ID_TITLE_GRID);
jdServiceList2.setData(brandBeans);


absSubAdapters.add(jdService);
absSubAdapters.add(new SingleAdapter(new SingleLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_SINGLE_DIVIDER));
absSubAdapters.add(category);
absSubAdapters.add(jdServiceList);
absSubAdapters.add(jdServiceList2);

delegateAdapter.setAdapters(absSubAdapters);
```

## 什么地方需要用到该框架（当然用VLayout是首选，不过重复造轮子也没什么不好，重要的是学习知识点 ^_^...）

 ![淘宝首页](https://github.com/kaikaixue/)
 ![淘宝筛选面板](https://github.com/kaikaixue/)
 ![京东首页](https://github.com/kaikaixue/)
 ![京东筛选面板](https://github.com/kaikaixue/)
 ![京东分类](https://github.com/kaikaixue/)




##
- 对外暴露的api模仿VLayout实现，简化了开发步骤。
- 对于布局的实现，使用gridLayoutManager控制span实现。
    - 相较于VLayout：
        - 实现方式更low ^_^
        - 但也有好处：扩展容易，只需要继承LayoutHelper，实现几个方法即可，具体可参考layouthelper包下的几个类。
- 目前已经实现的Layout：
    - LinearLayoutHelper 线性的，即类似于listView那样
    - GridLayoutHelper 网格型的，类似于GridView
    - TitleGridLayoutHelper title拖grid，可以实现类似于京东、淘宝、天猫搜索页面的筛选面板那样的布局。
    - SingleLayoutHelper 通栏型。 即放置一个控件，横向铺满屏幕。
        - 本来不打算实现该布局，因为他其实就是一个长度的LinearLayoutHelper。后来遇到一个问题。如下：
        三列grid+四列grid组合时，当三列gird最后一行不全时，四列gird的第一个item会顶上去，此时可以给两个模块中插入一个singleLayoutHelper
        - 所以干脆把他写出来了，使用也稍微方便了一点点。
- 关于itemShareType
    - 类似于VLayout的mHasConsistItemType变量。
    - 如果为ITEM_SHARE_TYPE_ALL类型，表示整个recyclerview中，所有的item，只要type相同，就可以彼此复用。然而这样也会引入一个问题，不同的子adapter，不好把控viewtype，可能出现两个子adapter中定义了相同的viewtype，但是他们的样式并不相同，这样就会出现显示的bug
        - 解决方案：对于ITEM_SHARE_TYPE_ALL类型，把onCreateViewHolder和getItemViewType定义为final，强制用户通过IShareAllTypeProvider接口定义type和创建viewholder。
    - 如果为ITEM_SHARE_TYPE_SUBADAPTER类型，表示对于每一个子adapter，都是一套独立的viewtype，开发起来就跟我们写一个最基本的adapter一样。
        - 用getItemViewTypeForSubAdapter和onCreateViewHolderForSubAdapter方法来代替原生方法
        - 目前暂未实现这种模式。

