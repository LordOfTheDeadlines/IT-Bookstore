package com.abramchuk.itbookstore.ui.books

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.abramchuk.itbookstore.data.BooksPagingSource


class FoundBooksViewModel(context: Context, inputStr: String) : ViewModel() {
    val books = Pager(PagingConfig(pageSize = 10)) {
        BooksPagingSource(context, inputStr)
    }.flow.cachedIn(viewModelScope)
}