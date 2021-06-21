package com.abramchuk.itbookstore.ui.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.databinding.FragmentFavouritesBinding
import com.abramchuk.itbookstore.databinding.FragmentNewBooksBinding
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.db.UserDao
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.dto.User
import com.abramchuk.itbookstore.manager.BookManager
import com.abramchuk.itbookstore.manager.NetworkManager
import com.abramchuk.itbookstore.ui.BookClickListener
import com.abramchuk.itbookstore.ui.newBooks.NewBooksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavouritesFragment : Fragment(), BookClickListener {
    var navController: NavController?=null
    private lateinit var viewModel: FavouritesViewModel
    private lateinit var binding: FragmentFavouritesBinding
    lateinit var bookManager: BookManager
    private lateinit var user: User
    var userDao : UserDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookManager = BookManager(requireContext())
        userDao = AppDatabase.createDb(requireContext()).getUserDAO()
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                user = AppDatabase.createDb(requireContext()).getUserDAO().getActiveUser()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        bookManager = BookManager(requireContext())
        val logoutBtn = binding.logoutBtn
        logoutBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    userDao?.deactivateAll()
                }
            }
            navController!!.navigate(R.id.action_favFragment_to_logFragment)
        }
        val view = binding.root

        GlobalScope.launch(Dispatchers.IO) {
            val books = bookManager.getFavourites()
            withContext(Main) {
                showUi(books)
            }
        }

        return view
    }

    private fun showUi(data: List<Book>) {
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.adapter = NewBooksAdapter(data, this@FavouritesFragment)
    }

    override fun onCellClickListener(item: Book, position: Int) {
        Log.d("TEST_RESP", "click, item id = "+item.isbn13)
        val bundle = bundleOf("book_id" to item.isbn13)
        navController!!.navigate(
                R.id.action_favFragment_to_bookInfoFragment,
                bundle
        )
    }
}