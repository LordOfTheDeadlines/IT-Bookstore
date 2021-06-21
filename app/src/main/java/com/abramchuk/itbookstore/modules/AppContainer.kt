package com.abramchuk.itbookstore.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abramchuk.itbookstore.ApiService
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.ui.foundBooks.FoundBooksViewModel
import com.abramchuk.itbookstore.repository.BookRepository
import com.abramchuk.itbookstore.ui.favourites.FavouritesViewModel
import com.abramchuk.itbookstore.ui.newBooks.NewBooksViewModel
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    val apiService = Builder()
    .baseUrl("https://api.itbook.store/1.0/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(ApiService::class.java)
    val repository = BookRepository(apiService)
    val homeViewModelFactory = HomeViewModelFactory(repository)
//    val bookSearchViewModelFactory = BookSearchViewModelFactory(repository)
//    val bookSearchViewModelFactory = BookSearchViewModelFactory(apiService)
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(val repository: BookRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        NewBooksViewModel(repository) as T
}

@Suppress("UNCHECKED_CAST")
class BookSearchViewModelFactory(val context: Context, private val api: ApiService, val inputStr:String) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FoundBooksViewModel(context, api, inputStr) as T
    }
}

//@Suppress("UNCHECKED_CAST")
//class FavouritesViewModelFactory(val userId: Int):
//        ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//            FavouritesViewModel(userId) as T
//}
