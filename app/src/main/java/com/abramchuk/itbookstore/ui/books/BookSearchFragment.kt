package com.abramchuk.itbookstore.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.databinding.FragmentBookSearchBinding

class BookSearchFragment : Fragment() {

    private var navController: NavController?=null
    private var binding: FragmentBookSearchBinding?=null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View{
        binding = FragmentBookSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val inputStr = binding?.inputStr
        val searchButton = binding?.searchBtn
        searchButton?.setOnClickListener{
            val text = inputStr?.text.toString()
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