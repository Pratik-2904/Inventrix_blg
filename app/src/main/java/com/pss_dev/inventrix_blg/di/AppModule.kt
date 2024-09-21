package com.pss_dev.inventrix_blg.di

import android.content.Context
import androidx.room.Room
import com.pss_dev.inventrix_blg.Localdb.UserRepository
import com.pss_dev.inventrix_blg.Localdb.dao.UserDao
import com.pss_dev.inventrix_blg.Localdb.database.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): UserDataBase {
        return Room.databaseBuilder(
            appContext,
            UserDataBase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(userDataBase: UserDataBase): UserDao{
        return  userDataBase.userdao()
    }

    @Provides
    @Singleton
    fun provideRepository(userdao: UserDao): UserRepository {
        return UserRepository(userdao)
    }
}