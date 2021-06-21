package com.abramchuk.itbookstore.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookInfo (
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    val isbn10: String,
    val isbn13: String,
    val pages: String,
    val year: String,
    val rating: String,
    val desc: String,
    val price: String,
    val image: String,
    val url: String,
//    val pdf: List<Dictionary<String, String>>
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}