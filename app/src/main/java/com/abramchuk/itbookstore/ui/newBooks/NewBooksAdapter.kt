package com.abramchuk.itbookstore.ui.newBooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.ui.BookClickListener
import com.abramchuk.itbookstore.dto.Book
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView

class NewBooksAdapter(val list: List<Book>, var cellClickListener: BookClickListener) : RecyclerView.Adapter<NewBooksAdapter.BookHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
                .apply {
                    return BookHolder(this)
                }
    }


    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(list[position], cellClickListener)
    }

    class BookHolder(val root: View) : RecyclerView.ViewHolder(root) {
        fun bind(item: Book, action: BookClickListener) {
            root.setOnClickListener {
                action.onCellClickListener(item, adapterPosition)
            }
            root.findViewById<MaterialTextView>(R.id.title).text = item.title
            root.findViewById<MaterialTextView>(R.id.price).text = item.price
            root.findViewById<MaterialTextView>(R.id.subtitle).text = item.subtitle
            Glide.with(root)
                    .load(item.image)
                    .override(200, 350)
                    .centerCrop()
                    .into(root.findViewById(R.id.imageView))
        }
    }
}