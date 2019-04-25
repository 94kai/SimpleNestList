# 一款仿VLayout的可嵌套RecyclerView框架

# 鸣谢 ^_^
https://github.com/alibaba/vlayout

## 已实现的效果图

> 以下只是暂时已经实现的几个布局，他们支持任意组合，搭配。相同类型的view之间支持互相复用。更重要的是扩展出新的布局也很简单，可以直接参考已经实现的效果去做。

列表

 ![列表](https://github.com/XSation/SimpleNestList/blob/master/image/list.png)

网格

 ![网格](https://github.com/XSation/SimpleNestList/blob/master/image/grid.png)

带title的网格

 ![带title的网格](https://github.com/XSation/SimpleNestList/blob/master/image/titlegrid.png)


横跨view的独立控件

 ![横跨view的独立控件](https://github.com/XSation/SimpleNestList/blob/master/image/single.png)

交错的网格（一个示例，具体可以根据自己产品需求自定义）

 ![交错的网格](https://github.com/XSation/SimpleNestList/blob/master/image/crossgridlayout.png)

搭配起来的效果

 ![demo](https://github.com/XSation/SimpleNestList/blob/master/image/demo.jpeg)


## 什么地方需要用到该框架（当然用VLayout是首选，不过重复造轮子也没什么不好，重要的是学习知识点 ^_^...）

![淘宝首页](https://github.com/XSation/SimpleNestList/blob/master/image/taobaohome.jpeg)
![淘宝筛选面板](https://github.com/XSation/SimpleNestList/blob/master/image/taobaofilter.jpeg)
![京东首页](https://github.com/XSation/SimpleNestList/blob/master/image/jdhome.jpeg)
![京东筛选面板](https://github.com/XSation/SimpleNestList/blob/master/image/jdfilter.jpeg)
![京东分类](https://github.com/XSation/SimpleNestList/blob/master/image/jdcategory.jpeg)

## 优缺点
- 对外暴露的api模仿VLayout实现，使用方式与VLayout一样简单。简化了开发步骤。
- 相同类型的view之间支持互相复用。
- 对于布局的实现，使用gridLayoutManager控制span实现。
    - 相较于VLayout：
        - 实现方式更low ^_^
        - 不支持流式布局
        - 但也有好处：扩展容易，只需要继承LayoutHelper，实现几个方法即可，具体可参考layouthelper包下的几个类。
- 目前已经实现的Layout：
    - LinearLayoutHelper 线性的，即类似于listView那样
    - GridLayoutHelper 网格型的，类似于GridView
    - TitleGridLayoutHelper title拖grid，可以实现类似于京东、淘宝、天猫搜索页面的筛选面板那样的布局。
    - SingleLayoutHelper 通栏型。 即放置一个控件，横向铺满屏幕。
        - 本来不打算实现该布局，因为他其实就是一个长度的LinearLayoutHelper。后来遇到一个问题。如下：
        三列grid+四列grid组合时，当三列gird最后一行不全时，四列gird的第一个item会顶上去，此时可以给两个模块中插入一个singleLayoutHelper
        - 参考https://github.com/XSation/SimpleNestList/blob/master/lib/src/main/java/com/xk/simplenestlist/layouthelper/SingleLayoutHelper.java
        - 所以干脆把他写出来了，使用也稍微方便了一点点。
    - 等等，具体以上面效果图为准
- 关于itemShareType
    - 类似于VLayout的mHasConsistItemType变量。
    - 如果为ITEM_SHARE_TYPE_ALL类型，表示整个recyclerview中，所有的item，只要type相同，就可以彼此复用。然而这样也会引入一个问题，不同的子adapter，不好把控viewtype，可能出现两个子adapter中定义了相同的viewtype，但是他们的样式并不相同，这样就会出现显示的bug
        - 解决方案：对于ITEM_SHARE_TYPE_ALL类型，把onCreateViewHolder和getItemViewType定义为final，强制用户通过IShareAllTypeProvider接口定义type和创建viewholder。
    - 如果为ITEM_SHARE_TYPE_SUBADAPTER类型，表示对于每一个子adapter，都是一套独立的viewtype，开发起来就跟我们写一个最基本的adapter一样。
        - 用getItemViewTypeForSubAdapter和onCreateViewHolderForSubAdapter方法来代替原生方法
        - 目前暂未实现这种模式。

# TODO
- 可以利用ItemDecoration实现类似阿里的fix类型的布局。开发者同样使用adapter的形式使用，delegateadapter中把这种helper的subadapter过滤掉，单独处理。
- 吸顶效果
- 支持ITEM_SHARE_TYPE_SUBADAPTER