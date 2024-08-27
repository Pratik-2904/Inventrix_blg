package com.pss_dev.inventrix_blg.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pss_dev.inventrix_blg.Localdb.UserRepository
import com.pss_dev.inventrix_blg.data.AccessLevel
import com.pss_dev.inventrix_blg.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var _loggedInUser: MutableLiveData<User?> = MutableLiveData()

    val loggedInUser: LiveData<User?> get() = _loggedInUser

    val loggedInUsername: String?
        get() = _loggedInUser.value?.username

    fun checkIfUserIsLoggedIn(): Boolean {
        viewModelScope.launch {
            _loggedInUser.value = userRepository.getLoggedInUser()
        }
        return _loggedInUser.value != null
    }

    fun authenticate(username: String, password: String) {
        viewModelScope.launch {
            val user = userRepository.authenticateUser(username, password)
            if ((user != null)) {
                userRepository.setLoginStatus(
                    userId = user.id,
                    status = true
                )
                _loggedInUser.value = user
            } else {
                _loggedInUser.value = null
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.setLoginStatus(
                userId = _loggedInUser.value!!.id,
                status = false
            )
            _loggedInUser.value = null
        }
    }

    fun signup(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        context: Context
    ) {
        // Basic input validation
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }
        val user = User(
            username = email,
            password = password,
            name = "$firstName $lastName",
            email = email,
            userRole = AccessLevel.ADMIN
        )
        viewModelScope.launch {
            try {
                userRepository.insertUser(user)

            } catch (e: Exception) {
                Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
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