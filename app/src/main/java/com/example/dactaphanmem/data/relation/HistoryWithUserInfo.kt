package com.example.dactaphanmem.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.dactaphanmem.data.entity.History
import com.example.dactaphanmem.data.entity.UserInfo

//data class HistoryWithUserInfo (
//    @Embedded val history: History? = null,
//
//    @Relation(
//        entity = UserInfo::class,
//        parentColumn = "historyId",
//        entityColumn = "historyUserId"
//    )
//    val historyUserInfoListMyBook: UserWithListMyBook? = null
//)
