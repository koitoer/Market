package com.example.koitoer.market.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by koitoer on 2/25/16.
 */
@DatabaseTable
public class ItemList implements Serializable {


    @DatabaseField(generatedId = true)
    private long itemListId;

    @DatabaseField(foreign = true, columnName = "marketListId")
    public MarketList marketListId;

    @DatabaseField(foreign = true, columnName = "marketItemId")
    public MarketItem marketItemId;

    public ItemList() {
    }

    public ItemList(MarketList marketListId, MarketItem marketItemId) {
        this.marketListId = marketListId;
        this.marketItemId = marketItemId;
    }

    public MarketList getMarketListId() {
        return marketListId;
    }

    public void setMarketListId(MarketList marketListId) {
        this.marketListId = marketListId;
    }

    public MarketItem getMarketItemId() {
        return marketItemId;
    }

    public void setMarketItemId(MarketItem marketItemId) {
        this.marketItemId = marketItemId;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "itemListId=" + itemListId +
                ", marketListId=" + marketListId +
                ", marketItemId=" + marketItemId +
                '}';
    }
}
