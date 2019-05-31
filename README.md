# 一款仿VLayout的可嵌套RecyclerView框架

# 鸣谢 ^_^
https://github.com/alibaba/vlayout

## 使用

- 项目级build.gradle添加
```
allprojects {
    repositories {
        ...
        maven{
            url "https://dl.bintray.com/xk3440395/maven"
        }
    }
}
```
- module级build.gradle添加
```

```

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
        - 参考https://github.com/XSation/SimpleNestList/blob/master/simplenestlist/src/main/java/com/xk/simplenestlist/layouthelper/SingleLayoutHelper.java
        - 所以干脆把他写出来了，使用也稍微方便了一点点。
    - 等等，具体以上面效果图为准
- 关于itemShareType
    - 类似于VLayout的mHasConsistItemType变量。
    - 最开始支持了不同模块之间viewtype共享，后来发现这样的话使用起来成本高，而且意义不是太大，所以直接删了。各模块之间的type互不干扰。
    - 每个subAdapter随意实现自己的getItemType即可

## 数据操作、ui刷新

目前支持：
- 整个recyclerView一次性刷。简单粗暴。
- 针对一个subAdapter刷。由于subAdapter的模块化开发。所以也很简单。同时效率也更好。
- 不要使用Adapter官方提供的方法!!!不要使用Adapter官方提供的方法!!!不要使用Adapter官方提供的方法!!!
- 构建AbsSubAdapter时传入RecycledViewPool，然后通过调用setMaxRecycledViews方法设置各种viewType的回收池大小
### 如下：

- 简单粗暴法。刷整个recyclerview
    - 修改任意一个subAdapter的数据源之后，可以调用delegateAdapter.simpleNotifyDataSetChanged来刷新，注意不是系统提供的notifyDataSetChanged方法
        - delegateAdapter.simpleNotifyDataSetChanged();
- 略微粗暴法。
    - 刷一个subAdapter的小区域，目前只想到这种方法，并且必须要传入新旧数据源，没有好的思路保存这两数据源。大家有其他思路的可以提一提。
    - 使用默认的DiffCallback，即认为调用该方法，先移除，后新增，效率略低
        - categoryAdapter.simpleNotifyDataSetChanged(oldDatas, categoryBeans);
    - 使用自定义的DiffCallback，会根据数据源的变化情况来刷，效率略高
        - categoryAdapter.simpleNotifyDataSetChanged(oldDatas, categoryBeans,new DefaultDiffCallback<CategoryBean>(){// TODO: by xk 2019/4/25 下午3:56 实现各种自己的实现});
- itemChanged
    - categoryAdapter.simpleNotifyItemChanged(1);
- itemRemove
    - categoryAdapter.simpleNotifyItemRemoved(0);
- itemMove
    - categoryAdapter.simpleNotifyItemMoved(2, 10);
- itemInserted
    - categoryAdapter.simpleNotifyItemInserted(4);
- itemRangeChanged
    - categoryAdapter.simpleNotifyItemRangeChanged(1,4);
- itemRangeRemoved
    - categoryAdapter.simpleNotifyItemRangeRemoved(1,4);
- itemRangeInserted
    - categoryAdapter.simpleNotifyItemRangeInserted(1,4);


# TODO
- 可以利用ItemDecoration实现类似阿里的fix类型的布局。开发者同样使用adapter的形式使用，delegateadapter中把这种helper的subadapter过滤掉，单独处理。
- 吸顶效果
- 针对subAdapter刷所有数据时，不需要传入新旧数据源（没有想到较好的办法去保存修改前后数据源）