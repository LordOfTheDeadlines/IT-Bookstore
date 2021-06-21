package com.abramchuk.itbookstore.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abramchuk.itbookstore.dto.Favourites


@Dao
interface FavouritesDAO {
    @Insert
    fun insert(favourites: Favourites)

    @Query("DELETE FROM favourites")
    fun deleteAll()

    @Query("DELETE FROM favourites WHERE id= :id")
    fun deleteBook(id : Int)

    @Query("DELETE FROM favourites WHERE user_id = :user_id AND book_id=:book_id")
    fun deleteUserFavourite(user_id : Int, book_id: Int)

    @Query("SELECT * FROM favourites WHERE user_id = :user_id")
    fun getUserFavourites(user_id: Int): List<Favourites>

    @Query("SELECT * FROM favourites WHERE user_id = :user_id AND book_id=:book_id")
    fun findUserFavourite(user_id: Int, book_id:Int): List<Favourites>

}