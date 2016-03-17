package com.example.koitoer.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.koitoer.market.adapter.MarketItemAdapter;
import com.example.koitoer.market.adapter.MarketItemAdapter2;
import com.example.koitoer.market.model.MarketItem;
import com.example.koitoer.market.model.MarketList;
import com.example.koitoer.market.service.MarketService;
import com.example.koitoer.market.service.MarketServiceLocal;

import java.util.ArrayList;
import java.util.List;

public class CreateListActivity extends AppCompatActivity {

    private static final int MY_REQUEST_ID = 0;
    ListView lv;
    List<MarketItem> marketItemList;
    MarketItemAdapter2 marketItemAdapter;
    MarketService marketService = new MarketServiceLocal();
    private boolean isListCreated = false;
    private int listIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        lv = (ListView)findViewById(R.id.itemListView);
        if(getIntent().getIntExtra(SelectListsActivity.EXTRA_MESSAGE, -1) != -1){
            isListCreated = true;
            listIndex = getIntent().getExtras().getInt(SelectListsActivity.EXTRA_MESSAGE);
            this.displayItemList(listIndex);
        }

        if(!isListCreated){
            findViewById(R.id.createList).setVisibility(View.VISIBLE);
            findViewById(R.id.saveListButton).setVisibility(View.INVISIBLE);
        }
       // this.displayItemList();
    }

    private void displayItemList(int anInt) {
        marketService.setContext(this);
        marketItemAdapter = new MarketItemAdapter2(marketService.getMarketListFromId(anInt).getMarketItemList(), this);
        ListView listView = (ListView) findViewById(R.id.itemListView);
        listView.setAdapter(marketItemAdapter);
        Helper.getListViewSize(listView);

        //Lista creada es proceso de edicion
        if(isListCreated){
            findViewById(R.id.createList).setVisibility(View.INVISIBLE);
            findViewById(R.id.saveListButton).setVisibility(View.VISIBLE);
        }

    }

    private void displayItemList() {
        marketItemList = marketService.getMarketCatalog();
        marketItemAdapter = new MarketItemAdapter2(marketItemList, this);
        ListView listView = (ListView) findViewById(R.id.itemListView);
        listView.setAdapter(marketItemAdapter);
        Helper.getListViewSize(listView);
        //tengo que jalar la vista del layout
    }

    public void addItemToList(View view) {
        Intent intent = new Intent(this, CatalogLibraryActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                ArrayList<MarketItem> myValueList = (ArrayList<MarketItem>)data.getSerializableExtra("list");
                Log.d("LIST", myValueList.toString());
                marketItemAdapter = new MarketItemAdapter2(myValueList, this);
                ListView listView = (ListView) findViewById(R.id.itemListView);
                listView.setAdapter(marketItemAdapter);
                Helper.getListViewSize(listView);
            }
        }

    }

    public void createNewList(View view) {
        marketService.setContext(this);
        MarketList marketList = marketService.createList(marketItemAdapter.getMarketItemList());
        Log.d("createdList", marketList.toString());
        finish();
    }

    public void saveChangesList(View view) {
       List<MarketItem> marketItems = marketItemAdapter.getMarketItemList();
        Log.d("itemnew", marketItems.toString());
       marketService.updateMarketList(listIndex, marketItems);
        //Update database with changes
    }
}
