package com.rishabh.newstand.home.news;

import com.rishabh.newstand.base.BaseModel;



class NewsModel extends BaseModel<NewsModelListener> {

    private static final String TAG = "HomeModel";

    NewsModel(NewsModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {

    }

}
