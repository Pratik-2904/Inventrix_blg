package com.pss_dev.inventrix_blg.data.localDb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pss_dev.inventrix_blg.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

//    @Query("SELECT * FROM users")
//    suspend fun getAllUsers() : Flow<List<User>>

    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getLoggedInUser(): User?

    @Query("UPDATE users SET isLoggedIn = :status WHERE id = :userId")
    suspend fun updateLoginStatus(userId: Int, status: Boolean)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun authenticateUser(username: String, password: String): User?

}