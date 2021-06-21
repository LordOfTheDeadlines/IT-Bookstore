package com.abramchuk.itbookstore.modules

import com.abramchuk.itbookstore.ApiService
import com.abramchuk.itbookstore.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideBookRepository(apiService: ApiService) = BookRepository(apiService)
}