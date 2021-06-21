package com.abramchuk.itbookstore.ui.bookSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.abramchuk.itbookstore.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    var navController: NavController?=null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_book_search, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val inputStr = view.findViewById<EditText>(R.id.inputStr)
        val searchButton =  view.findViewById<Button>(R.id.search_btn)
        searchButton.setOnClickListener{
            val text = inputStr.text.toString()
            searchBooks(text)
        }
    }

    private fun searchBooks(text: String){
        if(text.isNotEmpty()){
            val bundle = bundleOf("searchStr" to text)
            navController!!.navigate(
                R.id.action_dashboardFragment_to_bookSearchFragment,
                bundle
            )
        }
    }
}