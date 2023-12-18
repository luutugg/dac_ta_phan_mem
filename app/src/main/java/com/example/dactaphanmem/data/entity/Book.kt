package com.example.dactaphanmem.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Book(
    @PrimaryKey
    var bookId: String,

    var bookName: String? = null,

    var bookCount: Int? = null,

    var bookAuthor: String? = null,

    var bookYear: String? = null
) : Parcelable
