package com.example.dactaphanmem.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey
    var historyId: String,

    var userId: String,

    var userName: String? = null,

    var userPhone: String? = null,

    var bookName: String? = null,

    var bookCount: Int? = null,

    var bookTime: Int? = null,

    var historyDate: String? = null,

    var historyType: Boolean = false // false: trả , true: mượn
)
