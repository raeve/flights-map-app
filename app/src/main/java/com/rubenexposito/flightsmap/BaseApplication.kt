package com.rubenexposito.flightsmap

import android.app.Activity
import android.app.Application
import com.rubenexposito.flightsmap.di.AppComponent
import com.rubenexposito.flightsmap.di.DaggerAppComponent
import com.rubenexposito.flightsmap.domain.OAuthInteractor
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var interactor: OAuthInteractor

    lateinit var component: AppComponent

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initAuth()
    }

    private fun initDagger() {
        component = DaggerAppComponent
                .builder()
                .application(this)
                .build()

        component.inject(this)
    }

    private fun initAuth() {
        interactor.getAuthToken()
    }
}