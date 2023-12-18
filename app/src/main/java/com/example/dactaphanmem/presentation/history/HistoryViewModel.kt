package com.example.dactaphanmem.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.History
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private var database = AppDatabase.instance!!

    private var _historyState = MutableStateFlow<List<History>?>(null)
    val historyState = _historyState.asStateFlow()

    init {
        getAllHistory()
    }

    private fun getAllHistory() {
        viewModelScope.launch {
            _historyState.value = database.getHistory().getAllHistory()
        }
    }
}
