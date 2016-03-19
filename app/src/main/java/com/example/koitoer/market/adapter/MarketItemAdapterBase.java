package com.example.koitoer.market.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.koitoer.market.R;
import com.example.koitoer.market.model.MarketItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by koitoer on 3/18/16.
 */
public abstract class MarketItemAdapterBase extends ArrayAdapter<MarketItem> {

    protected NumberFormat nf = NumberFormat.getInstance(Locale.US);
    protected List<MarketItem> marketItemList;
    protected Context context;

    public List<MarketItem> getMarketItemList() {
        return marketItemList;
    }

    public void setMarketItemList(List<MarketItem> marketItemList) {
        this.marketItemList = marketItemList;
    }

    public MarketItemAdapterBase(Context context, int layoutId, List<MarketItem> marketItemList) {
        super(context, layoutId, marketItemList);
        this.marketItemList = marketItemList;
        this.context = context;
    }

}

