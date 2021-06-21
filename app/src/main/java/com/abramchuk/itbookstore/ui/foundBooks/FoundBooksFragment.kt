package com.abramchuk.itbookstore.ui.foundBooks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.abramchuk.itbookstore.ApiService
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.databinding.FragmentFoundBooksBinding
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.manager.BookManager
import com.abramchuk.itbookstore.manager.NetworkManager
import com.abramchuk.itbookstore.modules.BookSearchViewModelFactory
import com.abramchuk.itbookstore.ui.BookClickListener
import com.abramchuk.itbookstore.ui.newBooks.NewBooksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FoundBooksFragment : Fragment(), BookClickListener {
    var navController: NavController?=null
    private lateinit var viewModel: FoundBooksViewModel
    private lateinit var binding: FragmentFoundBooksBinding
    private lateinit var searchStr: String
    lateinit var bookManager: BookManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchStr = requireArguments().getString("searchStr").toString()
        Log.d("TEST_RESP", "searchStr = "+searchStr)
    }

//    override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ) = FragmentFoundBooksBinding.inflate(inflater, container, false).also {
//        binding = it
//        bookManager = BookManager(requireContext())
//    }.root
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val factory = BookSearchViewModelFactory(ApiService.getInstance(), searchStr)
//        navController = Navigation.findNavController(view)
//        viewModel = ViewModelProvider(this, factory).get(FoundBooksViewModel::class.java)
//
//        val bookSearchAdapter = FoundBooksAdapter(this@FoundBooksFragment)
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.setHasFixedSize(true)
//
//        binding.recyclerView.adapter = bookSearchAdapter
//
//        lifecycleScope.launch {
//            viewModel.books.collectLatest { pagedData ->
//                bookSearchAdapter.submitData(pagedData)
//            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bookManager = BookManager(requireContext())
        val nm = context?.let { NetworkManager(it) }
        val connected = nm?.isConnectedToInternet
        val factory = BookSearchViewModelFactory(requireContext(),ApiService.getInstance(), searchStr)
        viewModel = ViewModelProvider(this, factory).get(FoundBooksViewModel::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            val books : List<Book>
            if (connected!!) {
                val bookSearchAdapter = FoundBooksAdapter(this@FoundBooksFragment)
                withContext(Dispatchers.Main) {
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerView.setHasFixedSize(true)
                    binding.recyclerView.adapter = bookSearchAdapter
                }
                lifecycleScope.launch {
                    viewModel.books.collectLatest { pagedData ->
                        bookSearchAdapter.submitData(pagedData)
                    }
                }
            } else {
                books = bookManager.getBooksByName(searchStr)
                val bookSearchAdapter = NewBooksAdapter(books,this@FoundBooksFragment)
                withContext(Dispatchers.Main) {
                    binding.recyclerView.adapter = bookSearchAdapter
                }
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoundBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCellClickListener(item: Book, position: Int) {
        val bundle = bundleOf("book_id" to item.isbn13)
        navController!!.navigate(
            R.id.action_bookSearchFragment_to_bookInfoFragment,
            bundle
        )
    }
}