package com.abramchuk.itbookstore

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.db.BookDAO
import com.abramchuk.itbookstore.db.UserDao
import com.abramchuk.itbookstore.dto.BookInfo
import com.abramchuk.itbookstore.dto.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseUnitTest {
    private lateinit var bookDAO: BookDAO
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext;
        bookDAO = AppDatabase.createDb(context).getBookDAO()
        userDao = AppDatabase.createDb(context).getUserDAO()
    }

    @Test
    fun insert_anime_isCorrect() {
        val book = BookInfo("title","subtitle","Author", "publisher",
                "123","12345","100", "2018","5.00","desc",
                "10.00$","image","url")
        GlobalScope.launch(Dispatchers.IO) {
            bookDAO.insert(book)
            val dbBook = bookDAO.findByIsbn13("12345")
//            bookDAO.deleteAnimeById(dbBook.id)
            assertEquals(book.title, dbBook.title)
        }
    }

    @Test
    fun delete_user_isCorrect() {
        val user = User("admin", "admin@admin.com", "admin", false)
        GlobalScope.launch(Dispatchers.IO) {
            userDao.insert(user)
            userDao.deleteUserByLogin(user.login)
            val deleted_user = userDao.getUserByLogin(user.login)
            assertEquals(null, deleted_user)
        }
    }
}