package com.example.koitoer.market.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by koitoer on 2/21/16.
 */
@DatabaseTable(tableName = "marketitem")
public class MarketItem implements Serializable {

    @DatabaseField(generatedId = true)
    public long id;

    @DatabaseField
    public String name;

    @DatabaseField
    public double price;

    @DatabaseField
    public boolean selected;

    @DatabaseField
    public String category;

    public MarketItem() {
    }

    public MarketItem(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "MarketItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", selected=" + selected +
                ", category='" + category + '\'' +
                '}';
    }
}
