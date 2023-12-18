package com.example.dactaphanmem.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class MyBook (
    @PrimaryKey
    var myBookId: String,

    var myBookName: String? = null,

    var myBookCount: Int? = null,

    var myBookAuthor: String? = null,

    var myBookYear: String? = null,

    var myBookDateGiveBack: String? = null,

    var myBookNumberOder: Int? = null,

    var userInfoBookId: String
) : Parcelable
