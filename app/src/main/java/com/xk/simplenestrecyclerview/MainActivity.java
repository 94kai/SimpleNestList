package com.xk.simplenestrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xk.simplenestlist.DelegateAdapter;
import com.xk.simplenestlist.SimpleNestLayoutManager;
import com.xk.simplenestlist.adapter.AbsSubAdapter;
import com.xk.simplenestlist.adapter.SingleAdapter;
import com.xk.simplenestlist.layouthelper.CrossGridLayoutHelper;
import com.xk.simplenestlist.layouthelper.GridLayoutHelper;
import com.xk.simplenestlist.layouthelper.LinearLayoutHelper;
import com.xk.simplenestlist.layouthelper.SingleLayoutHelper;
import com.xk.simplenestlist.layouthelper.TitleGridLayoutHelper;
import com.xk.simplenestrecyclerview.bean.BrandBean;
import com.xk.simplenestrecyclerview.bean.CategoryBean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    LinkedList<AbsSubAdapter> absSubAdapters = new LinkedList<>();
    private CategoryAdapter categoryAdapter;
    private List<CategoryBean> categoryBeans;
    private DelegateAdapter delegateAdapter;
    private BrandAdapter brandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        Toolbar toolBar = findViewById(R.id.action_bar);
        toolBar.setSubtitle("点击操作数据源");
        toolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help();
            }
        });

        SimpleNestLayoutManager layoutManager = new SimpleNestLayoutManager(this);
        delegateAdapter = new DelegateAdapter(layoutManager, new ShareAllTypeProvider());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 15);
        recycledViewPool.setMaxRecycledViews(1, 15);
        recyclerView.setRecycledViewPool(recycledViewPool);
        List<BrandBean> brandBeans = createDatas(BrandBean.class);
        categoryBeans = createDatas(CategoryBean.class);


        BrandAdapter service = new BrandAdapter(new TitleGridLayoutHelper(3), ShareAllTypeProvider.ADAPTER_ID_TITLE_GRID);
        service.setData(brandBeans);

        categoryAdapter = new CategoryAdapter(new GridLayoutHelper(4), ShareAllTypeProvider.ADAPTER_ID_GRID);
        categoryAdapter.setData(categoryBeans);


        BrandAdapter serviceList = new BrandAdapter(new LinearLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_LIST);
        serviceList.setData(brandBeans);

        BrandAdapter serviceList2 = new BrandAdapter(new TitleGridLayoutHelper(2), ShareAllTypeProvider.ADAPTER_ID_TITLE_GRID);
        serviceList2.setData(brandBeans);

        absSubAdapters.add(service);
        absSubAdapters.add(new SingleAdapter(new SingleLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_SINGLE_DIVIDER));
        absSubAdapters.add(categoryAdapter);
        absSubAdapters.add(serviceList);
        absSubAdapters.add(serviceList2);
        absSubAdapters.add(new SingleAdapter(new SingleLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_SINGLE_DIVIDER));

        brandAdapter = new BrandAdapter(new CrossGridLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_GRID);
        brandAdapter.setData(createDatas(BrandBean.class, 25));
        absSubAdapters.add(brandAdapter);
        delegateAdapter.setAdapters(absSubAdapters);
        recyclerView.setAdapter(delegateAdapter);
    }


    private <T> List<T> createDatas(Class<T> clazz, int count) {
        List<T> beans = new ArrayList<>();
        try {
            for (int i = 0; i < count; i++) {
                if (clazz == CategoryBean.class) {
                    beans.add(clazz.getConstructor(String.class).newInstance("" + i));
                } else {
                    beans.add(clazz.getConstructor().newInstance());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return beans;
    }

    private <T> List<T> createDatas(Class<T> clazz) {
        return createDatas(clazz, 12);
    }

    public void help() {

        //==================数据操作使用说明
        //1.1修改subAdapter数据源
//        categoryBeans.remove(0);
//        categoryBeans.set(1,new CategoryBean("修改后的"));
//        final List oldDatas = new ArrayList(categoryBeans);
//        categoryBeans.add(4, new CategoryBean("新增第四个"));
//        categoryBeans.clear();
//        CategoryBean temp = categoryBeans.remove(2);
//        categoryBeans.add(10, temp);
        //1.2修改subAdapter的getItemCount方法，实现类似于二级菜单打开关闭的功能
//        categoryAdapter.setCloseState(true);

        //==================recyclerView刷新说明
        //2.1简单粗暴法。刷整个recyclerview
        // 修改任意一个subAdapter的数据源之后，可以调用delegateAdapter.simpleNotifyDataSetChanged来刷新，注意不是系统提供的notifyDataSetChanged方法
//        delegateAdapter.simpleNotifyDataSetChanged();
        //2.2略微粗暴法。刷一个subAdapter的小区域，目前只想到这种方法。大家有其他思路的可以提一提
        //2.2.1使用默认的DiffCallback，即认为调用该方法，先移除，后新增，效率略低
//        categoryAdapter.simpleNotifyDataSetChanged(oldDatas, categoryBeans);
        //2.2.3使用自定义的DiffCallback，会根据数据源的变化情况来刷，效率略高
//        categoryAdapter.simpleNotifyDataSetChanged(oldDatas, categoryBeans,new DefaultDiffCallback<CategoryBean>(){
            // TODO: by xk 2019/4/25 下午3:56 实现各种自己的实现
//        });
        //2.3itemChanged
//        categoryAdapter.simpleNotifyItemChanged(1);
        //2.4itemRemove
//        categoryAdapter.simpleNotifyItemRemoved(0);
        //2.5itemMove
//        categoryAdapter.simpleNotifyItemMoved(2, 10);
        //2.6itemInserted
//        categoryAdapter.simpleNotifyItemInserted(4);
        //2.7itemRangeChanged
//        categoryAdapter.simpleNotifyItemRangeChanged(1,4);
        //2.8itemRangeRemoved
//        categoryAdapter.simpleNotifyItemRangeRemoved(1,4);
        //2.8itemRangeInserted
//        categoryAdapter.simpleNotifyItemRangeInserted(1,4);

    }
}
