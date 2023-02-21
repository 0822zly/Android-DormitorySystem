package com.cnki.paotui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.cnki.paotui.databinding.ActivityAddTralltionBinding;
import com.cnki.paotui.db.MyTravell;
import com.cnki.paotui.map.DrivingRouteOverlay;
import com.cnki.paotui.utils.AMapUtil;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.cnki.paotui.utils.ToastUtil;

import java.util.Calendar;

public class AddTralltion extends BaseActivity{

    private com.cnki.paotui.databinding.ActivityAddTralltionBinding viewBinding;
    ActivityResultLauncher<Intent> activityResultLaucherFrom = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                Intent data = result.getData();
                MyMark myMark = (MyMark) data.getSerializableExtra(MyMark.class.getSimpleName());
                viewBinding.tvFrom.setText(myMark.getTitle());
                viewBinding.tvFrom.setTag(myMark);
                refreshMap();
            }
        }
    });
    ActivityResultLauncher<Intent> activityResultLaucherTo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                Intent data = result.getData();
                MyMark myMark = (MyMark) data.getSerializableExtra(MyMark.class.getSimpleName());
                viewBinding.tvTo.setText(myMark.getTitle());
                viewBinding.tvTo.setTag(myMark);
                refreshMap();
            }
        }
    });
    ActivityResultLauncher<Intent> activityResultLaucherHotal = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                Intent data = result.getData();
                MyMark myMark = (MyMark) data.getSerializableExtra(MyMark.class.getSimpleName());
                viewBinding.tvHotal.setText(myMark.getTitle());
                viewBinding.tvHotal.setTag(myMark);
            }
        }
    });

    MyTravell myTravell;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tralltion);
        myTravell= (MyTravell) getIntent().getSerializableExtra(MyTravell.class.getSimpleName());
        viewBinding = ActivityAddTralltionBinding.inflate(getLayoutInflater());
          setContentView(viewBinding.getRoot());

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        viewBinding.btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(viewBinding.tvFrom.getText().toString())||TextUtils.isEmpty(viewBinding.tvTo.getText().toString()) ||TextUtils.isEmpty(viewBinding.tvHotal.getText().toString())||TextUtils.isEmpty(viewBinding.tvEnddata.getText().toString())){
                    Toast.makeText(mContext, "不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    MyMark myMarkTo = (MyMark) viewBinding.tvTo.getTag();
                    MyMark myMarkFrom = (MyMark) viewBinding.tvFrom.getTag();

                    MyTravell myTravell = new MyTravell();
                    myTravell.setStarttime(viewBinding.tvEnddata.getText().toString());
                    myTravell.setFromlocation(viewBinding.tvFrom.getText().toString());
                    myTravell.setTolocation(viewBinding.tvTo.getText().toString());
                    myTravell.setHotal(viewBinding.tvHotal.getText().toString());
                    myTravell.setFromlat(myMarkFrom.getLat()+"");
                    myTravell.setFromlon(myMarkFrom.getLng()+"");
                    myTravell.setTolat(myMarkTo.getLat()+"");
                    myTravell.setTolon(myMarkTo.getLng()+"");
                    ThreadPoolExecutorUtil.doTask(new Runnable() {


                        @Override
                        public void run() {
                            JDBC.getInstance().insertMyTrall(myTravell);
                            finish();
                        }
                    });
                }
            }
        });
        refreshView();
    }
    private void refreshView() {
        if(myTravell==null) {
            viewBinding.lineEnddata.setOnClickListener(v -> {
                showDataPicker();
            });
            viewBinding.lineFrom.setOnClickListener(v -> {
                activityResultLaucherFrom.launch(new Intent(AddTralltion.this, PoiActivity.class));
            });
            viewBinding.lineTo.setOnClickListener(v -> {
                activityResultLaucherTo.launch(new Intent(AddTralltion.this, PoiActivity.class));
            });
            viewBinding.lineHotal.setOnClickListener(v -> {
                Intent i = new Intent(AddTralltion.this, PoiSearchActivity.class);
                if (viewBinding.tvTo.getTag() == null) {
                    Toast.makeText(mContext, "请先设置终点", Toast.LENGTH_SHORT).show();
                } else {
                    MyMark myMark = (MyMark) viewBinding.tvTo.getTag();

                    i.putExtra("city", myMark.getCityCode());
                    i.putExtra("latitude", myMark.getLat());
                    i.putExtra("longitude", myMark.getLng());
                    activityResultLaucherHotal.launch(i);
                }
            });
        }else {
            viewBinding.tvEnddata.setText(myTravell.getStarttime());
            viewBinding.tvFrom.setText(myTravell.getFromlocation());
            viewBinding.tvTo.setText(myTravell.getTolocation());
            viewBinding.tvHotal.setText(myTravell.getHotal());
            MyMark myMarkfrom=new MyMark();
            myMarkfrom.setLat(myTravell.getFromlat());
            myMarkfrom.setLng(myTravell.getFromlon());
            MyMark myMarkTo=new MyMark();
            myMarkTo.setLat(myTravell.getTolat());
            myMarkTo.setLng(myTravell.getTolon());
            viewBinding.tvFrom.setTag(myMarkfrom);
            viewBinding.tvTo.setTag(myMarkTo);
            viewBinding.btFinish.setVisibility(View.GONE);
            refreshMap();
        }
    }
    private DriveRouteResult mDriveRouteResult;

    private void refreshMap() {
        MyMark myMarkTo = (MyMark) viewBinding.tvTo.getTag();
        MyMark myMarkFrom = (MyMark) viewBinding.tvFrom.getTag();
        if(myMarkTo!= null && myMarkFrom!= null){
            try {
                RouteSearch   mRouteSearch = new RouteSearch(this);
                mRouteSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
                    @Override
                    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

                    }

                    @Override
                    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
                     //   dissmissProgressDialog();
                        aMap.clear();// 清理地图上的所有覆盖物
                        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
                            if (result != null && result.getPaths() != null) {
                                if (result.getPaths().size() > 0) {
                                    mDriveRouteResult = result;
                                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                                            .get(0);
                                    if(drivePath == null) {
                                        return;
                                    }
                                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                                            mContext, aMap, drivePath,
                                            mDriveRouteResult.getStartPos(),
                                            mDriveRouteResult.getTargetPos(), null);
                                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                                    drivingRouteOverlay.removeFromMap();
                                    drivingRouteOverlay.addToMap();
                                    drivingRouteOverlay.zoomToSpan();
                                 //   mBottomLayout.setVisibility(View.VISIBLE);
                                    int dis = (int) drivePath.getDistance();
                                    int dur = (int) drivePath.getDuration();
                                    String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
                                    viewBinding.tvDes.setText("驾车："+des);



                                } else if (result != null && result.getPaths() == null) {
                                    ToastUtil.show(mContext, R.string.no_result);
                                }

                            } else {
                                ToastUtil.show(mContext, R.string.no_result);
                            }
                        } else {
                            ToastUtil.showerror(AddTralltion.this, errorCode);
                        }
                    }

                    @Override
                    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

                    }

                    @Override
                    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

                    }
                });
                final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                        new LatLonPoint(Double.parseDouble(myMarkFrom.getLat()),Double.parseDouble(myMarkFrom.getLng())),  new LatLonPoint(Double.parseDouble(myMarkTo.getLat()),Double.parseDouble(myMarkTo.getLng())));

                RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, 2, null,
                        null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
            } catch (AMapException e) {
                e.printStackTrace();
            }


        }


    }
    MapView mapView;
    AMap aMap;
    /**
     * 展示日期控件
     */
    private void showDataPicker(){


        //获取日历的一个实例，里面包含了当前的年月日
        Calendar calendar=Calendar.getInstance();
        //构建一个日期对话框，该对话框已经集成了日期选择器
        //DatePickerDialog的第二个构造参数指定了日期监听器
        DatePickerDialog dialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                // String desc=String.format("您选择的日期是%d年%d月%d日",dp_date.getYear(),dp_date.getMonth()+1,dp_date.getDayOfMonth());
                viewBinding.tvEnddata.setText(i+"-"+(i1+1)+"-"+i2);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //把日期对话框显示在界面上
        dialog.show();


    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
}
