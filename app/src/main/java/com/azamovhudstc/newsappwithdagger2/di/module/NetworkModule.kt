package com.azamovhudstc.newsappwithdagger2.di.module

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.azamovhudstc.newsappwithdagger2.data.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(var context: Context) {

    @Provides
    @Singleton
    fun provideContext():Context = context

    @Provides
    @Singleton
    fun provideBaseUrl():String = "https://api.thenewsapi.com/v1/news/"


    @Provides
    @Singleton
    fun provideGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(httpLoggingInterceptor)
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl:String, gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}