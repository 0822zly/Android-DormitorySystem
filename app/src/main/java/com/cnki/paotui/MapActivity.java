package com.cnki.paotui;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItemV2;
import com.amap.api.services.poisearch.PoiResultV2;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearchV2;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.BusRouteResultV2;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveRouteResultV2;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RideRouteResultV2;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearchV2;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkRouteResultV2;

import java.util.ArrayList;
import java.util.List;

public class MapActivity  extends BaseActivity {

    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (mapView != null) {
            aMap = mapView.getMap();

            dingweilandian();
          //  show3D();
         //   show室内();
         //   改变地图模式();
         //   show工具按钮();
          ///  show手势();
            //改变中心点();
         //   改变显示区域(savedInstanceState);
        //    增加标点();
        //    绘制海量标点();
        }

    findViewById(R.id.sousuopoi).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                POI搜索();
            } catch (AMapException e) {
                e.printStackTrace();
            }
        }
    });
        findViewById(R.id.jiacheluxian).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                显示驾车路线();
            } catch (AMapException e) {
                e.printStackTrace();
            }
        }
    });
    }
    MyLocationStyle myLocationStyle;
    /**
     * 定位蓝点
     */
    private void dingweilandian() {

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);//是否显示蓝点
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                System.out.println(location.getLatitude()+","+location.getLongitude());
            }
        });

//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
////以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
    }

    /**
     * 显示3D
     */
    private void show3D(){
        MapsInitializer.setTerrainEnable(true);
    }

    /**
     * 显示室内地图
     */
    private void show室内(){
        aMap.showIndoorMap(true);
    }

    /**
     * 切换地图模式
     */
    private void 改变地图模式( ){
        findViewById(R.id.daohangditu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                aMap.setMapType(AMap.MAP_TYPE_NAVI);// 设置卫星地图模式，aMap是地图控制器对象。
            }
        });
        findViewById(R.id.yejingditu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);// 设置卫星地图模式，aMap是地图控制器对象。
            }
        });
        findViewById(R.id.baitianditu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 设置卫星地图模式，aMap是地图控制器对象。
            }
        });
        findViewById(R.id.weixingtu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式，aMap是地图控制器对象。
            }
        });
    }
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private void show工具按钮(){
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(true);//显示缩放
        mUiSettings.setZoomPosition(AMapOptions.LOGO_MARGIN_BOTTOM

        );//显示缩放位置
        mUiSettings.setCompassEnabled(true);//显示指南针
        mUiSettings.setScaleControlsEnabled(true);//显示比例尺子
        //显示定位按钮  ---------------------有问题
//        aMap.setLocationSource(new LocationSource() {
//            @Override
//            public void activate(OnLocationChangedListener onLocationChangedListener) {
//
//            }
//
//            @Override
//            public void deactivate() {
//
//            }
//        });//通过aMap对象设置定位数据源的监听

      //  mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮

      //  aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
    }
    private void show手势(){
        mUiSettings.setZoomGesturesEnabled(true);//放大缩小
        mUiSettings.setScrollGesturesEnabled(true);//滑动手势
        mUiSettings.setRotateGesturesEnabled(true);//旋转手势
        mUiSettings.setTiltGesturesEnabled(true);//倾斜手势
        aMap.setPointToCenter(200,200);//指定屏幕中心点的手势操作 指定屏幕中心
        mUiSettings.setGestureScaleByMapCenter(true);//开启以屏幕中心的手势操作
    }
    private void 改变中心点(){
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(40.977290,116.337000),18,30,0));
        aMap.animateCamera(mCameraUpdate);
    }
    private void 改变显示区域( Bundle savedInstanceState){
        // 定义北京市经纬度坐标（此处以北京坐标为例）
        LatLng centerBJPoint= new LatLng(119,116.405285);
// 定义了一个配置 AMap 对象的参数类
        AMapOptions mapOptions = new AMapOptions();
// 设置了一个可视范围的初始化位置
// CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
// CameraPosition 第二个参数： 目标可视区域的缩放级别
// CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
// CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
        mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
// 定义一个 MapView 对象，构造方法中传入 mapOptions 参数类
        MapView mapView = new MapView(this, mapOptions);
// 调用 onCreate方法 对 MapView LayoutParams 设置
        mapView.onCreate(savedInstanceState);
    }
    private void 增加标点(){
        LatLng latLng = new LatLng(39.906901,116.397972);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));//增加标点
        //加入动画
        Animation animation = new RotateAnimation(marker.getRotateAngle(),marker.getRotateAngle()+180,0,0,0);
        long duration = 1000L;
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());
        marker.setAnimation(animation);
        marker.startAnimation();
        //标点点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //自定义图标标点
        LatLng latLng1 = new LatLng(34.341568,108.940174);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng1);
        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.mipmap.icon_home)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        aMap.addMarker(markerOption);
    }

    /**
     * 记录跑步场景
     */
    private void 设置运动轨迹(){
        // 获取轨迹坐标点
        List<LatLng> points = new ArrayList<>();
        LatLngBounds bounds = new LatLngBounds(points.get(0), points.get(points.size() - 2));
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        SmoothMoveMarker smoothMarker = new SmoothMoveMarker(aMap);
// 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.icon));

        LatLng drivePoint = points.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());

// 设置滑动的轨迹左边点
        smoothMarker.setPoints(subList);
// 设置滑动的总时间
        smoothMarker.setTotalDuration(40);
// 开始滑动
        smoothMarker.startSmoothMove();

    }
    private void 绘制海量标点(){
        //第 1 步 设置海量点属性
        MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
        overlayOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon));//设置图标
        overlayOptions.anchor(0.5f,0.5f); //设置锚点
        //第 1 步 添加海量点获取管理对象
        MultiPointOverlay multiPointOverlay = aMap.addMultiPointOverlay(overlayOptions);

        //第 1 步 读取数据并通过海量点管理对象设置
        List<MultiPointItem> list = new ArrayList<MultiPointItem>();
        for (int i = 0; i <10 ; i++) {
            MultiPointItem multiPointItem = new MultiPointItem(new LatLng(39.906901+i,116.39797+i));
            list.add(multiPointItem);
        }
        //将规范化的点集交给海量点管理对象设置，待加载完毕即可看到海量点信息
        multiPointOverlay.setItems(list);
        //定义海量点点击事件
        AMap.OnMultiPointClickListener multiPointClickListener = new AMap.OnMultiPointClickListener() {
            // 海量点中某一点被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onPointClick(MultiPointItem pointItem) {
                return false;
            }
        };
        // 绑定海量点点击事件
        aMap.setOnMultiPointClickListener(multiPointClickListener);
    }
    private void POI搜索() throws AMapException {
        PoiSearchV2.Query  query=new PoiSearchV2.Query("天坛", "110200", "010");
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        PoiSearchV2   poiSearch = new PoiSearchV2(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearchV2.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResultV2 poiResultV2, int i) {
                if(i==1000){
                    Toast.makeText(mContext, "搜索成功",     Toast.LENGTH_SHORT).show();
                    for (int j = 0; j <poiResultV2.getPois().size() ; j++) {
                        PoiItemV2 poiItemV2 = poiResultV2.getPois().get(j);
                        LatLonPoint latLonPoint = poiItemV2.getLatLonPoint();
                        LatLng latLng = new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
                        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(poiItemV2.getTitle()).snippet(poiItemV2.getSnippet()));//增加标点
                       aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                           @Override
                           public boolean onMarkerClick(Marker marker) {
                               try {
                                   aMap.clear();
                                   poi周边搜索(marker);
                               } catch (AMapException e) {
                                   e.printStackTrace();
                               }
                               return true;
                           }
                       }) ;
                    }

                }else {

                }
                //解析result获取POI信息
            }

            @Override
            public void onPoiItemSearched(PoiItemV2 poiItemV2, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }
    private void poi周边搜索(Marker locationMarker) throws AMapException {
        PoiSearchV2.Query  query=new PoiSearchV2.Query("酒店", "100000", "010");
        PoiSearchV2   poiSearch = new PoiSearchV2(this, query);
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        poiSearch.setBound(new PoiSearchV2.SearchBound(new LatLonPoint(locationMarker.getPosition().latitude,
                locationMarker.getPosition().longitude), 1000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(new PoiSearchV2.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResultV2 poiResultV2, int i) {
                if(i==1000){
                    Toast.makeText(mContext, "搜索成功"+poiResultV2.getPois().size(),     Toast.LENGTH_SHORT).show();
                    for (int j = 0; j <poiResultV2.getPois().size() ; j++) {
                        PoiItemV2 poiItemV2 = poiResultV2.getPois().get(j);
                        LatLonPoint latLonPoint = poiItemV2.getLatLonPoint();
                        LatLng latLng = new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
                        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(poiItemV2.getTitle()).snippet(poiItemV2.getSnippet()));//增加标点
                        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {

                                return false;
                            }
                        }) ;
                    }

                }else {

                }
                //解析result获取POI信息
            }

            @Override
            public void onPoiItemSearched(PoiItemV2 poiItemV2, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }
    private void 显示驾车路线() throws AMapException {
        RouteSearchV2 routeSearch = new RouteSearchV2(this);
        routeSearch.setRouteSearchListener(new RouteSearchV2.OnRouteSearchListener() {
            @Override
            public void onDriveRouteSearched(DriveRouteResultV2 driveRouteResultV2, int i) {

            }

            @Override
            public void onBusRouteSearched(BusRouteResultV2 busRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResultV2 walkRouteResultV2, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResultV2 rideRouteResultV2, int i) {

            }


        });
//        RouteSearchV2.FromAndTo fromAndTo=new RouteSearchV2.FromAndTo(new LatLonPoint(39.904967,116.));
//        RouteSearchV2.DriveRouteQuery query = new RouteSearchV2.DriveRouteQuery(fromAndTo, RouteSearchV2.DrivingStrategy.DEFAULT, null, null, "");
//        routeSearch.calculateDriveRouteAsyn(query);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }
}
