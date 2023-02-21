package com.cnki.paotui.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cnki.paotui.App;
import com.cnki.paotui.BaseListFragment;
import com.cnki.paotui.Ikeys;
import com.cnki.paotui.db.Order;
import com.cnki.paotui.db.OrderDao;

import java.util.List;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/19
 * Time: 20:21
 * 此类的作用为：
 */
public class MyOrderFragment extends BaseListFragment {
    int type;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type= getArguments().getInt(Ikeys.TYPE,-1);
    }

    @Override
    protected List<Order> getList() {
        if(App.user.userstate==0) {
            return new OrderDao(App.mIntance).queryAllMyCreateType(type);
        }else {
            return new OrderDao(App.mIntance).queryAllMyDoneType(type);
        }
        //return new ArrayList<>();
    }
    @Override
    public void initView() {
        super.initView();
        orderAdapter.setMyList(true);
    }
    public static MyOrderFragment getInstance(int type) {
        MyOrderFragment orderShopFragment=new MyOrderFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Ikeys.TYPE,type);
        orderShopFragment.setArguments(bundle);
        return orderShopFragment;
    }
}
