package com.example.dactaphanmem.data.relation

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.dactaphanmem.data.entity.MyBook
import com.example.dactaphanmem.data.entity.Violate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyBookWithViolate(
    @Embedded var myBook: MyBook,

    @Relation(
        parentColumn = "myBookId",
        entityColumn = "myBookViolateId"
    )
    var violate: Violate? = null
) : Parcelable
