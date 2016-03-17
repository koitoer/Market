package com.example.koitoer.market.model;

import android.util.Log;

import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by koitoer on 2/21/16.
 */
@DatabaseTable
public class MarketList implements Serializable {

    @DatabaseField(generatedId = true)
    public long id;

    @DatabaseField
    public Date date;

    @DatabaseField
    public String name;

    public List<MarketItem> marketItemList =  new ArrayList<MarketItem>();

    public MarketList() {
    }

    public MarketList(long id, Date date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MarketItem> getMarketItemList() {
        return this.marketItemList;
    }

    public void setMarketItemList(List<MarketItem> marketItemList) {
        this.marketItemList = marketItemList;
    }

    @Override
    public String toString() {
        return "MarketList{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", marketItemList=" + marketItemList +
                '}';
    }
}
