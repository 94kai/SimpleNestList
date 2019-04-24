package com.xk.simplenestrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xk.simplenestlist.AbsSubAdapter;
import com.xk.simplenestlist.DelegateAdapter;
import com.xk.simplenestlist.layouthelper.GridLayoutHelper;
import com.xk.simplenestlist.layouthelper.TitleGridLayoutHelper;
import com.xk.simplenestrecyclerview.bean.CategoryBean;
import com.xk.simplenestrecyclerview.bean.JDServiceBean;

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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        final DelegateAdapter delegateAdapter = new DelegateAdapter();

        // TODO: by xk 2019/4/24 下午1:16 maxSpan为所有列数的最小公倍数
        int maxSpan = 2520;

        recyclerView.setLayoutManager(new GridLayoutManager(this, maxSpan));

        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(delegateAdapter.getSpanSizeLookUp((GridLayoutManager) recyclerView.getLayoutManager()));
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,15);
        recycledViewPool.setMaxRecycledViews(1,15);
        recyclerView.setRecycledViewPool(recycledViewPool);
        List<JDServiceBean> jdServiceBeans = createDatas(JDServiceBean.class);
        List<CategoryBean> categoryBeans = createDatas(CategoryBean.class);
        JDServiceAdapter jdService = new JDServiceAdapter(new TitleGridLayoutHelper(3));
        jdService.setData(jdServiceBeans);
        CategoryAdapter category = new CategoryAdapter(new GridLayoutHelper(4));
        category.setData(categoryBeans);

        absSubAdapters.add(jdService);
        absSubAdapters.add(category);
        delegateAdapter.setAdapters(absSubAdapters);
        recyclerView.setAdapter(delegateAdapter);
    }

    private <T> List<T> createDatas(Class<T> clazz) {
        List<T> beans = new ArrayList<>();
        try {
            for (int i = 0; i < 100; i++) {
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
}
