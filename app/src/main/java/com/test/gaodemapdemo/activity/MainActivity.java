package com.test.gaodemapdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.gaodemapdemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtShowMap;
    private Button mBtShowLocation;
    private Button mBtShowMarkers;
    private Button mBtShowPoi;
    private Button mBtShowLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void initView() {
        mBtShowMap = (Button) findViewById(R.id.bt_showMap);
        mBtShowLocation = (Button) findViewById(R.id.bt_showLocation);
        mBtShowPoi = (Button) findViewById(R.id.bt_showPoi);
        mBtShowMarkers = (Button) findViewById(R.id.bt_showMarkers);
        mBtShowLine = (Button) findViewById(R.id.bt_showLine);
    }

    private void setListener() {
        mBtShowMap.setOnClickListener(this);
        mBtShowLocation.setOnClickListener(this);
        mBtShowPoi.setOnClickListener(this);
        mBtShowMarkers.setOnClickListener(this);
        mBtShowLine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_showMap:
                startActivity(new Intent(this,ShowMapActivity.class));
                break;
            case R.id.bt_showLocation:
                startActivity(new Intent(this,ShowLocationActivity.class));
                break;
            case R.id.bt_showMarkers:
                startActivity(new Intent(this,ShowMarkersActivity.class));
                break;
            case R.id.bt_showPoi:
                startActivity(new Intent(this,ShowPoiActivity.class));
                break;
            case R.id.bt_showLine:
                startActivity(new Intent(this,ShowLineActivity.class));
                break;
        }
    }
}
