package com.example.dactaphanmem.presentation.violate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.data.entity.History
import com.example.dactaphanmem.data.entity.Violate
import com.example.dactaphanmem.data.relation.MyBookWithViolate
import com.example.dactaphanmem.presentation.AppPreferences
import com.example.dactaphanmem.presentation.MockDatabase
import com.example.dactaphanmem.presentation.util.TimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

class ViolateViewModel : ViewModel() {

    private val database = AppDatabase.instance!!

    private var _violateState = MutableStateFlow<List<MyBookWithViolate>?>(null)
    val violateState = _violateState.asStateFlow()

    init {
        getAllViolate()
    }

    private fun getAllViolate() {
        viewModelScope.launch(Dispatchers.IO) {
            val listData =
                database.getUserDao().getMyBookViolateList().myBookViolateList?.filter { v ->
                    v.myBook.myBookDateGiveBack != null && TimeUtils.checkGreaterTime(v.myBook.myBookDateGiveBack!!)
                }
            _violateState.value = listData
        }
    }

    fun resolveViolate(myBookWithViolate: MyBookWithViolate) {
        viewModelScope.launch(Dispatchers.IO) {
            database.getMyBookDao().deleteMyBook(myBookWithViolate.myBook.myBookId.toString())
            database.getViolateDao().deleteViolate(myBookWithViolate.violate?.violateId.toString())
            val book = database.getBookDao().getBook(myBookWithViolate.myBook.myBookId, myBookWithViolate.myBook.myBookName.toString())
            if (book != null) {
                val newBook = book.copy()
                newBook.bookCount =
                    (myBookWithViolate.myBook.myBookCount ?: 0) + newBook.bookCount!!
                database.getBookDao().updateBook(newBook)
            } else {
                database.getBookDao().insertBook(
                    Book(
                        bookId = myBookWithViolate.myBook.myBookId,
                        bookName = myBookWithViolate.myBook.myBookName,
                        bookCount = myBookWithViolate.myBook.myBookCount,
                        bookAuthor = myBookWithViolate.myBook.myBookAuthor,
                        bookYear = myBookWithViolate.myBook.myBookYear
                    )
                )
            }

            database.getHistory().insertHistory(
                History(
                    historyId = UUID.randomUUID().toString(),
                    userId = AppPreferences.userInfo!!.userId,
                    userName = AppPreferences.userInfo!!.userName,
                    bookName = myBookWithViolate.myBook.myBookName,
                    bookCount = myBookWithViolate.myBook.myBookCount,
                    historyDate = TimeUtils.convertTimeDactaToDDMMYY(Calendar.getInstance().time),
                    historyType = false,
                    userPhone = AppPreferences.userInfo!!.userPhoneNumber,
                    bookTime = myBookWithViolate.myBook.myBookNumberOder
                )
            )

            getAllViolate()
        }
    }
}
