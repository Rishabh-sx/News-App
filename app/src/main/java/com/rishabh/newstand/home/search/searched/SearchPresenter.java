package com.rishabh.newstand.home.search.searched;

import com.rishabh.newstand.base.BasePresenter;
import com.rishabh.newstand.pojo.headlinesresponse.Article;

import java.util.List;

public class SearchPresenter extends BasePresenter<SearchView> implements SearchModelListener {
//    private SearchModel mModel;
    private SearchRxModel rxModel;

    public SearchPresenter(SearchView view) {
        super(view);
    }

    @Override
    protected void setModel() {
//        mModel = new SearchModel(this);
        rxModel = new SearchRxModel(this);
    }

    @Override
    protected void destroy() {
        rxModel.detachListener();
        rxModel = null;
    }

    @Override
    protected void initView() {
        if (getView() != null) {
            getView().initViewsAndVariables();
            getView().setUpAdapter();
        }
    }

    public void getSearchedNews(String mSearch) {
        rxModel.getSearch(mSearch);

    }

    @Override
    public void onSearchResponse(List<Article> articles) {
        if(getView()!=null)
            getView().setSearchResult(articles);
    }

    public void noSearchQuery() {
        if(getView()!=null)
            getView().clearAdapter();
    }
}
