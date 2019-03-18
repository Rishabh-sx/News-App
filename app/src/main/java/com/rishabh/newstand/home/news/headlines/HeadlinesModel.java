package com.rishabh.newstand.home.news.headlines;

import com.google.gson.Gson;
import com.rishabh.newstand.base.BaseModel;
import com.rishabh.newstand.data.database.AppExecutors;
import com.rishabh.newstand.network.NetworkResponse;
import com.rishabh.newstand.pojo.FailureResponse;
import com.rishabh.newstand.pojo.headlinesresponse.Article;
import com.rishabh.newstand.pojo.headlinesresponse.HeadlinesResponse;
import com.rishabh.newstand.utils.AppConstants;

import java.util.HashMap;
import java.util.List;


class HeadlinesModel extends BaseModel<HeadlinesModelListener> {


    HeadlinesModel(HeadlinesModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {

    }

    void getHeadlines(final String headlineType) {

        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.KEY_CATEGORY, headlineType);
        hashMap.put(AppConstants.KEY_COUNTRY, "us");
        hashMap.put(AppConstants.KEY_API_KEY, AppConstants.NEWS_API_KEY);

        getDataManager().getHeadlines(hashMap).enqueue(new NetworkResponse<HeadlinesResponse>(this) {
            @Override
            public void onSuccess(final HeadlinesResponse body) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        //Delete all unsaved articles from the database by headline type
                        //which are not saved.
                        getDataManager().getDatabase().newsDao().delAllArticlesByHeadline(headlineType);


                        for (Article article : body.getArticles()) {
                                article.setHeadlineType(headlineType);
                                getDataManager().getDatabase().newsDao().insertArticle(article);
                        }

                        if(headlineType.equals(AppConstants.KEY_POPULAR)){
                            saveArticlesInPreference(body.getArticles());
                        }

                    }
                });
            }

            @Override
            public void onFailure(int code, FailureResponse failureResponse) {

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    private void saveArticlesInPreference(List<Article> articles) {

        getDataManager().getPreferenceManager().setString(AppConstants.KEY_POPULAR,
                new Gson().toJson(articles));

        if(getListener()!=null){
            getListener().refreshWidget();
        }
    }



}
