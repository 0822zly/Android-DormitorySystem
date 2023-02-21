package com.cnki.paotui.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cnki.paotui.AddTralltion;
import com.cnki.paotui.App;
import com.cnki.paotui.R;
import com.cnki.paotui.WebActivity1;
import com.cnki.paotui.adapter.MyTrallAdapter;
import com.cnki.paotui.db.MyTravell;
import com.cnki.paotui.db.Order;
import com.cnki.paotui.ui.fragment.OrderShopFragment;
import com.cnki.paotui.utils.JDBC;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {


    private View mRootView;
    Map<Integer,Fragment> map=new HashMap<>();
    List<MyTravell> titles = new ArrayList<>();
    Handler mHandler = new Handler(Looper.getMainLooper());
    private MyTrallAdapter myTrallAdapter;
    ActivityResultLauncher<String[]> GroupPermissions = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            if(result.get(android.Manifest.permission.ACCESS_COARSE_LOCATION)&&
                    result.get(android.Manifest.permission.ACCESS_FINE_LOCATION)&&
                    result.get(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)){

            }
        }
    });
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
       mRootView.findViewById(R.id.addtrall).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(App.user==null){
                    Toast.makeText(getActivity(), "请先登入", Toast.LENGTH_SHORT).show();
                }else {

               Intent intent = new Intent(getActivity(), AddTralltion.class);
               getActivity().startActivity(intent);
                }
           }
       });

       mRootView.findViewById(R.id.tv_addtrall).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(App.user==null){
                   Toast.makeText(getActivity(), "请先登入", Toast.LENGTH_SHORT).show();
               }else {

                   Intent intent = new Intent(getActivity(), AddTralltion.class);
                   getActivity().startActivity(intent);
               }
           }
       });
       mRootView.findViewById(R.id.jingdian).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(getContext(), WebActivity1.class);
               intent.putExtra("url","https://www.ctrip.com/");
               intent.putExtra("title","酒店");
               getContext().startActivity(intent);
           }
       });
       mRootView.findViewById(R.id.jingqu).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(getContext(), WebActivity1.class);
               intent.putExtra("url","https://m.ctrip.com/webapp/you/gspoi/sight/2.html/");
               intent.putExtra("title","景区");
               getContext().startActivity(intent);
           }
       });
       mRootView.findViewById(R.id.jiaotong).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(getContext(), WebActivity1.class);
               intent.putExtra("url","https://trains.ctrip.com/");
               intent.putExtra("title","交通");
               getContext().startActivity(intent);
           }
       });
        RecyclerView recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        myTrallAdapter = new MyTrallAdapter(R.layout.item_mytrall);
        recyclerView.setAdapter(myTrallAdapter);
        return mRootView;
    }

    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                titles.clear();
                titles= JDBC.getInstance().queryAllMyTrall();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        myTrallAdapter.setNewInstance(titles);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(App.user!=null) {
            getData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}