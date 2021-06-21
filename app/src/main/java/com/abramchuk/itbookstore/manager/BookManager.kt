package com.abramchuk.itbookstore.manager

import android.content.Context
import com.abramchuk.itbookstore.ApiService
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.db.BookDAO
import com.abramchuk.itbookstore.db.FavouritesDAO
import com.abramchuk.itbookstore.db.UserDao
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
    private var userDao: UserDao? = AppDatabase.createDb(context).getUserDAO()
    private var favouritesDao: FavouritesDAO? = AppDatabase.createDb(context).getFavouritesDAO()

    fun likeBook(item: BookInfo) {
        val user = userDao!!.getActiveUser()
        favouritesDao?.insert(Favourites(user.id, item.id))
    }

    fun dislikeBook(item: BookInfo) {
        val user = userDao!!.getActiveUser()
        favouritesDao?.deleteUserFavourite(user.id,item.id)
    }

    fun isFavourite(book:BookInfo): Boolean? {
        val user = userDao!!.getActiveUser()
        val fav = favouritesDao?.findUserFavourite(user.id, book.id)
        return fav?.isNotEmpty()
    }

    fun getFavourites(): List<Book> {
        val user = userDao!!.getActiveUser()
        val favourites = favouritesDao?.getUserFavourites(user.id)
        return convertFavouritesToBooks(favourites)
    }

    suspend fun downloadNewBooks(): List<Book> {
        val listResponse = ApiService.getInstance().getNewBooks()
        val books = listResponse.body()?.books!!
        saveBooksToDb(books)
        return books
    }

    suspend fun downloadBooksByName(animeName: String, page:String): BookResponse {
        val listResponse = ApiService.getInstance().getBooksData(animeName, page)
        val books = listResponse.books
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
               saveBooksToDb(books)
            }
        }
        return listResponse
    }

    fun getBooksByName(title: String): List<Book> {
        val booksInfo = bookDao?.findByTitle(title)
        val books = mutableListOf<Book>()
        if (booksInfo != null) {
            for (bI in booksInfo)
                books.add(convertBookInfoToBook(bI))
        }
        return books
    }

    fun getBooksISBN13(id: String): BookInfo? {
        return bookDao?.findByIsbn13(id)
    }


//    private fun markAnime(item: Anime, labelName: String) {
//        val user = userDao?.getActiveUser()
//        val anime = bookDao?.getAnimeByMalId(item.mal_id)
//        if (user != null && anime != null) {
//            val existingAnime =
//                    userAnimeDao?.getAnimesByUserAnimeAndType(user.id, anime.id, labelName)
//            if (!existingAnime.isNullOrEmpty()) {
//                for (exAnime in existingAnime) {
//                    userAnimeDao?.deleteAnime(exAnime.id)
//                }
//            } else {
//                userAnimeDao?.insert(UserAnime(user.id, anime.id, labelName))
//            }
//        }
//    }

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