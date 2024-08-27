package com.pss_dev.inventrix_blg.viewModel

import androidx.lifecycle.ViewModel
import com.pss_dev.inventrix_blg.Localdb.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _users = userRepository.getAllUsers()
//    private var _username = "Pratik"
//    private var _password = "123456"
//
//    private var _isLoggedIn = mutableStateOf(false)
//
//    fun validateCredentials(username: String, password: String): Boolean {
//        return username == _username && password == _password
//    }
//
//    fun login() {
//        _isLoggedIn.value = true
//    }
//
//    fun isLoggedIn(): Boolean {
//        return _isLoggedIn.value
//    }
//
//    fun getusername(): String {
//        return _username
//    }
}