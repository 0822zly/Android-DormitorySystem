package com.cnki.paotui.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.cnki.paotui.Add1Activity;
import com.cnki.paotui.Add2Activity;
import com.cnki.paotui.AddActivity;
import com.cnki.paotui.Ikeys;
import com.cnki.paotui.R;
import com.cnki.paotui.adapter.JingdianAdapter;
import com.cnki.paotui.adapter.LvYouAdapter;
import com.cnki.paotui.db.Travell;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.Jingdian;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View mRootView;
    private Banner banner;
    private TabLayout tablayout;
    RecyclerView recyclerView;
    Handler handler=new Handler();
    private LvYouAdapter lvYouAdapter;
    private JingdianAdapter jingdianAdapter;
    List<Jingdian> listJing=new ArrayList<>();
    {
        Jingdian 中粮祥云小镇 = new Jingdian("中粮祥云小镇", "https://dimg01.c-ctrip.com/images/0105j120008hwu205C634_C_220_150.png", "4.7", "https://m.ctrip.com/webapp/you/gspoi/sight/1/1455236.html?seo=0&isHideNavBar=YES&from=https%3A%2F%2Fgs.ctrip.com%2Fhtml5%2Fyou%2Fplace%2F1.html");
        中粮祥云小镇.setAddress("北京顺义区安泰大街九号院");
        中粮祥云小镇.setContent("中粮祥云小镇位于顺义区，集餐饮、购物、娱乐于一体的综合性街区。全家一起来这里玩玩转转，有孩子们喜欢的喷泉，各种品牌的专卖店，餐厅的选择也很多，还有影城可以去看看最新的电影。来这里转一天也挺充实呢。");
        中粮祥云小镇.setOpentime("10:00-21:00开放");
        中粮祥云小镇.setImageurl1("https://dimg08.c-ctrip.com/images/0100i120009xbrjhsCA20_D_180_180.jpg?proc=autoorient");
        中粮祥云小镇.setImageurl2("https://dimg05.c-ctrip.com/images/0103i120009xbqg733F15_D_180_180.jpg?proc=autoorient");
        listJing.add(中粮祥云小镇);
        Jingdian 故宫博物馆 = new Jingdian("故宫博物馆", "https://youimg1.c-ctrip.com/target/0100j1200046x94ebB936_D_350_350.jpg", "4.8", "https://m.ctrip.com/webapp/you/gspoi/sight/1/229.html?seo=0&isHideNavBar=YES&from=https%3A%2F%2Fgs.ctrip.com%2Fhtml5%2Fyou%2Fplace%2F1.html");
        故宫博物馆.setAddress("北京市东城区景山前街4号");
        故宫博物馆.setContent("金黄色的琉璃瓦下，是一排朱红的围墙，装点着它的崇高与奢华；24位皇帝留下了不被时候抹去的陈迹，记录着它每个历史阶段的高雅刹时；宫殿上方滚滚滚动的云彩，彰明显它的时候之轮飞快运永连续歇；从雍正到宣统，从古代到当下，讲述着它历史的长久和波折。是谁创设了历史?又是谁在历史中创设了宏年夜的文明?故宫便是见证！见证着中华平易近族良久历史中不朽的传奇");
        故宫博物馆.setOpentime("8:30-16:30开放");
        故宫博物馆.setImageurl1("https://dimg04.c-ctrip.com/images/1mh6p12000al4b54mF5C4_D_180_180.jpg?proc=autoorient");
        故宫博物馆.setImageurl2("https://dimg08.c-ctrip.com/images/03010120009w45ixd826D_D_180_180.jpg?proc=autoorient");
        listJing.add(故宫博物馆);
        Jingdian 颐和园 = new Jingdian("颐和园", "https://youimg1.c-ctrip.com/target/100l180000014evwv9F6B_D_350_350.jpg", "4.7", "https://m.ctrip.com/webapp/you/gspoi/sight/1/1455236.html?seo=0&isHideNavBar=YES&from=https%3A%2F%2Fgs.ctrip.com%2Fhtml5%2Fyou%2Fplace%2F1.html");
        颐和园.setAddress("北京市海淀区新建宫门路19号");
        颐和园.setContent("这里的湖光山色百看不厌。东宫门前有一个巨大的影壁，影壁外面还有慈福牌楼。东宫门进来，穿过正殿就是昆明湖东岸了，知春亭周边最美赏景点，昆明湖、西山、万寿山尽收眼底。");
        颐和园.setOpentime("6:30-19:00开放");
        颐和园.setImageurl1("https://dimg06.c-ctrip.com/images/0102e12000a0rpic4CBA3_D_180_180.jpg?proc=autoorient");
        颐和园.setImageurl2("https://dimg02.c-ctrip.com/images/0104p12000ablttbh97D8_D_180_180.jpg?proc=autoorient");
        listJing.add(颐和园);
        Jingdian 窦店清真寺 = new Jingdian("窦店清真寺", "https://dimg05.c-ctrip.com/images/0104i120009l4tjhi514B_C_220_150.png", "4.7", "https://m.ctrip.com/webapp/you/gspoi/sight/1/15465404.html?seo=0&isHideNavBar=YES&from=https%3A%2F%2Fgs.ctrip.com%2Fhtml5%2Fyou%2Fplace%2F1.html");
        listJing.add(窦店清真寺);
        Jingdian 上海迪士尼度假区 = new Jingdian("上海迪士尼度假区", "https://dimg01.c-ctrip.com/images/0105j120008hwu205C634_C_220_150.png", "4.7", "https://you.ctrip.com/sight/shanghai2/1412255.html");
        上海迪士尼度假区.setAddress("上海市浦东新区川沙新镇黄赵路310号");
        上海迪士尼度假区.setContent("小朋友流连忘返的童话世界，这里有可爱萌萌哒的萌友天团，有诱惑难抵的美食诱惑，有适合娃娃玩的游乐设施，还有最爱的公主王子互动体验，值得大朋友和小朋友们游玩的好去处");
        上海迪士尼度假区.setOpentime("08:30-21:30开放");
        上海迪士尼度假区.setImageurl1("https://dimg08.c-ctrip.com/images/0104q120009z041q8285A_D_180_180.jpg?proc=autoorient");
        上海迪士尼度假区.setImageurl2("https://dimg08.c-ctrip.com/images/01071120009z0277x1D2A_D_180_180.jpg?proc=autoorient");
        listJing.add(上海迪士尼度假区);
        Jingdian 东方明珠 = new Jingdian("东方明珠", "https://dimg04.c-ctrip.com/images/010691200097uy8rk36FE_R_1600_10000.jpg", "4.7", "https://you.ctrip.com/sight/shanghai2/762.html");
        东方明珠.setAddress("上海市浦东新区陆家嘴世纪大道1号");
        东方明珠.setContent("东方明珠矗立于上海浦东陆家嘴，与外滩隔江相望，是上海的标志性建筑。乘坐全透明观光电梯登上351米高的太空舱观光层，可将浦江两岸风光一览无余，还可以让人们与“宇航员”来一次互动。若在267米高的空中旋转餐厅一边品尝美食一边欣赏申城夜景，更是超凡的体验。");
        东方明珠.setOpentime("09:00-21:00开放");
        东方明珠.setImageurl1("https://dimg07.c-ctrip.com/images/0306k12000abdbuiv2BE6_D_180_180.jpg?proc=autoorient");
        东方明珠.setImageurl2("https://dimg04.c-ctrip.com/images/0303012000abdcn6c183A_D_180_180.jpg?proc=autoorient");
        listJing.add(东方明珠);
        Jingdian 外滩 = new Jingdian("外滩", "https://dimg04.c-ctrip.com/images/0102p1200082jbvtcD451_R_1600_10000.jpg", "4.8", "https://you.ctrip.com/sight/shanghai2/736.html");
        外滩.setAddress("上海市黄浦区中山东一路（临黄浦江）");
        外滩.setContent("外滩位于黄浦江畔，全长约1.5公里，是上海城市象征意义的景点，风格迥异的万国建筑群和浦江夜景是它的精华所在。外滩矗立着几十幢风格迥异的古典复兴大楼，知名的中国银行大楼、和平饭店、海关大楼、汇丰银行大楼等再现了昔日“远东华尔街”的风采。令人惊讶的是，这些建筑并非出自同一位设计师，也不是建于同一时期，却拥有一种独特的和谐美。外滩多种建筑风格，其中包括法国古典式、法国大住宅式、哥特式等，这些都是中国近现代重要史迹及代表性建筑，也是黄浦江畔一道靓丽的风景线。");
        外滩.setOpentime("全天开放");
        外滩.setImageurl1("https://dimg01.c-ctrip.com/images/01046120009xbnmxm5025_D_180_180.jpg?proc=autoorient");
        外滩.setImageurl2("https://dimg01.c-ctrip.com/images/0103c120009xbprphF6F4_D_180_180.jpg?proc=autoorient");
        listJing.add(外滩);
        Jingdian 上海海昌海洋公园 = new Jingdian("上海海昌海洋公园", "https://dimg02.c-ctrip.com/images/01021120009ujhqzp72AA_R_1600_10000.jpg", "4.6", "https://you.ctrip.com/sight/shanghai2/4651499.html");
        上海海昌海洋公园.setAddress("上海市浦东新区南汇新城镇银飞路166号");
        上海海昌海洋公园.setContent("上海海昌海洋公园被评定为国家4A级旅游景区，以海洋文化为主题，缔造五大区域和一个度假酒店，拥有《虎鲸科普秀》《海象嘻游记》《海豚恋曲》等十六大明星剧目；设有南极企鹅馆、海兽探秘馆等六大动物展示场馆，提供火山漂流、海豚过山车等十余项游乐设施，汇聚三万余只海洋生物。");
        上海海昌海洋公园.setOpentime("6:30-19:00开放");
        上海海昌海洋公园.setImageurl1("https://dimg06.c-ctrip.com/images/0304z120009n0rv0tF831_D_180_180.jpg?proc=autoorient");
        上海海昌海洋公园.setImageurl2("https://dimg03.c-ctrip.com/images/0300g120009n0qu3p9143_D_180_180.jpg?proc=autoorient");
        listJing.add(上海海昌海洋公园);
        Jingdian 上海野生动物园 = new Jingdian("上海野生动物园", "https://dimg04.c-ctrip.com/images/0101f12000acizecb31EF_C_1600_1200.jpg", "4.6", "https://you.ctrip.com/sight/shanghai2/758.html");
        上海野生动物园.setAddress("上海市浦东新区南六公路178号");
        上海野生动物园.setContent("园区简介欢迎来到上海野生动物园，这里将唤起您心中对于大自然的美好向往与满满好奇，我们也将为您诠释一座人与动物和谐共处的美好家园。作为国内首批5A级旅游景区，一直以来，我们都在努力拉近人与自然之间的距离。园内步行区、车入区及“水域探秘”三大区域将为您从不同角度呈现国内外200余种，上万头（只）野生动物。");
        上海野生动物园.setOpentime("6:30-19:00开放");
        上海野生动物园.setImageurl1("https://dimg01.c-ctrip.com/images/0107412000a90sx3y0DE3_D_180_180.jpg?proc=autoorient");
        上海野生动物园.setImageurl2("https://dimg02.c-ctrip.com/images/0104h12000a90sow88CE0_D_180_180.jpg?proc=autoorient");
        listJing.add(上海野生动物园);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=mRootView.findViewById(R.id.listview);
        lvYouAdapter = new LvYouAdapter(R.layout.item_lvyougonglue);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(lvYouAdapter);
        jingdianAdapter=new JingdianAdapter(R.layout.item_jingdian);
        jingdianAdapter.setNewInstance(listJing);
        tablayout=mRootView.findViewById(R.id.tablayout);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("攻略")){
                    recyclerView.setAdapter(lvYouAdapter);
                }else {
                    recyclerView.setAdapter(jingdianAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        banner = mRootView.findViewById(R.id.banner);
        List<Integer> list=new ArrayList<>();
        list.add(R.mipmap.banner1);
        list.add(R.mipmap.banner2);
        list.add(R.mipmap.banner3);
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        //图片加载自己实现
//                        Glide.with(holder.itemView)
//                                .load(data)
//                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                                .into(holder.imageView);
                        holder.imageView.setImageDrawable(getActivity().getResources().getDrawable(data));
                    }
                })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getContext()));

        tablayout.addTab(tablayout.newTab().setText("攻略"));
        tablayout.addTab(tablayout.newTab().setText("景点"));

        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                JDBC jdbc=JDBC.getInstance();
                List<Travell> list1 = jdbc.queryAllTrall();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lvYouAdapter.setNewInstance(list1);
                    }
                });
            }
        });
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}