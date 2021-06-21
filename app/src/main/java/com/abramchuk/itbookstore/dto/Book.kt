package com.abramchuk.itbookstore.dto

import androidx.room.Entity

data class Book(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val pages: String,
    val image: String,
    val url: String
)