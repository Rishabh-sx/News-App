package com.rishabh.newstand.presenter_test;

import com.rishabh.newstand.home.news.headlines.HeadlinesPresenter;
import com.rishabh.newstand.home.news.headlines.HeadlinesView;
import com.rishabh.newstand.utils.AppConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PresenterHeadlinesTests {

    @Mock
    private HeadlinesPresenter headlinesPresenter;

    @Mock
    private HeadlinesView headlinesView;

    @Before
    public void setUp() {
        headlinesPresenter = new HeadlinesPresenter(headlinesView);
    }

    @Test
    public void fragmentTypeFetched() {
        String fragmentType = AppConstants.KEY_SAVED;
        headlinesPresenter.fragmentTypeFetched(fragmentType);
        verify(headlinesView).setAdapters();
        verify(headlinesView).initListeners(fragmentType);
    }

    @Test
    public void savedInstanceStateFlow() {
        String fragmentType = AppConstants.KEY_TECHNOLOGY;
        headlinesPresenter.savedInstanceStateFlow(fragmentType);
        verify(headlinesView).setAdapters();
        verify(headlinesView).initListeners(fragmentType);
    }


    @Test
    public void refreshWidget() {
        headlinesPresenter.refreshWidget();
        verify(headlinesView).refreshWidget();
    }

}
