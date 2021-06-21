package com.abramchuk.itbookstore.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.databinding.FragmentRegistrationBinding
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.db.UserDAO
import com.abramchuk.itbookstore.dto.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationFragment:Fragment() {
    private var binding: FragmentRegistrationBinding?=null
    private var navController: NavController?=null
    private var userDAO : UserDAO? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        userDAO = AppDatabase.createDb(requireContext()).getUserDAO()
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val inputUser = binding?.loginEditText
        val inputEmail = binding?.emailEditText
        val inputPass = binding?.passwordEditText
        binding?.loginBtn?.setOnClickListener{
            val login = inputUser?.text.toString()
            val email = inputEmail?.text.toString()
            val password = inputPass?.text.toString()
            registration(login, email, password)
        }
    }

    private fun registration(username: String, email: String, pass: String){
        var usersFromDB : List<User>? = null
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                usersFromDB = userDAO?.getUserByLogin(username)
            }
        }
        if (usersFromDB.isNullOrEmpty() && username.isNotEmpty() && pass.isNotEmpty() && email.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    userDAO?.insert(User(username, email, pass, false))
                }
            }
            navController!!.navigate(R.id.action_regFragment_to_logFragment)
        }
    }
}