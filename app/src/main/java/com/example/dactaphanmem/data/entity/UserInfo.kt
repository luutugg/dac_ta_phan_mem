package com.example.dactaphanmem.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo (
    @PrimaryKey
    var userId: String,

    var userName: String? = null,

    var userPassword: String? = null,

    var userPhoneNumber: String? = null,

    var userGrade: String? = null,

    var isUserNormal: Boolean = true, // true -> sinh viên | false -> thủ thư,

)
