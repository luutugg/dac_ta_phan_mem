package com.example.dactaphanmem.presentation.main.home.borow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.data.entity.History
import com.example.dactaphanmem.data.entity.MyBook
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

    private var _borrowState = MutableStateFlow<Boolean?>(null)
    val borrowState = _borrowState.asStateFlow()

    init {

    }

    fun borrowBook(book: Book, count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var time = 1
            var oldCount = 0
            if (database.getMyBookDao().checkBookExist(book.bookId) != null) {
                // add my book
                time = database.getMyBookDao().checkBookExist(book.bookId)!!.myBookNumberOder!! + 1
                oldCount = database.getMyBookDao().checkBookExist(book.bookId)!!.myBookCount ?: 0
            }
            database.getMyBookDao().insertMyBook(
                MyBook(
                    myBookId = book.bookId,
                    myBookName = book.bookName,
                    myBookCount = count + oldCount,
                    myBookYear = book.bookYear,
                    myBookAuthor = book.bookAuthor,
                    myBookDateGiveBack = "28/12/2023",
                    userInfoBookId = AppPreferences.userInfo!!.userId,
                    myBookNumberOder = time
                )
            )

            // xóa sách khỏi thư viện
            if (count == book.bookCount) {
                // xóa
                database.getBookDao().deleteBook(book.bookId)
            } else {
                // update
                val newBook = book.copy()
                newBook.bookCount = book.bookCount!! - count
                database.getBookDao().updateBook(newBook)
            }

            // thêm vào lịch sử
            database.getHistory().insertHistory(
                History(
                    historyId = UUID.randomUUID().toString(),
                    userId = AppPreferences.userInfo!!.userId,
                    userName = AppPreferences.userInfo!!.userName,
                    userPhone = AppPreferences.userInfo!!.userPhoneNumber,
                    bookName = book.bookName,
                    bookCount = count,
                    bookTime = time,
                    historyDate = TimeUtils.convertTimeDactaToDDMMYY(Calendar.getInstance().time),
                    historyType = true
                )
            )

            _borrowState.value = true
        }
    }
}
