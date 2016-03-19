package com.example.koitoer.market.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.koitoer.market.CatalogLibraryActivity;
import com.example.koitoer.market.CreateListActivity;
import com.example.koitoer.market.MainActivity;
import com.example.koitoer.market.R;
import com.example.koitoer.market.model.MarketItem;

import java.util.List;

/**
 * Created by koitoer on 2/21/16.
 */
public class MarketItemAdapter2 extends MarketItemAdapterBase{


    public MarketItemAdapter2(List<MarketItem> marketItemList, Context context) {
        super(context, R.layout.activity_create_list, marketItemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MarketItemHolder marketItemHolder = new MarketItemHolder();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.item_list_selected, null);
            marketItemHolder.itemName = (TextView)v.findViewById(R.id.itemName);
            marketItemHolder.itemPrice = (TextView)v.findViewById(R.id.dist);
            marketItemHolder.category = (TextView)v.findViewById(R.id.categoryName);
            v.setTag(marketItemHolder);
        }else{
            marketItemHolder =( MarketItemHolder)v.getTag();
        }

        MarketItem marketItem = marketItemList.get(position);
        marketItemHolder.itemName.setText(marketItem.getName());
        marketItemHolder.itemPrice.setText(String.valueOf(marketItem.getPrice()));
        marketItemHolder.category.setText(marketItem.getCategory());
        return v;
    }



}
