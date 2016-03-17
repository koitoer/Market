package com.example.koitoer.market;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.koitoer.market.adapter.MarketItemAdapter;
import com.example.koitoer.market.model.MarketItem;
import com.example.koitoer.market.service.MarketService;
import com.example.koitoer.market.service.MarketServiceLocal;

import java.util.ArrayList;
import java.util.List;

public class CatalogLibraryActivity extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener{

    ListView lv;
    List<MarketItem> marketItemList;
    MarketItemAdapter marketItemAdapter;
    MarketService marketService = new MarketServiceLocal();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_listview_item);
        lv = (ListView)findViewById(R.id.listView);
        displayItemList();
        
    }

    private void displayItemList() {
        marketService.setContext(this);
        marketItemList = marketService.getMarketCatalog();
        marketItemAdapter = new MarketItemAdapter(marketItemList, this);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(marketItemAdapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                Intent resultData = new Intent();
                resultData.putExtra("list", (ArrayList<MarketItem>) this.getOnlyMarkedItems(marketItemAdapter.getMarketItemList()));
                setResult(Activity.RESULT_OK, resultData);
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private ArrayList<MarketItem> getOnlyMarkedItems(List<MarketItem> marketItemList) {
        ArrayList<MarketItem> selectedItems =  new ArrayList<>();
        for(MarketItem marketItem : marketItemList){
            if(marketItem.isSelected()){
                selectedItems.add(marketItem);
            }
        }
        Log.d("Selected" , selectedItems.toString());
        return selectedItems;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Log.d("Buttonmae", buttonView.toString());

        if(buttonView.isShown())
        {
            Log.d("Click er", String.valueOf(isChecked));
            int pos = lv.getPositionForView(buttonView);
        Log.d("Click ewwr", String.valueOf(pos));
            if (pos != ListView.INVALID_POSITION) {
                MarketItem marketItem = marketItemAdapter.getMarketItemList().get(pos);
                marketItem.setSelected(isChecked);

                Toast.makeText(this, "Clicke don " + pos, Toast.LENGTH_LONG).show();
            }
        }
    }
}
