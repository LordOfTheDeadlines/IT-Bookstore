package com.abramchuk.itbookstore.manager

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.ui.BookClickListener
import com.abramchuk.itbookstore.ui.foundBooks.FoundBooksAdapter

class UiHelper {
    companion object {
        fun updateAdapter(view: View, rv: RecyclerView, animes: List<Book>?) {
//            val adapter = FoundBooksAdapter()
//            rv.layoutManager = GridLayoutManager(view.context, 2)
//            rv.adapter = adapter
//
//            if (animes != null) {
//                adapter.update(animes)
//            }
        }
    }
}