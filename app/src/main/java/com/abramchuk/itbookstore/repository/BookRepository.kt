package com.abramchuk.itbookstore.repository

import com.abramchuk.itbookstore.ApiService
import com.abramchuk.itbookstore.dto.BookInfo
import com.abramchuk.itbookstore.dto.BookResponse
import com.abramchuk.itbookstore.utils.Result
import retrofit2.Response
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNewBooks(): Result<BookResponse>? {
        val response = apiService.getNewBooks()
        return response.getApiResult()
    }

    suspend fun getBook(id:String):Result<BookInfo>?{
        val response = apiService.getBookByISBN(id)
        return response.getApiResult()
    }
    suspend fun getBooksData(searchStr:String, page:String): BookResponse {
        return apiService.getBooksData(searchStr,page)
    }
}


fun <T> Response<T>.getApiResult(): Result<T>? {
    return if(this.isSuccessful && this.code()==200) Result.success(this.body())
    else if(this.code()>=400) Result.error<T>(null, this.code().toString())
    else Result.error<T>(null, "")
}
