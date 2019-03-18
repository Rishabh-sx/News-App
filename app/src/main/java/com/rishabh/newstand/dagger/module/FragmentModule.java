package com.rishabh.newstand.dagger.module;
import com.rishabh.newstand.home.news.NewsFragment;
import com.rishabh.newstand.home.sources.SourcesFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    @Provides
    NewsFragment getNewsFragment() {
        return new NewsFragment();
    }


    @Provides
    SourcesFragment getSourcesFragment() {
        return new SourcesFragment();
    }


}