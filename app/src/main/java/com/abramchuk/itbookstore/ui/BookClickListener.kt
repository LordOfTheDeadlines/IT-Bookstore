package com.abramchuk.itbookstore.ui

import com.abramchuk.itbookstore.dto.Book

interface BookClickListener {
    fun onCellClickListener(item: Book, position: Int)
}