package com.abramchuk.itbookstore.ui.foundBooks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.databinding.RvItemBinding
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.ui.BookClickListener
import com.bumptech.glide.Glide

class FoundBooksAdapter(private var cellClickListener: BookClickListener): PagingDataAdapter<Book, FoundBooksAdapter.BooksViewHolder>(BooksComparator) {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): BooksViewHolder {
        return BooksViewHolder(
                RvItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, cellClickListener) }
    }

    inner class BooksViewHolder(private val binding: RvItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book, action: BookClickListener) = with(binding) {
            root.setOnClickListener {
                action.onCellClickListener(item, adapterPosition)
            }
            binding.title.text = item.title
            binding.price.text = item.price
            binding.subtitle.text = item.subtitle
            Glide.with(root)
                    .load(item.image)
                    .override(200, 350)
                    .centerCrop()
                    .into(binding.imageView)
        }
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