package com.abramchuk.itbookstore.ui.favourites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.dto.BookResponse
import com.abramchuk.itbookstore.dto.User
import com.abramchuk.itbookstore.utils.Result
import kotlinx.coroutines.launch


class FavouritesViewModel(userId: Int) : ViewModel() {

    private val _book = MutableLiveData<Result<BookResponse>>()
    val book: LiveData<Result<BookResponse>> = _book

//    init {
//        loadBooks(userId)
//    }
//
//    fun loadBooks(userId:Int) {
//        Log.d("Load_test", "loaded ")
//        viewModelScope.launch {
//            AppDatabase.createDb().getFavouritesDAO().getUserFavourites(userId)
//            _book.value =
//        }
//    }
}