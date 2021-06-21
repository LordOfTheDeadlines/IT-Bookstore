package com.abramchuk.itbookstore.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.dto.BookInfo


@Dao
interface BookDAO {
    @Insert
    fun insert(book: BookInfo)

    @Query("SELECT * FROM books")
    fun findAllBooks() : List<BookInfo>

    @Query("SELECT * FROM books WHERE isbn13 = :id")
    fun findByIsbn13(id:String) : BookInfo

    @Query("SELECT * FROM books WHERE id = :id")
    fun findById(id:Int) : BookInfo

    @Query("SELECT * FROM books WHERE title LIKE  '%' || :title || '%'")
    fun findByTitle(title:String) :  List<BookInfo>

    @Query("DELETE FROM books WHERE isbn13 = :id")
    fun deleteFromBooks(id:String)

    @Query("DELETE FROM books")
    fun deleteAllBooks()
}