package com.steveq.movieexplorer.db;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.steveq.movieexplorer.model.Movie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbOperationManager {
    private static DbOperationManager instance;
    private WishlistSqliteHelper dbHelper;

    public static DbOperationManager getInstance(Context ctx){
        if(instance == null){
            instance = new DbOperationManager(ctx);
        }
        return instance;
    }

    public DbOperationManager(Context ctx) {
        this.dbHelper = OpenHelperManager.getHelper(ctx, WishlistSqliteHelper.class);
    }

    private WishlistSqliteHelper getHelper(){
        return dbHelper;
    }

    public boolean isWished(int id){
        List<Movie> results = new ArrayList<>();
        try {
            QueryBuilder<Movie, Integer> queryBuilder = getHelper().getMovieDao().queryBuilder();
            queryBuilder.where().eq("id", id);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  results.size() != 0;
    }

    public int addWish(Movie movie){
        try {
            getHelper().getMovieDao().create(movie);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie.getId();
    }

    public void removeWish(int id){
        try {
            DeleteBuilder<Movie, Integer> deleteBuilder = getHelper().getMovieDao().deleteBuilder();
            deleteBuilder.where().eq("id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getWishes(){
        List<Movie> results = new ArrayList<>();
        try {
            QueryBuilder<Movie, Integer> queryBuilder = getHelper().getMovieDao().queryBuilder();
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
