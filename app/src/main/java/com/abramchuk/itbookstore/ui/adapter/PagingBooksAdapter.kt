package com.abramchuk.itbookstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.databinding.RvItemBinding
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.ui.BookClickListener
import com.bumptech.glide.Glide

class PagingBooksAdapter(private var cellClickListener: BookClickListener): PagingDataAdapter<Book, BookHolder>(BooksComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
                RvItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, cellClickListener) }
    }

    object BooksComparator : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.isbn13 == newItem.isbn13
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}