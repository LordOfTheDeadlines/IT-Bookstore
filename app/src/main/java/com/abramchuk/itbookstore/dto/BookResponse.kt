package com.abramchuk.itbookstore.dto

data class BookResponse(
    val total: String,
    val page: String,
    val books: List<Book>)
