package com.abramchuk.itbookstore.data

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abramchuk.itbookstore.ApiService
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.manager.BookManager

//class BooksPagingSource(private val api: ApiService, private val inputStr:String) : PagingSource<Int, Book>() {
//
//    private val BOOKS_ON_PAGE = 10;
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
//        return try {
//            val nextPageNumber = params.key ?: 0
//            val response =  api.getBooksData(inputStr,nextPageNumber.toString())
//            LoadResult.Page(
//                    data = response.books,
//                    prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
//                    nextKey = if (nextPageNumber * BOOKS_ON_PAGE < response.total.toInt()) nextPageNumber + 1 else null
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
//        return 0
//    }
//}

class BooksPagingSource(context: Context, private val api: ApiService, private val inputStr:String) : PagingSource<Int, Book>() {

    private val BOOKS_ON_PAGE = 10;
    var bookManager = BookManager(context)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response =  bookManager.downloadBooksByName(inputStr,nextPageNumber.toString())
            LoadResult.Page(
                    data = response.books,
                    prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                    nextKey = if (nextPageNumber * BOOKS_ON_PAGE < response.total.toInt()) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return 0
    }
}

