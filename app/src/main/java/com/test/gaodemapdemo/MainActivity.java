package com.test.gaodemapdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtShowMap;
    private Button mBtShowLocation;

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
    }

    private void setListener() {
        mBtShowMap.setOnClickListener(this);
        mBtShowLocation.setOnClickListener(this);
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
        }
    }
}
