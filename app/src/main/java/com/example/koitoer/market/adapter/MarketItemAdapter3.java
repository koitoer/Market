package com.example.koitoer.market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class MarketItemAdapter3 extends ArrayAdapter<MarketItem> {

    NumberFormat nf = NumberFormat.getInstance(Locale.US);

    private List<MarketItem> marketItemList;
    private Context context;

    public List<MarketItem> getMarketItemList() {
        return marketItemList;
    }

    public void setMarketItemList(List<MarketItem> marketItemList) {
        this.marketItemList = marketItemList;
    }

    public MarketItemAdapter3(List<MarketItem> marketItemList, Context context) {
        super(context, R.layout.single_listview_item, marketItemList);
        this.marketItemList = marketItemList;
        this.context = context;
    }

    public MarketItemAdapter3(List<MarketItem> marketItemList, Context context, int i){
        super(context, i, marketItemList);
        this.marketItemList = marketItemList;
        this.context = context;
    }

    private static class MarketItemHolder{
        public TextView itemName;
        public TextView itemPrice;
        public CheckBox checkBox;
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
           marketItemHolder.checkBox.setOnCheckedChangeListener((CatalogLibraryActivity)context);
            v.setTag(marketItemHolder);
        }else{
            marketItemHolder =( MarketItemHolder)v.getTag();
        }

        MarketItem marketItem = marketItemList.get(position);
        marketItemHolder.itemName.setText(marketItem.getName());
        marketItemHolder.itemPrice.setText(nf.format(marketItem.getPrice()));
        marketItemHolder.checkBox.setChecked(marketItem.isSelected());
        marketItemHolder.checkBox.setTag(marketItem);
        return v;
    }
}
