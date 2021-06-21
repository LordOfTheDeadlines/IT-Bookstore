package com.abramchuk.itbookstore.ui.foundBooks

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.abramchuk.itbookstore.ApiService
import com.abramchuk.itbookstore.data.BooksPagingSource


class FoundBooksViewModel(context: Context, private val api: ApiService, inputStr:String) : ViewModel() {
    val books = Pager(PagingConfig(pageSize = 10)) {
        BooksPagingSource(context,api, inputStr)
    }.flow.cachedIn(viewModelScope)
}