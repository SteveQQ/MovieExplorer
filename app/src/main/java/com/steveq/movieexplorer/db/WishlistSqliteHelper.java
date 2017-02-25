package com.steveq.movieexplorer.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.steveq.movieexplorer.model.Movie;

import java.sql.SQLException;

public class WishlistSqliteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "wishlist";
    private static final int DB_VERSION = 1;

    private Dao<Movie, Integer> movieDao;

    public Dao<Movie, Integer> getMovieDao() throws SQLException {
        if(movieDao == null){
            movieDao = getDao(Movie.class);
        }
        return movieDao;
    }

    public WishlistSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Movie.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
