package com.rishabh.newstand.home.news;

import com.rishabh.newstand.home.sources.SourcesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NewsPresenterProvider {

    @ContributesAndroidInjector(modules = SelectTopicModule.class)
    abstract NewsFragment provideCreateStepOneFragment();

    @ContributesAndroidInjector(modules = SourceFragmentModule.class)
    abstract SourcesFragment provideSourceFragment();
}