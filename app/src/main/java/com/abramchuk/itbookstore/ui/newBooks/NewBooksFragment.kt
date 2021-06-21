package com.abramchuk.itbookstore.ui.newBooks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.App
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.databinding.FragmentNewBooksBinding
import com.abramchuk.itbookstore.ui.BookClickListener
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.manager.BookManager
import com.abramchuk.itbookstore.manager.NetworkManager
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
    private val newBooksViewModel: NewBooksViewModel by viewModels() {
        App.INSTANCE.diContainer.homeViewModelFactory
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
//    override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ) = FragmentNewBooksBinding.inflate(inflater, container, false).also {
//        binding = it
//        val progressBar = binding.progressBar
//        val swipeContainer = binding.swipeContainer
//        val nm = context?.let { NetworkManager(it) }
//        val connected = nm?.isConnectedToInternet
//        swipeContainer.setOnRefreshListener {
//            newBooksViewModel.loadBooks()
//        }
//        newBooksViewModel.book.observe(viewLifecycleOwner, Observer { books ->
//            swipeContainer.isRefreshing = books.isLoading
//            progressBar.visibility = if (books.isLoading) View.VISIBLE
//            else View.INVISIBLE
//
//            if(books.error.isNullOrEmpty()) books.data?.let { showUi(it.books) }
//            else showError(books.error)
//        })
//    }.root

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewBooksBinding.inflate(inflater, container, false)
        bookManager = BookManager(requireContext())

        val view = binding.root
        val nm = context?.let { NetworkManager(it) }
        val connected = nm?.isConnectedToInternet

        GlobalScope.launch(Dispatchers.IO) {
            val books = if (connected!!) {
                bookManager.downloadNewBooks()
            } else {
                listOf<Book>()
            }
            withContext(Dispatchers.Main) {
                showUi(books)
            }
        }

        return view
    }

    private fun showError(error: String?) {
        Toast.makeText(requireContext(), "Error!! $error", Toast.LENGTH_LONG).show()
    }

    private fun showUi(data: List<Book>) {
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.adapter = NewBooksAdapter(data, this@NewBooksFragment)
    }

    override fun onCellClickListener(item: Book, position: Int) {
        Log.d("TEST_RESP", "click")
        val bundle = bundleOf("book_id" to item.isbn13)
        navController!!.navigate(
            R.id.action_booksFragment_to_bookInfoFragment,
            bundle
        )
    }
}