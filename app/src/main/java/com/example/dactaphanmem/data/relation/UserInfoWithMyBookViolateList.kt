package com.example.dactaphanmem.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.dactaphanmem.data.entity.MyBook
import com.example.dactaphanmem.data.entity.UserInfo

data class UserInfoWithMyBookViolateList(
    @Embedded val userInfo: UserInfo,

    @Relation(
        entity = MyBook::class,
        parentColumn = "userId",
        entityColumn = "userInfoBookId"
    )
    val myBookViolateList: List<MyBookWithViolate>? = null
)
