package com.abramchuk.itbookstore.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.abramchuk.itbookstore.R
import com.abramchuk.itbookstore.databinding.FragmentAuthorizationBinding
import com.abramchuk.itbookstore.db.AppDatabase
import com.abramchuk.itbookstore.db.UserDAO
import com.abramchuk.itbookstore.dto.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthorizationFragment : Fragment() {
    private var binding: FragmentAuthorizationBinding?=null
    private var navController: NavController?=null
    private var userDAO : UserDAO? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userDAO = AppDatabase.createDb(requireContext()).getUserDAO()
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val inputUser = binding?.loginEditText
        val inputPass = binding?.passwordEditText
        var user: User?
        var usersFromDB: List<User>?
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                usersFromDB = userDAO?.findActiveUser()
                withContext(Main){
                    if (!usersFromDB.isNullOrEmpty()) {
                        user = usersFromDB!![0]
                        navigateUserToGreetingFragment(user!!.login)
                    }
                }
            }
        }

        binding?.loginBtn?.setOnClickListener{
            val login = inputUser?.text.toString()
            val password = inputPass?.text.toString()
            authorize(login, password)
        }
        binding?.regNavBtn?.setOnClickListener {
            navController!!.navigate(R.id.action_logFragment_to_regFragment)
        }
    }

    private fun authorize(username: String, pass: String){
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                val user: User?
                val usersFromDB = userDAO?.findUser(username, pass)
                if (!usersFromDB.isNullOrEmpty()) {
                    user = usersFromDB[0]
                    userDAO?.setActive(user.id)
                    withContext(Main) {
                        navigateUserToGreetingFragment(username)
                    }
                }
            }
        }
    }


    private fun navigateUserToGreetingFragment(user: String){
        val bundle = bundleOf("username" to user)
        navController!!.navigate(
                R.id.action_logFragment_to_userFragment,
                bundle
        )
    }
}