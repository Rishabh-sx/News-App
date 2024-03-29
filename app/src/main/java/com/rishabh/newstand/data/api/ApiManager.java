package com.rishabh.newstand.data.api;

import com.rishabh.newstand.pojo.headlinesresponse.HeadlinesResponse;
import com.rishabh.newstand.pojo.sources.SourcesResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static final ApiManager instance = new ApiManager();
    private final ApiClient apiClient;

    private ApiManager() {
        apiClient = getRetrofitService();
    }

    public static ApiManager getInstance() {
        return instance;
    }

    private static ApiClient getRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiClient.ENDPOINT)
                .build();

        return retrofit.create(ApiClient.class);
    }

    public Call<HeadlinesResponse> getHeadlines(HashMap<String,String> hashMap) {
        return apiClient.getHeadlineTypes(hashMap);
    }

    public Call<SourcesResponse> getSources(HashMap<String,String> hashMap) {
        return apiClient.getSources(hashMap);
    }

    public Call<HeadlinesResponse> search(HashMap<String, String> hashMap) {
        return apiClient.search(hashMap);
    }

    public Observable<HeadlinesResponse> rxSearch(HashMap<String, String> hashMap) {
        return apiClient.rxSearch(hashMap);
    }
}
