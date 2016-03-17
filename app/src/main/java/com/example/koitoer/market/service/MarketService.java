package com.example.koitoer.market.service;

import android.content.Context;

import com.example.koitoer.market.model.MarketItem;
import com.example.koitoer.market.model.MarketList;

import java.util.List;

/**
 * Created by koitoer on 2/21/16.
 */
public interface MarketService {

    void setContext(Context context);

    void addItem(MarketItem marketItem);

    MarketList createList(List<MarketItem> marketItems);

    MarketList getMarketListFromId(long id);

    List<MarketItem> getMarketCatalog();

    List<MarketList> getSavedLists();

    void updateMarketList(int listIndex, List<MarketItem> marketItems);
}
