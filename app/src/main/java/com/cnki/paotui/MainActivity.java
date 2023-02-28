package com.cnki.paotui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import com.cnki.paotui.db.Travell;
import com.cnki.paotui.ui.dashboard.DashboardFragment;
import com.cnki.paotui.ui.home.HomeFragment;
import com.cnki.paotui.ui.person.PersonFragment;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.SPUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
//主界面
public class MainActivity extends AppCompatActivity {


    private FragmentManager supportFragmentManager;
    Map<Integer, Fragment> mapFrag=new HashMap<>();
    private BottomNavigationView navView;

    public void select(int index){
        navView.setSelectedItemId(navView.getMenu().getItem(index).getItemId());

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        supportFragmentManager = getSupportFragmentManager();
        getSupportActionBar().hide();
        navView = findViewById(R.id.nav_view);
        HomeFragment home= new HomeFragment();
        mapFrag.put(0,home);

        supportFragmentManager.beginTransaction().add(R.id.framelayout,home).commitAllowingStateLoss();
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                hideAll();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                switch (item.getItemId())
                {
                   case R.id.navigation_home:
                        if(mapFrag.get(0)==null){
                            HomeFragment home= new HomeFragment();
                            mapFrag.put(0,home);
                            fragmentTransaction.add(R.id.framelayout,home);
                        }else{
                            fragmentTransaction.show(mapFrag.get(0));
                        }
                    fragmentTransaction.commitAllowingStateLoss();
                        return true;

                    case   R.id.navigation_dashboard :
                        if(mapFrag.get(1)==null){
                            DashboardFragment home= new DashboardFragment();
                            mapFrag.put(1,home);
                            fragmentTransaction.add(R.id.framelayout,home);
                        }else{
                            fragmentTransaction.show(mapFrag.get(1));
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        return true;
                    case   R.id.navigation_person :
                        if(mapFrag.get(2)==null){
                            PersonFragment home= new PersonFragment();
                            mapFrag.put(2,home);
                            fragmentTransaction.add(R.id.framelayout,home);
                        }else{
                            fragmentTransaction.show(mapFrag.get(2));
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        return true;
                }
                return  false;
            }
        });
   //初始化阅读配置
new Thread(new Runnable() {
    @Override
    public void run() {
     if(TextUtils.isEmpty(SPUtil.getInstance().getString("textcolor"))){
         SPUtil.getInstance().setValue("textcolor","#C8E4CB");
     }
        if(SPUtil.getInstance().getInt("textsize")==0){
            SPUtil.getInstance().setValue("textsize",14);
        }
    }
}).start();
    }
    //隐藏所有fragment
    private void hideAll(){
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        for (Map.Entry<Integer, Fragment> entry : mapFrag.entrySet()) {
           // String mapKey = entry.getKey();
            Fragment mapValue = entry.getValue();
            beginTransaction.hide(mapValue);
        }
        beginTransaction.commitAllowingStateLoss();
    }

}