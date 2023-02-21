package com.cnki.paotui.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.cnki.paotui.R;
import com.cnki.paotui.ui.fragment.MyOrderFragment;
import com.cnki.paotui.ui.fragment.OrderShopFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private View mRootView;
    Map<Integer,Fragment> map=new HashMap<>();
    List<String> titles = new ArrayList<>();
    //List<Order> list=new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        titles.add("全部");
        titles.add("跑腿");
        titles.add("学业指导");
        titles.add("竞赛组队");
        map.put(0, MyOrderFragment.getInstance(-1));
        map.put(1, MyOrderFragment.getInstance(0));
        map.put(2, MyOrderFragment.getInstance(1));
        map.put(3, MyOrderFragment.getInstance(2));
        TabLayout tabLayout=  mRootView.findViewById(R.id.tablayout1);
        ViewPager viewPager=mRootView.findViewById(R.id.viewpager1);
        viewPager.setOffscreenPageLimit(map.size());
        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return map.get(position);
            }

            @Override
            public int getCount() {
                return map.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}