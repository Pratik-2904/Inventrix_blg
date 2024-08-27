package com.pss_dev.inventrix_blg.Localdb

import com.pss_dev.inventrix_blg.Localdb.dao.UserDao
import com.pss_dev.inventrix_blg.data.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userdao: UserDao) {
    suspend fun getAllUsers(): Flow<List<User>> {
        return userdao.getAllUsers()
    }

    suspend fun insertUser(user: User) {
        return userdao.insertUser(user = user)
    }

    suspend fun deleteUser(user: User) {
        return userdao.deleteUser(user = user)
    }

    suspend fun updateUser(user: User) {
        return userdao.updateUser(user = user)
    }

    suspend fun getLoggedInUser(): User? {
        return userdao.getLoggedInUser()
    }

    suspend fun authenticateUser(username: String, password: String): User? {
        return userdao.authenticateUser(username, password)
    }

    suspend fun setLoginStatus(userId: Int, status: Boolean) {
        userdao.updateLoginStatus(userId, status)
    }

}