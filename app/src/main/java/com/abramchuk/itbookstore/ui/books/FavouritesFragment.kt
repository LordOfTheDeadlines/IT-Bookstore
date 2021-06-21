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
import com.abramchuk.itbookstore.databinding.FragmentFavouritesBinding
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.db.UserDAO
import com.abramchuk.itbookstore.dto.Book
import com.abramchuk.itbookstore.dto.User
import com.abramchuk.itbookstore.manager.BookManager
import com.abramchuk.itbookstore.ui.BookClickListener
import com.abramchuk.itbookstore.ui.adapter.BooksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavouritesFragment : Fragment(), BookClickListener {
    private var navController: NavController?=null
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var bookManager: BookManager
    private lateinit var user: User
    private var userDAO : UserDAO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookManager = BookManager(requireContext())
        userDAO = AppDatabase.createDb(requireContext()).getUserDAO()
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

        binding.logoutBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    userDAO?.deactivateAll()
                }
            }
            navController!!.navigate(R.id.action_favFragment_to_logFragment)
        }

        GlobalScope.launch(Dispatchers.IO) {
            val books = bookManager.getFavourites()
            withContext(Main) {
                showUi(books)
            }
        }
        return binding.root
    }

    private fun showUi(data: List<Book>) {
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.adapter = BooksAdapter(data, this@FavouritesFragment)
    }

    override fun onCellClickListener(item: Book, position: Int) {
        val bundle = bundleOf("book_id" to item.isbn13)
        navController!!.navigate(
                R.id.action_favFragment_to_bookInfoFragment,
                bundle
        )
    }
}