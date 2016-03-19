package com.example.koitoer.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.koitoer.market.adapter.MarketItemAdapter2;
import com.example.koitoer.market.model.MarketItem;
import com.example.koitoer.market.model.MarketList;
import com.example.koitoer.market.service.MarketService;
import com.example.koitoer.market.service.MarketServiceLocal;
import com.example.koitoer.market.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that show the list of preselected items.
 */
public class CreateListActivity extends AppCompatActivity {

    private MarketService marketService = new MarketServiceLocal();
    private MarketItemAdapter2 marketItemAdapter;
    private int listIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        if(getIntent().getIntExtra(SelectListsActivity.EXTRA_MESSAGE, -1) != -1){
            listIndex = getIntent().getExtras().getInt(SelectListsActivity.EXTRA_MESSAGE);
            this.displayItemList(listIndex);
            findViewById(R.id.createList).setVisibility(View.INVISIBLE);
            findViewById(R.id.saveListButton).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.createList).setVisibility(View.VISIBLE);
            findViewById(R.id.saveListButton).setVisibility(View.INVISIBLE);
        }

    }

    private void displayItemList(int anInt) {
        marketService.setContext(this);
        MarketList marketList = marketService.getMarketListFromId(anInt);
        marketItemAdapter = new MarketItemAdapter2(marketList.getMarketItemList(), this);
        ListView listView = (ListView) findViewById(R.id.itemListView);
        listView.setAdapter(marketItemAdapter);
        Helper.getListViewSize(listView);
        ((TextView)findViewById(R.id.titleOfList)).setText(marketList.toString());

    }

    /**
     * Call to display the item coming from the market list.
     */
    private void displayItemList() {
        marketItemAdapter = new MarketItemAdapter2(marketService.getMarketCatalog(), this);
        ListView listView = (ListView) findViewById(R.id.itemListView);
        listView.setAdapter(marketItemAdapter);
        Helper.getListViewSize(listView);
    }

    /**
     * Launch the selection item screen
     * @param view
     */
    public void addItemToList(View view) {
        Intent intent = new Intent(this, SelectItemsActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * Once I select the items, list will reflect those changes.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantUtil.MY_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                ArrayList<MarketItem> myValueList = (ArrayList<MarketItem>)data.getSerializableExtra("list");
                marketItemAdapter = new MarketItemAdapter2(myValueList, this);
                ListView listView = (ListView) findViewById(R.id.itemListView);
                listView.setAdapter(marketItemAdapter);
                Helper.getListViewSize(listView);
            }
        }
    }

    /**
     * Trigger once the user click save, will create the new list.
     * @param view
     */
    public void createNewList(View view) {
        marketService.setContext(this);
        MarketList marketList = marketService.createList(marketItemAdapter.getMarketItemList());
        finish();
    }

    /**
     * Update the list with new items and selections.
     * @param view
     */
    public void saveChangesList(View view) {
       List<MarketItem> marketItems = marketItemAdapter.getMarketItemList();
       marketService.updateMarketList(listIndex, marketItems);
    }
}
