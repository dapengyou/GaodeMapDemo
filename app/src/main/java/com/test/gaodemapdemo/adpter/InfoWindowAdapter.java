package com.test.gaodemapdemo.adpter;

import android.view.View;

import com.amap.api.maps.model.Marker;

/**
 * Created by lady_zhou on 2017/8/3.
 */

public interface InfoWindowAdapter {
    View getInfoWindow(Marker marker);
    View getInfoContents(Marker marker);
}
