package com.rishabh.newstand.dagger.module;

import com.rishabh.newstand.home.HomeActivity;
import com.rishabh.newstand.home.HomeModule;
import com.rishabh.newstand.home.news.NewsPresenterProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PresenterModule {

    @ContributesAndroidInjector(modules = {HomeModule.class,NewsPresenterProvider.class})
    abstract HomeActivity binHomeActivity();

}