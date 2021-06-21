package com.abramchuk.itbookstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.databinding.RvItemBinding
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.ui.BookClickListener

class BooksAdapter(val list: List<Book>, var cellClickListener: BookClickListener) : RecyclerView.Adapter<BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
                RvItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
        )
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(list[position], cellClickListener)
    }
}