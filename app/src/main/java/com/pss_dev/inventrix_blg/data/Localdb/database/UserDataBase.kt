package com.pss_dev.inventrix_blg.data.Localdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pss_dev.inventrix_blg.data.Localdb.dao.UserDao
import com.pss_dev.inventrix_blg.data.model.User

@Database(
    entities = [User::class],
    version = 1,
    //Todo how to get export schema
    exportSchema = true
)
//@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userdao(): UserDao

    //we are using dagger hilt for dependency injection
    //so below code will be of no use

    //For not using the dagger hilt
//    companion object {
//        @Volatile
//        private var INSTANCE: UserDataBase? = null
//
//        fun getDatabase(context: Context): UserDataBase {
//            //cheks if the instance of database is running or not
//            // if yes then returns it
//            // else create new instance with database builder
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDataBase::class.java,
//                    "user_database"
//                ).build()
//
//                INSTANCE = instance
//                instance
//            }
//
//        }
//    }

}
