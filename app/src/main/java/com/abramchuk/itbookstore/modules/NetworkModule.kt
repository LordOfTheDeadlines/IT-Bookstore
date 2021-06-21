package com.abramchuk.itbookstore.modules

import com.abramchuk.itbookstore.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named("api")
    fun provideBaseUrl() = ApiService.HOST


    @Provides
    @Singleton
    fun provideRestClient(@Named("api") baseUrl: String) : ApiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}