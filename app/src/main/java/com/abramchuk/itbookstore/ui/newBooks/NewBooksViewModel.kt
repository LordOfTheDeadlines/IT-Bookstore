package com.abramchuk.itbookstore.ui.newBooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abramchuk.itbookstore.dto.BookResponse
import com.abramchuk.itbookstore.repository.BookRepository
import com.abramchuk.itbookstore.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewBooksViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    private val _book = MutableLiveData<Result<BookResponse>>()
    val book: LiveData<Result<BookResponse>> = _book

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _book.value = Result.loading(true)
            _book.value = repository.getNewBooks()
        }
    }
}