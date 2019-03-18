package com.rishabh.newstand.home.news;

import dagger.Module;
import dagger.Provides;

@Module
public class SelectTopicModule {

    @Provides
    NewsPresenter provideNewsPresenter() {
        return new NewsPresenter();
    }
}