package com.example.koitoer.market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.koitoer.market.CatalogLibraryActivity;
import com.example.koitoer.market.R;
import com.example.koitoer.market.model.MarketItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by koitoer on 2/21/16.
 */
public class MarketItemAdapter3 extends MarketItemAdapterBase{

    /**
     * Constructor for the Adapter
     * @param marketItemList
     * @param context
     */
    public MarketItemAdapter3(List<MarketItem> marketItemList, Context context) {
        super(context, R.layout.single_listview_item, marketItemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MarketItemHolder marketItemHolder = new MarketItemHolder();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.item_catalog_entire_list_layout, null);
            marketItemHolder.itemName = (TextView)v.findViewById(R.id.itemName);
            marketItemHolder.itemPrice = (TextView)v.findViewById(R.id.dist);
            marketItemHolder.category = (TextView)v.findViewById(R.id.categoryName);
            marketItemHolder.button = (Button)v.findViewById(R.id.deleteItemButton);
            v.setTag(marketItemHolder);
        }else{
            marketItemHolder =( MarketItemHolder)v.getTag();
        }

        MarketItem marketItem = marketItemList.get(position);
        marketItemHolder.itemName.setText(marketItem.getName());
        marketItemHolder.itemPrice.setText(nf.format(marketItem.getPrice()));
        marketItemHolder.category.setText(marketItem.getCategory());
        marketItemHolder.button.setTag(marketItem.getId());
        return v;
    }
}
