package com.abramchuk.itbookstore.manager

import android.content.Context
import com.abramchuk.itbookstore.ApiService
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.db.BookDAO
import com.abramchuk.itbookstore.db.FavouritesDAO
import com.abramchuk.itbookstore.db.UserDAO
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.dto.BookInfo
import com.abramchuk.itbookstore.dto.BookResponse
import com.abramchuk.itbookstore.dto.Favourites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookManager(context: Context) {

    private var bookDao: BookDAO? = AppDatabase.createDb(context).getBookDAO()
    private var userDAO: UserDAO? = AppDatabase.createDb(context).getUserDAO()
    private var favouritesDao: FavouritesDAO? = AppDatabase.createDb(context).getFavouritesDAO()

    fun addToFavourites(item: BookInfo) {
        val user = userDAO!!.getActiveUser()
        favouritesDao?.insert(Favourites(user.id, item.id))
    }

    fun deleteFromFavourites(item: BookInfo) {
        val user = userDAO!!.getActiveUser()
        favouritesDao?.deleteUserFavourite(user.id,item.id)
    }

    fun isFavourite(book:BookInfo): Boolean? {
        val user = userDAO!!.getActiveUser()
        return favouritesDao?.findUserFavourite(user.id, book.id)?.isNotEmpty()
    }

    fun getFavourites(): List<Book> {
        val user = userDAO!!.getActiveUser()
        val favourites = favouritesDao?.getUserFavourites(user.id)
        return convertFavouritesToBooks(favourites)
    }

    suspend fun getApiNewBooks(): List<Book> {
        val listResponse = ApiService.getInstance().getNewBooks()
        val books = listResponse.body()?.books!!
        saveBooksToDb(books)
        return books
    }

    suspend fun getApiBooksByTitle(title: String, page:String): BookResponse {
        val listResponse = ApiService.getInstance().getBooksData(title, page)
        val books = listResponse.books
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
               saveBooksToDb(books)
            }
        }
        return listResponse
    }

    fun getDBBooksByTitle(title: String): List<Book> {
        val booksInfo = bookDao?.findByTitle(title)
        val books = mutableListOf<Book>()
        if (booksInfo != null) {
            for (bI in booksInfo)
                books.add(convertBookInfoToBook(bI))
        }
        return books
    }

    fun getBooksByISBN13(isbn13: String): BookInfo? {
        return bookDao?.findByIsbn13(isbn13)
    }

    private suspend fun saveBooksToDb(books: List<Book>) {
        for (book in books) {
            val dbBook = bookDao?.findByIsbn13(book.isbn13)
            if (dbBook == null) {
                val bookInfo = ApiService.getInstance().getBookByISBN(book.isbn13)
                bookInfo.body()?.let { bookDao!!.insert(it) }
            }
        }
    }

    private fun convertFavouritesToBooks(favourites: List<Favourites>?): List<Book> {
        val books = mutableListOf<Book>()
        if (favourites != null) {
            for (favBook in favourites) {
                val bookInfo = bookDao!!.findById(favBook.book_id)
                books.add(convertBookInfoToBook(bookInfo))
            }
        }
        return books
    }

    private fun convertBookInfoToBook(bookInfo:BookInfo):Book{
        return Book(bookInfo.title, bookInfo.subtitle, bookInfo.isbn13,
                bookInfo.price, bookInfo.pages, bookInfo.image, bookInfo.url)
    }
}