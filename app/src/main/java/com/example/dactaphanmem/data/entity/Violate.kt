package com.example.dactaphanmem.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Violate (
    @PrimaryKey
    var violateId: String,

    var violateName: String? = null,

    var myBookViolateId: String
) : Parcelable
