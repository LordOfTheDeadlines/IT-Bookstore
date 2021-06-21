package com.abramchuk.itbookstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abramchuk.itbookstore.dto.BookInfo
import com.abramchuk.itbookstore.dto.Favourites
import com.abramchuk.itbookstore.dto.User

@Database(entities = [BookInfo::class, Favourites::class, User::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getBookDAO(): BookDAO
    abstract fun getFavouritesDAO(): FavouritesDAO
    abstract fun getUserDAO(): UserDAO
    companion object {
        fun createDb(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "book").build()

    }
}