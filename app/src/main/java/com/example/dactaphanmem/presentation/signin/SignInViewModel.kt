package com.example.dactaphanmem.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.presentation.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private var userDao = AppDatabase.instance!!.getUserDao()

    private var _signInState = MutableStateFlow<Boolean?>(null)
    val signInState = _signInState.asStateFlow()

    init {

    }

    fun signIn(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (userDao.getUserInfo(userName, password) != null) {
                AppPreferences.userInfo = userDao.getUserInfo(userName, password)!!
                _signInState.value = true
            } else {
                _signInState.value = false
            }
        }
    }

    fun reset() {
        _signInState.value = null
    }
}
