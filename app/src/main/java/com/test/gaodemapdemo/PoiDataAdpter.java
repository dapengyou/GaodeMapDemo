package com.test.gaodemapdemo;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by lady_zhou on 2017/8/25.
 */

public class PoiDataAdpter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {

    private List<PoiItem> mBookList;

    public PoiDataAdpter(@Nullable List<PoiItem> data) {
        super(R.layout.item_address, data);
        mBookList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        if (item == null) return;
        helper.setText(R.id.tv_address_name, item.getTitle());
        helper.setText(R.id.tv_address_des, item.getSnippet());
    }

}
