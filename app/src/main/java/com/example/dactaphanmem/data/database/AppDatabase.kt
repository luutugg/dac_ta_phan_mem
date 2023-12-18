package com.example.dactaphanmem.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dactaphanmem.data.dao.BookDao
import com.example.dactaphanmem.data.dao.HistoryDao
import com.example.dactaphanmem.data.dao.MyBookDao
import com.example.dactaphanmem.data.dao.UserInfoDao
import com.example.dactaphanmem.data.dao.ViolateDao
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.data.entity.History
import com.example.dactaphanmem.data.entity.MyBook
import com.example.dactaphanmem.data.entity.UserInfo
import com.example.dactaphanmem.data.entity.Violate

@Database(entities = [UserInfo::class, Book::class, MyBook::class, Violate::class, History::class], version = 7)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME: String = "Đặc tả phần mềm"

        var instance: AppDatabase? = null

        fun createDatabase(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()
            }
            return instance!!
        }
    }

    abstract fun getUserDao(): UserInfoDao
    abstract fun getBookDao(): BookDao
    abstract fun getViolateDao(): ViolateDao
    abstract fun getMyBookDao(): MyBookDao
    abstract fun getHistory(): HistoryDao
}
