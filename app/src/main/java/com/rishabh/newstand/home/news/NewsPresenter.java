package com.rishabh.newstand.home.news;

import com.rishabh.newstand.base.BasePresenter;

import javax.inject.Inject;



public class NewsPresenter extends BasePresenter<NewsView> implements NewsModelListener {


    private NewsModel model;

    @Inject
    NewsPresenter() {
        super(null);
    }

    @Override
    protected void setModel() {
        model = new NewsModel(this);
    }

    @Override
    protected void destroy() {
        model.detachListener();
        model = null;
    }

    @Override
    protected void initView() {

    }

    void onViewCreated() {
        if(getView()!=null)
            getView().setupViews();
    }


}
