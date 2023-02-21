package com.cnki.paotui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cnki.paotui.ui.login.LoginActivity;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity  extends BaseActivity{

    private TextView tv_comein;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        ImmersionBar.with(this).transparentBar().init();

        ViewPager viewPager=findViewById(R.id.viewpager);
         List<View> mViewList=new ArrayList<>();
        ImageView imageView=new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.splah1));
         mViewList.add(imageView);
        ImageView imageView1=new ImageView(mContext);
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView1.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.splah2));
        mViewList.add(imageView1);
        ImageView imageView2=new ImageView(mContext);
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView2.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.splah3));
        mViewList.add(imageView2);
        viewPager.setAdapter(new AdapterViewpager(mViewList));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==2){
                    tv_comein.setVisibility(View.VISIBLE);
                }else {
                    tv_comein.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    tv_comein.setVisibility(View.VISIBLE);
                }else {
                    tv_comein.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_comein = findViewById(R.id.tv_comein);
        tv_comein.setVisibility(View.GONE);
        tv_comein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent=new Intent(mContext, LoginActivity.class);
                      mContext.startActivity(intent);
                      finish();
            }
        });

    }
    public class AdapterViewpager extends PagerAdapter {
        private List<View> mViewList;

        public AdapterViewpager(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {//必须实现
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {//必须实现
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
            container.removeView(mViewList.get(position));
        }
    }
}
