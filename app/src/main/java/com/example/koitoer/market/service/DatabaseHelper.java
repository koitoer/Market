package com.example.koitoer.market.service;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.koitoer.market.model.ItemList;
import com.example.koitoer.market.model.MarketItem;
import com.example.koitoer.market.model.MarketList;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "helloNoBase4.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<MarketList, Long> simpleDao = null;
    private Dao<MarketItem, Long> simpleItemDao = null;
    private Dao<ItemList, Long> simpleListItemDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, MarketList.class);
            TableUtils.createTable(connectionSource, MarketItem.class);
            TableUtils.createTable(connectionSource, ItemList.class);
            long millis = System.currentTimeMillis();
            Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, MarketList.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<MarketList, Long> getSimpleDataDao() throws SQLException {
        if (simpleDao == null) {
            simpleDao = getDao(MarketList.class);
        }
        return simpleDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<MarketItem, Long> getSimpleItemDataDao() throws SQLException {
        if (simpleItemDao == null) {
            simpleItemDao = getDao(MarketItem.class);
        }
        return simpleItemDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<ItemList, Long> getSimpleItemListDataDao() throws SQLException {
        if (simpleListItemDao == null) {
            simpleListItemDao = getDao(ItemList.class);
        }
        return simpleListItemDao;
    }
    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        simpleDao = null;
        simpleItemDao = null;
        simpleListItemDao =  null;
    }
}

