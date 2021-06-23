package com.abramchuk.itbookstore.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.databinding.RvItemBinding
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.ui.BookClickListener
import com.bumptech.glide.Glide

class BookHolder(private val binding: RvItemBinding) :
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