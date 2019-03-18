package com.rishabh.newstand.home.news.headlines;

import com.rishabh.newstand.base.BaseView;



public interface HeadlinesView extends BaseView {

    void getHeadlinesType();

    void initListeners(String fragmentType);

    void setAdapters();

    void refreshWidget();
}
