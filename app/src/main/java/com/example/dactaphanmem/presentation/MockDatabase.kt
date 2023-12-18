package com.example.dactaphanmem.presentation

import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.data.entity.MyBook
import com.example.dactaphanmem.data.entity.UserInfo
import com.example.dactaphanmem.data.entity.Violate
import com.example.dactaphanmem.presentation.util.TimeUtils
import java.util.UUID

object MockDatabase {

    private val database = AppDatabase.instance!!

    init {

    }

    private fun addUserLibrarian() {
        val userInfoLibrarian = UserInfo(
            userId = UUID.randomUUID().toString(),
            userName = "admin",
            userPassword = "123",
            userPhoneNumber = "0362902095",
            userGrade = "CNTT6",
            isUserNormal = false
        )

        database.getUserDao().insertUser(userInfoLibrarian)
    }

    private fun addListBook() {
        database.getBookDao().apply {
            insertBook(
                Book(
                    bookId = UUID.randomUUID().toString(),
                    bookName = "Lập trình Android",
                    bookAuthor = "Ngô Duy Anh",
                    bookCount = 10,
                    bookYear = "2010"
                )
            )
            insertBook(
                Book(
                    bookId = UUID.randomUUID().toString(),
                    bookName = "Lập trình Trực Quan",
                    bookAuthor = "Nguyễn Trần Lê",
                    bookCount = 4,
                    bookYear = "2001"
                )
            )
            insertBook(
                Book(
                    bookId = UUID.randomUUID().toString(),
                    bookName = "Tiếng Anh Cho Người Mới",
                    bookAuthor = "Bùi Đặng Hồ",
                    bookCount = 6,
                    bookYear = "2005"
                )
            )
            insertBook(
                Book(
                    bookId = UUID.randomUUID().toString(),
                    bookName = "Cách nghĩ để thành công",
                    bookAuthor = "Nguyễn Trương Minh",
                    bookCount = 5,
                    bookYear = "2000"
                )
            )
            insertBook(
                Book(
                    bookId = UUID.randomUUID().toString(),
                    bookName = "Hạt giống tâm hồn",
                    bookAuthor = "Đàm Dịch Vũ",
                    bookCount = 1,
                    bookYear = "2012"
                )
            )
        }
    }

    fun addMyBook() {
        database.getMyBookDao().apply {
            insertMyBook(
                MyBook(
                    myBookId = UUID.randomUUID().toString(),
                    myBookName = "Tiếng chim hót trong bụi mận gai",
                    myBookCount = 3,
                    myBookAuthor = "Ngô Duy Anh",
                    myBookYear = "2010",
                    myBookDateGiveBack = "14/12/2023",
                    myBookNumberOder = 1,
                    userInfoBookId = AppPreferences.userInfo!!.userId
                )
            )

            insertMyBook(
                MyBook(
                    myBookId = UUID.randomUUID().toString(),
                    myBookName = "Nhật ký Đặng Thùy Trâm",
                    myBookCount = 1,
                    myBookAuthor = "Nguyễn Duy Cần",
                    myBookYear = "2000",
                    myBookDateGiveBack = "16/12/2023",
                    myBookNumberOder = 1,
                    userInfoBookId = AppPreferences.userInfo!!.userId
                )
            )

            insertMyBook(
                MyBook(
                    myBookId = UUID.randomUUID().toString(),
                    myBookName = "Tôi thấy hoa vàng trên cỏ xanh",
                    myBookCount = 3,
                    myBookAuthor = "Nguyễn Nhật Ánh",
                    myBookYear = "2016",
                    myBookDateGiveBack = "19/12/2023",
                    myBookNumberOder = 1,
                    userInfoBookId = AppPreferences.userInfo!!.userId
                )
            )

            insertMyBook(
                MyBook(
                    myBookId = UUID.randomUUID().toString(),
                    myBookName = "Tuổi trẻ đáng giá bao nhiêu?",
                    myBookCount = 2,
                    myBookAuthor = "Nguyễn Nhật Ánh",
                    myBookYear = "2011",
                    myBookDateGiveBack = "11/12/2023",
                    myBookNumberOder = 1,
                    userInfoBookId = AppPreferences.userInfo!!.userId
                )
            )

            insertMyBook(
                MyBook(
                    myBookId = UUID.randomUUID().toString(),
                    myBookName = "Đắc nhân tâm",
                    myBookCount = 3,
                    myBookAuthor = "Rosie Nguyễn",
                    myBookYear = "2010",
                    myBookDateGiveBack = "22/12/2023",
                    myBookNumberOder = 1,
                    userInfoBookId = AppPreferences.userInfo!!.userId
                )
            )
        }
    }

    fun addViolate() {
        database.getMyBookDao().getAllMyBook().forEachIndexed { i, v ->
            if (v.myBookDateGiveBack != null && TimeUtils.checkGreaterTime(v.myBookDateGiveBack!!)) {
                database.getViolateDao().insertViolate(
                    Violate(
                        violateId = UUID.randomUUID().toString(),
                        violateName = "Quá thời gian trả",
                        myBookViolateId = v.myBookId
                    )
                )
            }
        }
    }

    fun mockData() {
        if (!AppPreferences.isFirst) {
            addListBook()
            addUserLibrarian()
            AppPreferences.isFirst = true
        } else {
            addViolate()
        }
    }
}
