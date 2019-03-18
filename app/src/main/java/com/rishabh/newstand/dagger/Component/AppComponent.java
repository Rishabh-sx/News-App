package com.rishabh.newstand.dagger.Component;

import android.app.Application;

import com.rishabh.newstand.NewsStandApplication;
import com.rishabh.newstand.dagger.module.AppModule;
import com.rishabh.newstand.dagger.module.PresenterModule;
import com.rishabh.newstand.dagger.module.FragmentModule;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {FragmentModule.class, PresenterModule.class, AppModule.class})
public interface AppComponent {
    void inject(NewsStandApplication app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}