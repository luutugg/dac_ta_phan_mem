package com.example.dactaphanmem.data.dao

import android.util.TimeUtils
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.dactaphanmem.data.entity.MyBook
import com.example.dactaphanmem.data.relation.MyBookWithViolate

@Dao
interface MyBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyBook(myBook: MyBook)

    @Query("select * from mybook")
    fun getAllMyBook(): List<MyBook>

    @Query("DELETE FROM mybook WHERE myBookId = :id")
    fun deleteMyBook(id: String)

    @Query("DELETE FROM mybook")
    fun deleteAllMyBook()

    @Update
    fun updateMyBook(myBook: MyBook)

    @Query("select * from mybook where myBookId =:id")
    fun checkBookExist(id: String): MyBook?
}
