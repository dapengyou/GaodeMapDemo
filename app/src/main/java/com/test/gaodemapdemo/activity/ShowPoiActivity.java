package com.test.gaodemapdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.test.gaodemapdemo.R;
import com.test.gaodemapdemo.adpter.PoiDataAdpter;

import java.util.ArrayList;
import java.util.List;

import static com.test.gaodemapdemo.MyApplication.getmContext;

/**
 * 展示poi
 * Created by lady_zhou on 2017/8/2.
 */

public class ShowPoiActivity extends Activity implements PoiSearch.OnPoiSearchListener, BaseQuickAdapter.RequestLoadMoreListener {
    private PoiSearch.Query query;
    private EditText mEditText;
    private PoiSearch poiSearch;

    private RecyclerView mRecyclerView;
    private BaseQuickAdapter mAdapter;

    private String mKeyWord;
    private int page = 0;
    private int pageCount;

    private List<PoiItem> poiItems;// poi数据
    private PoiResult poiResult; // poi返回的结果

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_poi);

        mEditText = (EditText) findViewById(R.id.et_poi);
        mRecyclerView = (RecyclerView) findViewById(R.id.rl_list);

        mAdapter = new PoiDataAdpter(new ArrayList<PoiItem>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getmContext()));
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        doSearchQuery();

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearchQuery();
                }
                return false;
            }
        });


    }

    private void doSearchQuery() {
        mKeyWord = mEditText.getText().toString().trim();
        //默认返回“餐饮服务”、“商务住宅”、“生活服务”这三种类别的POI
        query = new PoiSearch.Query(mKeyWord, "", "");
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(page);// 设置查第一页


        LatLonPoint lp = new LatLonPoint(40.068316, 116.313391);//116.313391,40.068316(经度，纬度)

        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 2000, true));
            // 设置搜索区域为以lp点为圆心，其周围2000米范围
            poiSearch.searchPOIAsyn();// 异步搜索，调用 PoiSearch 的 searchPOIAsyn() 方法发送请求。
        }
    }

    /**
     * 解析result获取POI信息
     *
     * @param result
     * @param i
     */
    @Override
    public void onPoiSearched(PoiResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            // 搜索poi的结果
            if (result != null && result.getQuery() != null) {
                // 是否是同一条
                if (result.getQuery().equals(query)) {
                    poiResult = result;
                    pageCount = poiResult.getPageCount();
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        if (page == 0) {
                            mAdapter.setNewData(poiItems);
                        } else {
                            mAdapter.addData(poiItems);
                            mAdapter.loadMoreComplete();
                        }
                    } else {
                        mAdapter.loadMoreEnd();
                    }
                }
            } else {
                mAdapter.loadMoreEnd();
            }
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        if (page < pageCount) {
            doSearchQuery();
        } else {
            mAdapter.loadMoreEnd();
        }
    }
}
