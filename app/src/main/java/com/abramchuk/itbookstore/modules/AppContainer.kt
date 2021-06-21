package com.abramchuk.itbookstore.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abramchuk.itbookstore.ui.foundBooks.FoundBooksViewModel

class AppContainer

@Suppress("UNCHECKED_CAST")
class BookSearchViewModelFactory(val context: Context, private val inputStr: String) :
        ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FoundBooksViewModel(context, inputStr) as T
    }
}

