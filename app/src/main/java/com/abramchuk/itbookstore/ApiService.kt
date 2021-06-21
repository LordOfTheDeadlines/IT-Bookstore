package com.abramchuk.itbookstore

import com.abramchuk.itbookstore.dto.BookInfo
import com.abramchuk.itbookstore.dto.BookResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET("search/{inputStr}/{page}")
    suspend fun getBooksData(@Path("inputStr")inputStr: String,@Path("page")page:String): BookResponse

    @GET("new")
    suspend fun getNewBooks(): Response<BookResponse>

    @GET("books/{id}")
    suspend fun getBookByISBN(@Path("id") id: String) : Response<BookInfo>

    companion object {
        const val HOST = "https://api.itbook.store/1.0/"
        private fun makeClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()

        fun getInstance(): ApiService = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(makeClient())
            .build()
            .create(ApiService::class.java)
    }
}
