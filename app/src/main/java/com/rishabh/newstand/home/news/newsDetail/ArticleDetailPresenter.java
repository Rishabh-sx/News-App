package com.rishabh.newstand.home.news.newsDetail;

import com.rishabh.newstand.base.BasePresenter;


public class ArticleDetailPresenter extends BasePresenter<ArticleDetailView> implements ArticleDetailModelListener {


    protected ArticleDetailModel model;

    protected ArticleDetailPresenter(ArticleDetailView view) {
        super(view);
    }

    @Override
    protected void setModel() {
        model = new ArticleDetailModel(this);
    }

    @Override
    public void destroy() {
        model.detachListener();
        model = null;
    }

    @Override
    public void initView() {
        if(getView()!=null)
            getView().initViews();
    }

    /*public void updateArticle(Article article) {
        model.updateArticle(article);
    }*/
}
