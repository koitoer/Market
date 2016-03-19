package com.example.koitoer.market.service;

import android.content.Context;
import android.util.Log;

import com.example.koitoer.market.model.ItemList;
import com.example.koitoer.market.model.MarketItem;
import com.example.koitoer.market.model.MarketList;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by koitoer on 2/21/16.
 */
public class MarketServiceLocal implements MarketService {

    static List<MarketList> myMarketList =  new ArrayList<MarketList>();
    static List<MarketItem> myMarketCatalog =  new ArrayList<MarketItem>();

    DatabaseHelper databaseHelper = null;


    static{
        myMarketCatalog.add(new MarketItem(0,"Item0", 0.00));
        myMarketCatalog.add(new MarketItem(1,"Item1", 0.00));
    }

    private Context context =  null;

    public void setContext(Context context){
        this.context = context;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void addItem(MarketItem marketItem) {
        try {
            getHelper().getSimpleItemDataDao().create(marketItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
        try {
            List<MarketItem> ma = getHelper().getSimpleItemDataDao().queryForAll();
            Log.d("items", ma.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myMarketCatalog.add(marketItem);
    }

    @Override
    public MarketList createList(List<MarketItem> marketItems) {
        long newId = 0;
        try {
            newId = getHelper().getSimpleDataDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MarketList marketList = new MarketList(newId, new Date(), "My List");
        marketList.setMarketItemList(marketItems);
        Log.d("ItemforList", marketItems.toString());
        myMarketList.add(marketList);
        try {
            getHelper().getSimpleDataDao().create(marketList);

            for(MarketItem marketItem : marketList.getMarketItemList()){
                getHelper().getSimpleItemListDataDao().create(new ItemList(marketList, marketItem));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("ERRO", "insert not done");
        }
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
        try {
            List<MarketList> m = getHelper().getSimpleDataDao().queryForAll();
            for(MarketList ma : m) {
                Log.d("lis", ma.toString());
            }


            List<ItemList> itemLists = getHelper().getSimpleItemListDataDao().queryForAll();
            for(ItemList maa : itemLists) {
                Log.d("lis", maa.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marketList;

    }

    @Override
    public MarketList getMarketListFromId(long id) {
        try {
            MarketList marketList =  getHelper().getSimpleDataDao().queryForId(id+1L);
            marketList.setMarketItemList(this.lookupItemsForList(marketList));
            Log.d("Returned list" , marketList.toString());
            return marketList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myMarketList.get((int)id);
    }

    private PreparedQuery<MarketItem> itemsForListQuery = null;

    private List<MarketItem> lookupItemsForList(MarketList marketList) throws SQLException {
        if (itemsForListQuery == null) {
            itemsForListQuery = makeItemsForListQuery();
        }
        itemsForListQuery.setArgumentHolderValue(0, marketList);
        return getHelper().getSimpleItemDataDao().query(itemsForListQuery);
    }

    private PreparedQuery<MarketItem> makeItemsForListQuery() throws SQLException {
        // build our inner query for UserPost objects
        QueryBuilder<ItemList, Long> userPostQb = getHelper().getSimpleItemListDataDao().queryBuilder();
        // just select the post-id field
        userPostQb.selectColumns("marketItemId");
        SelectArg userSelectArg = new SelectArg();
        // you could also just pass in user1 here
        userPostQb.where().eq("marketListId", userSelectArg);

        // build our outer query for Post objects
        QueryBuilder<MarketItem, Long> postQb = getHelper().getSimpleItemDataDao().queryBuilder();
        // where the id matches in the post-id from the inner query
        postQb.where().in("id", userPostQb);
        return postQb.prepare();
    }

    @Override
    public List<MarketItem> getMarketCatalog() {
        //return myMarketCatalog;
        try {
            return getHelper().getSimpleItemDataDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myMarketCatalog;
    }

    @Override
    public List<MarketList> getSavedLists() {
        try {
            return getHelper().getSimpleDataDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myMarketList;
    }

    @Override
    public void updateMarketList(int listIndex, List<MarketItem> marketItems) {
        try {
            //Selection start in zero, and array in 1
            MarketList marketList = getHelper().getSimpleDataDao().queryForId((long) listIndex+1);
            marketList.setMarketItemList(marketItems);

            Log.d("to update list" , marketList.toString());

            QueryBuilder<ItemList, Long> itemListPq = getHelper().getSimpleItemListDataDao().queryBuilder();
            SelectArg userSelectArg = new SelectArg();
            itemListPq.where().eq("marketListId", userSelectArg);

            PreparedQuery<ItemList> pqItemList= itemListPq.prepare();
            pqItemList.setArgumentHolderValue(0, marketList.getId());

            List<ItemList> itemListList = getHelper().getSimpleItemListDataDao().query(pqItemList);
            Log.d("erasedItem", itemListList.toString());
            getHelper().getSimpleItemListDataDao().delete(itemListList);

            //Add the new values
            for(MarketItem marketItem : marketList.getMarketItemList()){
                getHelper().getSimpleItemListDataDao().create(new ItemList(marketList, marketItem));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Make the update here
    }

    @Override
    public void deleteMarketItem(long itemId) {
        try {
            getHelper().getSimpleItemDataDao().delete(new MarketItem(itemId,"",0.00));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
