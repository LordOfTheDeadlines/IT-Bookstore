package com.abramchuk.itbookstore.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.databinding.FragmentNewBooksBinding
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.manager.BookManager
import com.abramchuk.itbookstore.manager.NetworkManager
import com.abramchuk.itbookstore.ui.BookClickListener
import com.abramchuk.itbookstore.ui.adapter.BooksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class NewBooksFragment : Fragment(), BookClickListener {
    private lateinit var binding: FragmentNewBooksBinding
    var navController: NavController?=null
    lateinit var bookManager: BookManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewBooksBinding.inflate(inflater, container, false)
        bookManager = BookManager(requireContext())

        val nm = context?.let { NetworkManager(it) }

        GlobalScope.launch(Dispatchers.IO) {
            val books = if (nm?.isConnectedToInternet!!) {
                bookManager.getApiNewBooks()
            } else {
                listOf()
            }
            withContext(Dispatchers.Main) {
                showUi(books)
            }
        }

        return binding.root
    }

    private fun showUi(data: List<Book>) {
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.adapter = BooksAdapter(data, this@NewBooksFragment)
    }

    override fun onCellClickListener(item: Book, position: Int) {
        val bundle = bundleOf("book_id" to item.isbn13)
        navController!!.navigate(
            R.id.action_booksFragment_to_bookInfoFragment,
            bundle
        )
    }
}