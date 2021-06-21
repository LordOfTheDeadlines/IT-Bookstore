package com.abramchuk.itbookstore.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abramchuk.itbookstore.dto.User


@Dao
interface UserDAO {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getUsers() : List<User>

    @Query("DELETE FROM users")
    fun deleteAll()

    @Query("DELETE FROM users WHERE login = :login")
    fun deleteUserByLogin(login : String)

    @Query("SELECT * FROM users WHERE login = :login")
    fun getUserByLogin(login : String) : List<User>

    @Query("SELECT * FROM users WHERE login = :login AND password=:password")
    fun findUser(login : String, password:String) : List<User>

    @Query("SELECT * FROM users WHERE isActive = 1")
    fun findActiveUser() : List<User>

    @Query("UPDATE users SET isActive = 1 WHERE id = :userId")
    fun setActive(userId : Int)

    @Query("UPDATE users SET isActive = 0")
    fun deactivateAll()

    @Query("SELECT * FROM users WHERE isActive = 1")
    fun getActiveUser() : User
}