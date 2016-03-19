package com.example.koitoer.market;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.koitoer.market.adapter.MarketItemAdapter;
import com.example.koitoer.market.adapter.MarketItemAdapter3;
import com.example.koitoer.market.model.MarketItem;
import com.example.koitoer.market.service.MarketService;
import com.example.koitoer.market.service.MarketServiceLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koitoer on 3/18/16.
 */
public class CatalogLibraryActivity extends AppCompatActivity {

    private MarketService marketService = new MarketServiceLocal();
    private MarketItemAdapter3 marketItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_listview_item);
        displayItemList();

    }

    private void displayItemList() {
        marketService.setContext(this);
        marketItemAdapter = new MarketItemAdapter3(marketService.getMarketCatalog(), this);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(marketItemAdapter);
    }


    public void deleteItem(View view) {
        Toast.makeText(this, String.valueOf(view.getTag()), Toast.LENGTH_LONG).show();
        marketService.deleteMarketItem((long)view.getTag());
        displayItemList();
    }
}
