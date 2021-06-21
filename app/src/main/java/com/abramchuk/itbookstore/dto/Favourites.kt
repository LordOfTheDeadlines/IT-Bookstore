package com.abramchuk.itbookstore.dto

import androidx.room.ColumnInfo
import androidx.room.ForeignKey.CASCADE
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "favourites", foreignKeys = [
    ForeignKey(entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id"),
            onDelete = CASCADE),
    ForeignKey(entity = BookInfo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("book_id"),
            onDelete = CASCADE)]
)
data class Favourites (
        @ColumnInfo(name = "user_id")
        val user_id: Int,

        @ColumnInfo(name = "book_id")
        val book_id: Int,

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
)