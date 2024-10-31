package com.pss_dev.inventrix_blg.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pss_dev.inventrix_blg.data.localDb.PartyRepositoryImpl
import com.pss_dev.inventrix_blg.data.localDb.dao.PartyDao
import com.pss_dev.inventrix_blg.data.model.PartyEntity
import com.pss_dev.inventrix_blg.repository.PartyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
    entities = [PartyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun partyDao(): PartyDao
}

// 2. DatabaseModule for providing Room Database and DAO
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "inventrix_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePartyDao(database: AppDatabase): PartyDao {
        return database.partyDao()
    }
}

// 3. Repository Module
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPartyRepository(
        partyRepositoryImpl: PartyRepositoryImpl
    ): PartyRepository
}