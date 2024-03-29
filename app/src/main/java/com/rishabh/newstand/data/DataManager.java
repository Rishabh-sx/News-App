package com.rishabh.newstand.data;

import android.content.Context;

import com.rishabh.newstand.data.api.ApiManager;
import com.rishabh.newstand.data.database.AppDatabase;
import com.rishabh.newstand.data.preference.PreferenceManager;
import com.rishabh.newstand.pojo.headlinesresponse.HeadlinesResponse;
import com.rishabh.newstand.pojo.sources.SourcesResponse;

import java.util.HashMap;

import retrofit2.Call;



public class DataManager implements IDataManager {

    private final AppDatabase mDb;
    private final ApiManager apiManager;
    private static DataManager instance;
    private final PreferenceManager preferenceManager;


    public static void init(Context applicationContext) {
        if (instance == null)
            instance = new DataManager(applicationContext);
    }

    public static DataManager getInstance() {
        return instance;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    private DataManager(Context applicationContext) {
        preferenceManager = PreferenceManager.getInstance(applicationContext);
        apiManager = ApiManager.getInstance();
        mDb = AppDatabase.getInstance(applicationContext);
    }

    /*public static DataManager getInstance() {
        if (instance == null) {
            synchronized (DataManager.class){
                if (instance == null)
                    instance = new DataManager(applicationContext);
            }
        }
        return instance;
    }*/


    public AppDatabase getDatabase() {
        return mDb;
    }

    @Override
    public Call<HeadlinesResponse> getHeadlines(HashMap<String, String> hashMap) {
        return apiManager.getHeadlines(hashMap);
    }

    @Override
    public Call<SourcesResponse> getSources(HashMap<String, String> hashMap) {
        return apiManager.getSources(hashMap);
    }

    @Override
    public Call<HeadlinesResponse> getSearch(HashMap<String, String> hashMap) {
        return apiManager.search(hashMap);
    }
}
