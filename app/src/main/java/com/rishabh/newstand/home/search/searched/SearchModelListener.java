package com.rishabh.newstand.home.search.searched;

import com.rishabh.newstand.base.BaseModelListener;
import com.rishabh.newstand.pojo.headlinesresponse.Article;

import java.util.List;



interface SearchModelListener extends BaseModelListener {

    void onSearchResponse(List<Article> articles);
}
