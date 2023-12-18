package com.example.dactaphanmem.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.UserInfo
import com.example.dactaphanmem.presentation.AppPreferences
import com.example.dactaphanmem.presentation.MockDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SignUpViewModel : ViewModel() {

    private var database = AppDatabase.instance!!
    private val userDao = database.getUserDao()

    private var _signUpState = MutableStateFlow<Boolean?>(null)
    val signUpState = _signUpState.asStateFlow()

    init {

    }

    fun signUp(userName: String, password: String, phoneNumber: String, grade: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (userDao.checkNameUser(name = userName) != null) {
                _signUpState.value = false
            } else {
                val userInfo = UserInfo(
                    userId = UUID.randomUUID().toString(),
                    userName = userName,
                    userPassword = password,
                    userPhoneNumber = phoneNumber,
                    userGrade = grade
                )
                if (AppPreferences.userInfo != null && AppPreferences.userInfo!!.userId != userInfo.userId) {
                    database.getMyBookDao().deleteAllMyBook()
                    database.getViolateDao().deleteAllViolate()
                    delay(500)
                }
                AppPreferences.userInfo = userInfo
                userDao.insertUser(userInfo)
                MockDatabase.addMyBook()
                MockDatabase.addViolate()
                _signUpState.value = true
            }
        }
    }

    fun reset() {
        _signUpState.value = null
    }
}
