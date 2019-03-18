package com.rishabh.newstand.home.news;

import com.rishabh.newstand.home.sources.SourcesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SourceFragmentModule {

    @Provides
    SourcesPresenter provideSourcePresenter() {
        return new SourcesPresenter();
    }
}