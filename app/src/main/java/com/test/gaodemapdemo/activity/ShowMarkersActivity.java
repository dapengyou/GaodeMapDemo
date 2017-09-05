package com.test.gaodemapdemo.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.test.gaodemapdemo.R;

/**
 * 展示marker
 * Created by lady_zhou on 2017/8/2.
 */

public class ShowMarkersActivity extends Activity implements AMapLocationListener, AMap.OnMapLongClickListener {
    private AMap aMap;  //初始化地图控制器对象
    private MapView mMapView;

    private MyLocationStyle myLocationStyle = new MyLocationStyle();    //初始化定位蓝点样式类

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    Marker marker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);
        initView();
        mMapView.onCreate(savedInstanceState);
        initMap();
        setListener();
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.mv_map);
    }

    private void initMap() {

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。

        //获取一次定位结果：该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void setListener() {
        mLocationClient.setLocationListener(this);
        aMap.setOnMapLongClickListener(this);
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
        // 绑定marker拖拽事件
        aMap.setOnMarkerDragListener(markerDragListener);
    }

    /**
     * 高德定位回调
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                LatLng location = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
                drawMark(location);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        drawMark(latLng);
    }

    /**
     * 绘制mark标记
     *
     * @param point
     */
    private void drawMark(LatLng point) {
        if (marker != null) {
            clearMarker();
        }
        if (marker == null) {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(point);
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.mipmap.icon_openmap_mark)));
            markerOption.setFlat(true);//设置marker平贴地图效果
            marker = aMap.addMarker(markerOption);
            marker.setDraggable(true);


        }
    }

    /**
     * 清除marker
     */
    private void clearMarker() {
        marker.remove();
        marker = null;
    }

    // 定义 Marker 点击事件监听
    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(Marker marker) {
            Log.d("888", marker.getPosition().latitude + "--------" + marker.getPosition().longitude);
            return false;
        }
    };
    // 定义 Marker拖拽的监听
    AMap.OnMarkerDragListener markerDragListener = new AMap.OnMarkerDragListener() {

        // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        @Override
        public void onMarkerDragStart(Marker arg0) {
            Log.d("888", "开始拖拽" + arg0.getPosition());
        }

        // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        @Override
        public void onMarkerDragEnd(Marker arg0) {
            Log.d("888", "拖拽结束" + arg0.getPosition());
        }

        // 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        @Override
        public void onMarkerDrag(Marker arg0) {

        }
    };

}