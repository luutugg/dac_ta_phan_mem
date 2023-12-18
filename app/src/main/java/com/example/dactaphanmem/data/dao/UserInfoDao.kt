package com.example.dactaphanmem.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.dactaphanmem.data.entity.UserInfo
import com.example.dactaphanmem.data.relation.UserInfoWithMyBookViolateList
import com.example.dactaphanmem.presentation.AppPreferences

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userInfo: UserInfo)

    @Update
    fun updateUser(userInfo: UserInfo)

    @Query("select * from userinfo")
    fun getAllUser(): List<UserInfo>

    @Query("select * from userinfo where userName = :name")
    fun checkNameUser(name: String): UserInfo?

    @Query("select * from userinfo where userName = :userName and userPassword = :password")
    fun getUserInfo(userName: String, password: String): UserInfo?

    @Transaction
    @Query("SELECT * FROM userinfo where userId = :userId")
    fun getMyBookViolateList(userId: String = AppPreferences.userInfo!!.userId): UserInfoWithMyBookViolateList
}
