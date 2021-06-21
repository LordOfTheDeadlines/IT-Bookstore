package com.abramchuk.itbookstore.ui.bookinfo

import android.content.Intent
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.abramchuk.itbookstore.databinding.FragmentBookInfoBinding
import com.abramchuk.itbookstore.manager.BookManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class BookInfoFragment : Fragment() {
    private var binding: FragmentBookInfoBinding?=null
    var navController: NavController?=null
    private lateinit var bookId: String
    lateinit var bookManager: BookManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookId = requireArguments().getString("book_id").toString()
        Log.d("TEST_RESP", "id = "+bookId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.navController = Navigation.findNavController(view)
        bookManager = BookManager(requireContext())

        GlobalScope.launch(Dispatchers.IO) {
            val book = bookManager.getBooksISBN13(bookId)
            val isFavBtn = binding?.isFavouriteBtn
            val shareBtn = binding?.shareBtn
            var isFav:Boolean? = null
            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    isFav = book?.let { bookManager.isFavourite(it) } == true
                    withContext(Main){
                        isFavBtn?.isChecked = isFav as Boolean
                    }
                }
            }

            shareBtn?.setOnClickListener {
                val myIntent = Intent(Intent.ACTION_SEND);
                myIntent.type = "text/plain";
                val body = "Your body here";
                val sub = "Your Subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"))
            }

            withContext(Dispatchers.Main) {
                Log.d("TEST_RESP", book.toString())
                binding?.title?.text = book?.title
                binding?.subtitle?.text = book?.subtitle
                binding?.authors?.text = book?.authors
                binding?.ratingBar?.rating = book?.rating?.toFloat()!!
                binding?.description?.text = book.desc
                binding?.publisher?.text = book.publisher
                binding?.link?.text = book.url
                binding?.link?.let { Linkify.addLinks(it,Linkify.WEB_URLS) }
                binding?.image?.let { Glide
                        .with(view)
                        .load(book.image)
                        .override(450, 650)
                        .centerCrop()
                        .into(it) }

                isFavBtn?.setOnClickListener {
                    if(isFavBtn.isChecked){
                        GlobalScope.launch(Dispatchers.IO) {
                            withContext(Dispatchers.IO) {
                                bookManager.likeBook(book)
                            }
                        }
                    }
                    else{
                        GlobalScope.launch(Dispatchers.IO) {
                            withContext(Dispatchers.IO) {
                                bookManager.dislikeBook(book)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentBookInfoBinding.inflate(inflater, container, false)
        return binding!!.root
    }
}