package com.example.dactaphanmem.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dactaphanmem.data.entity.Book

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    @Update
    fun updateBook(book: Book)

    @Query("delete from book where bookId =:bookId")
    fun deleteBook(bookId: String)

    @Query("select * from book")
    fun getAllBook(): List<Book>

    @Query("select * from book where bookId =:id and bookName =:name")
    fun getBook(id: String, name: String): Book?

    @Query("delete from book")
    fun deleteAllBook()
}
