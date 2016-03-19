package com.example.koitoer.market.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.koitoer.market.CatalogLibraryActivity;
import com.example.koitoer.market.MainActivity;
import com.example.koitoer.market.R;
import com.example.koitoer.market.SelectItemsActivity;
import com.example.koitoer.market.model.MarketItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by koitoer on 2/21/16.
 */
public class MarketItemAdapter extends MarketItemAdapterBase {

    public MarketItemAdapter(List<MarketItem> marketItemList,Context context) {
        super(context, R.layout.single_listview_item, marketItemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MarketItemHolder marketItemHolder = new MarketItemHolder();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.activity_catalog_library, null);
            marketItemHolder.itemName = (TextView)v.findViewById(R.id.itemName);
            marketItemHolder.itemPrice = (TextView)v.findViewById(R.id.dist);
            marketItemHolder.checkBox = (CheckBox)v.findViewById(R.id.chk_box);
            marketItemHolder.checkBox.setOnCheckedChangeListener((SelectItemsActivity)context);
            v.setTag(marketItemHolder);
        }else{
            marketItemHolder =( MarketItemHolder) v.getTag();
        }

        MarketItem marketItem = marketItemList.get(position);
        marketItemHolder.itemName.setText(marketItem.getName());
        marketItemHolder.itemPrice.setText(nf.format(marketItem.getPrice()));
        marketItemHolder.checkBox.setChecked(marketItem.isSelected());
        marketItemHolder.checkBox.setTag(marketItem);
        return v;
    }
}
