package com.rishabh.newstand.presenter_test;

import com.rishabh.newstand.home.news.newsDetail.ArticleDetailModel;
import com.rishabh.newstand.home.news.newsDetail.ArticleDetailPresenter;
import com.rishabh.newstand.home.news.newsDetail.ArticleDetailView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ArticleDetailPresenterTests {

    @Mock
    private ArticleDetailPresenter articleDetailPresenter;

    @Mock
    private ArticleDetailModel articleDetailModel;

    @Mock
    private ArticleDetailView articleDetailView;

    @Before
    public void setUp() {
        articleDetailPresenter = new ArticleDetailPresenter(articleDetailView){
            @Override
            protected void setModel() {
                this.model = articleDetailModel;
            }
        };
    }

    @Test
    public void destroyTest() {
       articleDetailPresenter.destroy();
       verify(articleDetailModel).detachListener();
    }

    @Test
    public void initViewTest() {
        articleDetailPresenter.initView();
        verify(articleDetailView).initViews();
    }


}
