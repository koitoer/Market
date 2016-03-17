package com.example.koitoer.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.koitoer.market.model.MarketList;
import com.example.koitoer.market.service.MarketService;
import com.example.koitoer.market.service.MarketServiceLocal;

public class SelectListsActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "SIPER" ;

    ListView myList;
    MarketService marketService = new MarketServiceLocal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lists);

        myList = (ListView) findViewById(R.id.listViewLists);
        marketService.setContext(this);

        myList.setAdapter(new ArrayAdapter<MarketList>(this, android.R.layout.simple_list_item_1, marketService.getSavedLists()));

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Intent intent = new Intent(getApplicationContext(), CreateListActivity.class);
                intent.putExtra(EXTRA_MESSAGE, position);
                startActivity(intent);
            }
        });
    }
}
