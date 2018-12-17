package com.rubenexposito.flightsmap.di

import com.rubenexposito.flightsmap.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Mockable
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BuildersModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BaseApplication): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(application: BaseApplication)
}