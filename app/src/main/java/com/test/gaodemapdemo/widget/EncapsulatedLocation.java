package com.test.gaodemapdemo.widget;

import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import static com.test.gaodemapdemo.MyApplication.getmContext;

/**
 * 封装定位
 * Created by lady_zhou on 2017/9/5.
 */

public class EncapsulatedLocation implements AMapLocationListener {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private static EncapsulatedLocation location;

    public static EncapsulatedLocation getInstance() {
        return location == null ? new EncapsulatedLocation() : location;
    }

    public void initLocation() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //初始化定位
        mLocationClient = new AMapLocationClient(getmContext());
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
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

    public void onLocationChanged(AMapLocation aMapLocation, TextView view) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //显示你想显示的
                view.setText(aMapLocation.getAddress());
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }
}
