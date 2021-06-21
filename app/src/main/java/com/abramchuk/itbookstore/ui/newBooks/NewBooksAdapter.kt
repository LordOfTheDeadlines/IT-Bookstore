package com.abramchuk.itbookstore.ui.newBooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.databinding.RvItemBinding
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.ui.BookClickListener
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView

class NewBooksAdapter(val list: List<Book>, var cellClickListener: BookClickListener) : RecyclerView.Adapter<NewBooksAdapter.BookHolder>() {

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
    inner class BookHolder(private val binding: RvItemBinding) :
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
}