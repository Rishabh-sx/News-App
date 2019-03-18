package com.rishabh.newstand.home.news.newsDetail;

import com.rishabh.newstand.base.BaseModel;



public class ArticleDetailModel extends BaseModel<ArticleDetailModelListener> {


    ArticleDetailModel(ArticleDetailModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {

    }

    /*public void updateArticle(final Article article) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                getDataManager().getDatabase().newsDao().updateArticle(article);
            }
        });
    }*/
}
