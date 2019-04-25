package com.xk.simplenestrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);

        SimpleNestLayoutManager layoutManager = new SimpleNestLayoutManager(this);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, new ShareAllTypeProvider());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 15);
        recycledViewPool.setMaxRecycledViews(1, 15);
        recyclerView.setRecycledViewPool(recycledViewPool);
        List<BrandBean> brandBeans = createDatas(BrandBean.class);
        List<CategoryBean> categoryBeans = createDatas(CategoryBean.class);


        BrandAdapter service = new BrandAdapter(new TitleGridLayoutHelper(3), ShareAllTypeProvider.ADAPTER_ID_TITLE_GRID);
        service.setData(brandBeans);

        CategoryAdapter category = new CategoryAdapter(new GridLayoutHelper(4), ShareAllTypeProvider.ADAPTER_ID_GRID);
        category.setData(categoryBeans);


        BrandAdapter serviceList = new BrandAdapter(new LinearLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_LIST);
        serviceList.setData(brandBeans);

        BrandAdapter serviceList2 = new BrandAdapter(new TitleGridLayoutHelper(2), ShareAllTypeProvider.ADAPTER_ID_TITLE_GRID);
        serviceList2.setData(brandBeans);

recyclerView.addItemDecoration();
        absSubAdapters.add(service);
        absSubAdapters.add(new SingleAdapter(new SingleLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_SINGLE_DIVIDER));
        absSubAdapters.add(category);
        absSubAdapters.add(serviceList);
        absSubAdapters.add(serviceList2);
        absSubAdapters.add(new SingleAdapter(new SingleLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_SINGLE_DIVIDER));

        BrandAdapter brandAdapter = new BrandAdapter(new CrossGridLayoutHelper(), ShareAllTypeProvider.ADAPTER_ID_GRID);
        brandAdapter.setData(createDatas(BrandBean.class,25));
        absSubAdapters.add(brandAdapter);

        delegateAdapter.setAdapters(absSubAdapters);
        recyclerView.setAdapter(delegateAdapter);
    }



    private <T> List<T> createDatas(Class<T> clazz, int count) {
        List<T> beans = new ArrayList<>();
        try {
            for (int i = 0; i < count; i++) {
                beans.add(clazz.getConstructor().newInstance());
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
        return createDatas(clazz,12);
    }
}
