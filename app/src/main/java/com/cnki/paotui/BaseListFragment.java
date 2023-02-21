package com.cnki.paotui;

import static com.cnki.paotui.Ikeys.EXTRE_ORDER;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.adapter.OrderAdapter;
import com.cnki.paotui.db.Order;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/19
 * Time: 16:49
 * 此类的作用为：
 */
public abstract class BaseListFragment extends Fragment {
    private View mRootView;

    List<Order> list=new ArrayList<>();
    public OrderAdapter orderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragmentlist, container, false);
        RecyclerView recyclerView=mRootView.findViewById(R.id.listview);
        list=getList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter=new OrderAdapter(list);
        orderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if(list.get(position).ordertype==0){
                    Intent intent=new Intent(getActivity(),AddActivity.class);
                    intent.putExtra(EXTRE_ORDER,list.get(position));
                    getActivity().startActivity(intent);
                }
                if(list.get(position).ordertype==1){
                    Intent intent=new Intent(getActivity(),Add1Activity.class);
                    intent.putExtra(EXTRE_ORDER,list.get(position));
                    getActivity().startActivity(intent);
                }

                if(list.get(position).ordertype==2){
                    Intent intent=new Intent(getActivity(),Add2Activity.class);
                    intent.putExtra(EXTRE_ORDER,list.get(position));
                    getActivity().startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(orderAdapter);
        initView();
        EventBus.getDefault().register(this);
        return mRootView;
    }
    public void refreshView(){
        list=getList();
        orderAdapter.setNewInstance(list);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event){
        refreshView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            refreshView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }
    public  void initView(){

    }
    protected abstract List<Order> getList();
}
