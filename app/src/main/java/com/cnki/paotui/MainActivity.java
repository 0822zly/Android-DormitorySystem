package com.cnki.paotui;

import android.os.Bundle;
import android.view.MenuItem;

import com.cnki.paotui.db.Travell;
import com.cnki.paotui.ui.dashboard.DashboardFragment;
import com.cnki.paotui.ui.home.HomeFragment;
import com.cnki.paotui.ui.notifications.NotificationsFragment;
import com.cnki.paotui.ui.person.PersonFragment;
import com.cnki.paotui.utils.JDBC;
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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
new Thread(new Runnable() {
    @Override
    public void run() {
      //  preJson();
    }
}).start();
    }
    private void hideAll(){
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        for (Map.Entry<Integer, Fragment> entry : mapFrag.entrySet()) {
           // String mapKey = entry.getKey();
            Fragment mapValue = entry.getValue();
            beginTransaction.hide(mapValue);
        }
        beginTransaction.commitAllowingStateLoss();
    }
    private void preJson(){

        List<Travell> persons =new Gson().fromJson(json, new TypeToken<List<Travell>>() {}.getType());
        JDBC.getInstance().insertAllTrall(persons);
    }
    String json="[{\"id\":3934646,\"title\":\"京城记\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z80c180000013yew14864_C_180_180.jpg\",\"name\":\"小糖芽\"},\"tagList\":[3,5],\"content\":\"打算趁着学生证没过期门票可以半价，并且可以错开旅游高峰，于是决定一个人去了 北京 。\",\"pictureNumber\":\"95\",\"commentNumber\":\"9\",\"viewNumber\":\"7733\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/100p1d000001eg1wl1639_D_230_160.jpg\",\"jumpUrl\":\"http://you.ctrip.com/travels/PekinTownship1676392/3934646.html\"},{\"id\":3966214,\"title\":\"北京攻略~探寻生活与艺术—朝圣帝都与发现古城之美\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/headphoto/123/775/437/ba11543d381d471995eecb535ed3cdd1_C_180_180.jpg\",\"name\":\"M67****51\"},\"tagList\":[3,5],\"content\":\"　　说起 北京 ，我脑子里浮现的第一个画面是相声，操着顺溜京腔自我调侃的长衫老爷们儿\",\"pictureNumber\":\"66\",\"commentNumber\":\"17\",\"viewNumber\":\"8250\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/0104h1200081t4ulk03D0_D_230_160.png\",\"jumpUrl\":\"http://you.ctrip.com/travels/Beijing1/3966214.html\"},{\"id\":3905233,\"title\":\"跟着祖国母亲生日之际去北京爬长城赏红叶\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z8080z000000n8y2h2A73_C_180_180.jpg\",\"name\":\"亚亚头\"},\"tagList\":[1,5],\"content\":\"（添加不了王菲的我和我的祖国.MP3，请自行脑补）前言从小就喜欢凑热闹的体质，到长大\",\"pictureNumber\":\"111\",\"commentNumber\":\"18\",\"viewNumber\":\"133577\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/100k1900000167va22FFE_D_230_160.jpg\",\"jumpUrl\":\"http://you.ctrip.com/travels/gubeishuizhen1446421/3905233.html\"},{\"id\":3908171,\"title\":\"雾里看叶别样美，房山休闲游，赏北京最早红叶\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z80h15000000xty2wD298_C_180_180.jpg\",\"name\":\"刘运泽LauRanger\"},\"tagList\":[3,5],\"content\":\"夜雨邈邈，金风细碎，惹梧桐叶叶纷黄，流云起。时节刚巧，秋霜在月下布满了山谷，清晨时已\",\"pictureNumber\":\"51\",\"commentNumber\":\"16\",\"viewNumber\":\"7548\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/100h190000017cmpf71C0_D_230_160.jpg\",\"jumpUrl\":\"http://you.ctrip.com/travels/FangshanDistrict143882/3908171.html\"},{\"id\":3904733,\"title\":\"[北京]我和北京的首次相遇，恰逢国庆70周年大阅兵（内含详细游玩攻略）\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z8040a0000004eri128B5_C_180_180.jpg\",\"name\":\"小猴子游戏人间\"},\"tagList\":[3,5],\"content\":\"如果说心中的遗憾有什么，就是要未曾到过北京。电视里常说的北漂，体现现代城市的繁华与繁\",\"pictureNumber\":\"101\",\"commentNumber\":\"18\",\"viewNumber\":\"8109\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/100l19000001608884229_D_230_160.jpg\",\"jumpUrl\":\"http://you.ctrip.com/travels/Beijing1/3904733.html\"},{\"id\":3894361,\"title\":\"北京各个景点详细游攻略，住一家南锣鼓巷里面的精品设计师风格酒店\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z80d0e00000077gwfA87D_C_180_180.jpg\",\"name\":\"爱旅行的小燕子\"},\"tagList\":[3,5],\"content\":\"北京天气：晴和谁一起：朋友住宿：玉京南锣酒店点评：就在南锣鼓巷，地理位置优越，外出走\",\"pictureNumber\":\"58\",\"commentNumber\":\"5\",\"viewNumber\":\"10964\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/100f180000013qn0g1185_D_230_160.jpg\",\"jumpUrl\":\"http://you.ctrip.com/travels/Beijing1/3894361.html\"},{\"id\":3902985,\"title\":\"清风有信，秋美无边｜古北水镇绽放一场绚丽的“花火”\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z80d0g0000007zlyc758C_C_180_180.jpg\",\"name\":\"背包客的笔记\"},\"tagList\":[1,5],\"content\":\"水镇江南 好风景旧曾谙日出江花红胜火春来江水绿如蓝能不忆 江南 ?诗王白居易的这首诗\",\"pictureNumber\":\"72\",\"commentNumber\":\"15\",\"viewNumber\":\"123610\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/100j190000015nhwa8744_D_230_160.jpg\",\"jumpUrl\":\"http://you.ctrip.com/travels/gubeishuizhen1446421/3902985.html\"},{\"id\":3904217,\"title\":\"北京3日游 深秋红叶季，小镇风光美\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z8050h0000008rdwn6EA3_C_180_180.jpg\",\"name\":\"雄鹰王浚\"},\"tagList\":[1,5],\"content\":\"说说这次旅行金秋十月，天高云淡，距离北京最佳赏红叶的时候，也越来越近了。期待了整整一\",\"pictureNumber\":\"79\",\"commentNumber\":\"15\",\"viewNumber\":\"40451\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/100s1900000164oviF30A_D_230_160.jpg\",\"jumpUrl\":\"http://you.ctrip.com/travels/gubeishuizhen1446421/3904217.html\"},{\"id\":3966182,\"title\":\"在胡同感受最东城，在东城看见最北京\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z80s0x000000l0v2oC8BB_C_180_180.jpg\",\"name\":\"恩铭行摄记\"},\"tagList\":[3,5],\"content\":\"悦耳动听的京片子，喝不惯的豆汁儿，密密麻麻的胡同，还有胡同墙内幽静的四合院儿....\",\"pictureNumber\":\"68\",\"commentNumber\":\"17\",\"viewNumber\":\"8354\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/0101t1200081uezg3D4C8_D_230_160.jpg\",\"jumpUrl\":\"http://you.ctrip.com/travels/Beijing1/3966182.html\"},{\"id\":3924837,\"title\":\"迷之帝都——千年古都的磅礴还有小井胡同的静雅\",\"author\":{\"avatar\":\"https://dimg04.c-ctrip.com/images/Z80a0x000000lgpbq4AE2_C_180_180.jpg\",\"name\":\"唯独是你56\"},\"tagList\":[2,5],\"content\":\"　　你总是把梦想留在未来 旅行留在下次 想做的事情留在以后 然后在本该是未来的那个时\",\"pictureNumber\":\"158\",\"commentNumber\":\"21\",\"viewNumber\":\"8954\",\"imageUrl\":\"https://youimg1.c-ctrip.com/target/100c1b000001bd26w13D1_D_230_160.png\",\"jumpUrl\":\"http://you.ctrip.com/travels/Beijing1/3924837.html\"}]";
}