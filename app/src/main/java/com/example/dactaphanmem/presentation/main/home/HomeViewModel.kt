package com.example.dactaphanmem.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.presentation.MockDatabase
import com.example.dactaphanmem.presentation.util.TimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var database = AppDatabase.instance!!

    private var _bookState = MutableStateFlow<List<Book>?>(null)
    val bookState = _bookState.asStateFlow()

    init {
        MockDatabase.mockData()
    }

    fun getAllBook() {
        viewModelScope.launch(Dispatchers.IO) {
            _bookState.value = database.getBookDao().getAllBook()
        }
    }

    fun onUpdateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = _bookState.value?.toMutableList()
            val index = list?.indexOfFirst {
                it.bookId == book.bookId
            }
            if (index != null && index >= 0) {
                list[index] = book
                _bookState.value = list
            }
        }
    }

    fun checkViolate(): Boolean {
        val listData = database.getUserDao().getMyBookViolateList().myBookViolateList?.filter { v ->
            v.myBook.myBookDateGiveBack != null && TimeUtils.checkGreaterTime(v.myBook.myBookDateGiveBack!!)
        }
        return !listData.isNullOrEmpty()
    }

    fun deleteBook(bookId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.getBookDao().deleteBook(bookId)
            val list = _bookState.value?.toMutableList()
            val index = list?.indexOfFirst {
                it.bookId == bookId
            }
            if (index != null && index >= 0) {
                list.removeAt(index)
                _bookState.value = list
            }
        }
    }

}
