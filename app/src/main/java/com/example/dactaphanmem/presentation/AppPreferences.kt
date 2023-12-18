package com.example.dactaphanmem.presentation

import android.content.Context
import android.content.SharedPreferences
import com.example.dactaphanmem.common.STRING_DEFAULT
import com.example.dactaphanmem.data.entity.UserInfo
import com.google.gson.Gson

object AppPreferences {
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences
    private lateinit var gson: Gson

    private const val FIRST_USE = "FIRST_USE"
    private const val USER_INFO_KEY = "USER_INFO_KEY"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(context.packageName, MODE)
        gson = Gson()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    private inline fun SharedPreferences.commit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.commit()
    }

    var isFirst: Boolean
        get() = preferences.getBoolean(FIRST_USE, false)
        set(value) = preferences.edit {
            it.putBoolean(FIRST_USE, value)
        }

    var userInfo: UserInfo?
        get() {
            return gson.fromJson<UserInfo>(preferences.getString(USER_INFO_KEY, STRING_DEFAULT), UserInfo::class.java)
        }
        set(value) = preferences.edit {
            it.putString(USER_INFO_KEY,  gson.toJson(value))
        }
}
