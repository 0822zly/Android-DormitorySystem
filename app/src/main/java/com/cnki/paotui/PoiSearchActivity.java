package com.cnki.paotui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.PoiItemV2;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiResultV2;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearchV2;
import com.cnki.paotui.map.PoiOverlay;
import com.cnki.paotui.utils.AMapUtil;
import com.cnki.paotui.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class PoiSearchActivity extends BaseActivity implements View.OnClickListener,OnMarkerClickListener,InfoWindowAdapter,
        OnPoiSearchListener {
    private MapView mapview;
    private AMap mAMap;

    private Marker detailMarker;
    private PoiSearch poiSearch;
    private PoiItem mPoi;

    private RelativeLayout mPoiDetail;
    private TextView mPoiName, mPoiAddress, mPoiInfo;
    private String ID = "";
    private EditText mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poiaroundsearch_activity);
        mapview = (MapView)findViewById(R.id.mapView);
        mapview.onCreate(savedInstanceState);
        init();
    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (mAMap == null) {
            mAMap = mapview.getMap();
            mAMap.setOnMarkerClickListener(this);
            TextView searchButton = (TextView) findViewById(R.id.btn_search);
            searchButton.setOnClickListener(this);
            mAMap.setInfoWindowAdapter(this);//AMap类中
        }
        setup();
    }
    LatLng latLng;
    private void setup() {
        latLng=new LatLng(Double.parseDouble(getIntent().getStringExtra("latitude")),Double.parseDouble(getIntent().getStringExtra("longitude")));
        mPoiDetail = (RelativeLayout) findViewById(R.id.poi_detail);
        mPoiName = (TextView) findViewById(R.id.poi_name);
        mPoiAddress = (TextView) findViewById(R.id.poi_address);
        mPoiInfo = (TextView)findViewById(R.id.poi_info);
        mSearchText = (EditText)findViewById(R.id.input_edittext);
        mSearchText.setText("酒店");// 北京大学
        mSearchText.setHint("请输入搜索ID");
        detailMarker = mAMap.addMarker(new MarkerOptions());
        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng
                        , 18, 0, 20)), null);
        mAMap.clear();
        mAMap.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }
    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        mAMap.moveCamera(update);
    }
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() throws AMapException {
        ID = mSearchText.getText().toString().trim();
        PoiSearchV2.Query  query=new PoiSearchV2.Query(ID, "100000", "");
        PoiSearchV2   poiSearch = new PoiSearchV2(this, query);
        query.setPageSize(100);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        poiSearch.setBound(new PoiSearchV2.SearchBound(new LatLonPoint(latLng.latitude,
                latLng.longitude), 1000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(new PoiSearchV2.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResultV2 poiResultV2, int i) {
                if(i==1000){
                    Toast.makeText(mContext, "搜索成功"+poiResultV2.getPois().size(),     Toast.LENGTH_SHORT).show();
                    for (int j = 0; j <poiResultV2.getPois().size() ; j++) {
                        PoiItemV2 poiItemV2 = poiResultV2.getPois().get(j);
                        LatLonPoint latLonPoint = poiItemV2.getLatLonPoint();
                        LatLng latLng = new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
                        final Marker marker = mAMap.addMarker(new MarkerOptions().position(latLng).title(poiItemV2.getTitle()).snippet(poiItemV2.getSnippet()));//增加标点
                        marker.showInfoWindow();
                        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
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
    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtil.show(PoiSearchActivity.this, infomation);

    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
        whetherToShowDetailInfo(false);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapview.onSaveInstanceState(outState);
    }

//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapview.onDestroy();
//    }

    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {

        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (item != null) {
                mPoi = item;
                detailMarker.setPosition(AMapUtil.convertToLatLng(mPoi.getLatLonPoint()));
                setPoiItemDisplayContent(mPoi);
                whetherToShowDetailInfo(true);
            }
        } else {
            ToastUtil.showerror(this, rCode);
        }
    }


    @Override
    public void onPoiSearched(PoiResult result, int rcode) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    private void setPoiItemDisplayContent(final PoiItem mCurrentPoi) {
        if (mCurrentPoi != null) {
            mPoiName.setText(mCurrentPoi.getTitle());
            mPoiAddress.setText(mCurrentPoi.getSnippet());
            mPoiInfo.setText("营业时间：" + mCurrentPoi.getPoiExtension().getOpentime()
                    + "     评分：" + mCurrentPoi.getPoiExtension().getmRating());
        }
    }
    @Override
    public View getInfoWindow(final Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
                null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());
        ImageButton button = (ImageButton) view
                .findViewById(R.id.start_amap_app);
        // 调起高德地图app

        View view1=view.findViewById(R.id.rootview);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMark mark =new MyMark(marker.getPosition().latitude+"",marker.getPosition().longitude+"",marker.getTitle(),marker.getSnippet());
                Intent intent = new Intent();
                intent.putExtra(MyMark.class.getSimpleName(),mark);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                try {
                    doSearchQuery();
                } catch (AMapException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }

    }

    private void whetherToShowDetailInfo(boolean isToShow) {
        if (isToShow) {
            mPoiDetail.setVisibility(View.VISIBLE);

        } else {
            mPoiDetail.setVisibility(View.GONE);

        }
    }


}