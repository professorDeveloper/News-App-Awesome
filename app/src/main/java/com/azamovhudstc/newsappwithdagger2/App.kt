package com.azamovhudstc.newsappwithdagger2

import android.app.Application
import com.azamovhudstc.newsappwithdagger2.di.component.AppComponent
import com.azamovhudstc.newsappwithdagger2.di.component.DaggerAppComponent
import com.azamovhudstc.newsappwithdagger2.di.module.NetworkModule
import com.azamovhudstc.newsappwithdagger2.utils.Topics.appComponent

class App : Application() {

    companion object{
        const val API_KEY = "H1J12hztuqaZiKQgJMUvDyCfyIX1VvMuDfbYIdnE"
    }


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule(this))
            .build()
    }

}