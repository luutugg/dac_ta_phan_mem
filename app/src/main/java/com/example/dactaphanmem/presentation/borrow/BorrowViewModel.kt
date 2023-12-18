package com.example.dactaphanmem.presentation.borrow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.data.entity.History
import com.example.dactaphanmem.data.relation.MyBookWithViolate
import com.example.dactaphanmem.presentation.AppPreferences
import com.example.dactaphanmem.presentation.util.TimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

class BorrowViewModel : ViewModel() {

    private val database = AppDatabase.instance!!

    private var _borrowState = MutableStateFlow<List<MyBookWithViolate>?>(null)
    val borrowState = _borrowState.asStateFlow()

    init {

    }

    fun getAllMyBook() {
        viewModelScope.launch(Dispatchers.IO) {
            _borrowState.value = database.getUserDao().getMyBookViolateList().myBookViolateList
        }
    }

    fun giveBackBook(myBookWithViolate: MyBookWithViolate, count: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val newMyBookWithViolate = myBookWithViolate.copy()
            val list = _borrowState.value?.toMutableList()

            when (count) {
                null -> {
                    database.apply {
                        getMyBookDao().deleteMyBook(myBookWithViolate.myBook.myBookId)
                        getViolateDao().deleteViolate(myBookWithViolate.violate?.violateId.toString())
                    }
                    list?.remove(newMyBookWithViolate)
                }

                myBookWithViolate.myBook.myBookCount -> {
                    database.apply {
                        getMyBookDao().deleteMyBook(myBookWithViolate.myBook.myBookId)
                        getViolateDao()
                            .deleteViolate(myBookWithViolate.violate?.violateId.toString())
                    }
                    list?.remove(newMyBookWithViolate)
                }

                else -> {
                    val newCount = myBookWithViolate.myBook.myBookCount!! - count
                    val newBook = newMyBookWithViolate.myBook.copy()
                    newBook.myBookCount = newCount
                    newMyBookWithViolate.myBook = newBook
                    database.getMyBookDao().updateMyBook(newMyBookWithViolate.myBook)
                    val index = list?.indexOfFirst {
                        it.myBook.myBookId == newMyBookWithViolate.myBook.myBookId
                    }
                    if (index != null && index >= 0) {
                        list[index] = newMyBookWithViolate
                    }
                }
            }

            _borrowState.value = list

            val book = database.getBookDao().getBook(newMyBookWithViolate.myBook.myBookId, newMyBookWithViolate.myBook.myBookName.toString())
            database.getBookDao().getAllBook()
            if (book != null) {
                val newBook = book.copy()
                newBook.bookCount =
                    (newMyBookWithViolate.myBook.myBookCount ?: 0) + newBook.bookCount!!
                database.getBookDao().updateBook(newBook)
            } else {
                database.getBookDao().insertBook(
                    Book(
                        bookId = newMyBookWithViolate.myBook.myBookId,
                        bookName = newMyBookWithViolate.myBook.myBookName,
                        bookCount = newMyBookWithViolate.myBook.myBookCount,
                        bookAuthor = newMyBookWithViolate.myBook.myBookAuthor,
                        bookYear = newMyBookWithViolate.myBook.myBookYear
                    )
                )
            }

            database.getHistory().insertHistory(
                History(
                    historyId = UUID.randomUUID().toString(),
                    userId = AppPreferences.userInfo!!.userId,
                    userName = AppPreferences.userInfo!!.userName,
                    bookName = newMyBookWithViolate.myBook.myBookName,
                    bookCount = newMyBookWithViolate.myBook.myBookCount,
                    historyDate = TimeUtils.convertTimeDactaToDDMMYY(Calendar.getInstance().time),
                    historyType = false,
                    userPhone = AppPreferences.userInfo!!.userPhoneNumber,
                    bookTime = newMyBookWithViolate.myBook.myBookNumberOder
                )
            )
        }
    }

}
