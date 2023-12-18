package com.example.dactaphanmem.presentation.managerbook

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.BaseActivity
import com.example.dactaphanmem.common.OnInsertBook
import com.example.dactaphanmem.common.OnUpdateBook
import com.example.dactaphanmem.common.eventbus.EventBusManager
import com.example.dactaphanmem.common.getAppString
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.databinding.ActivityUpdateBinding
import java.util.UUID


class ManagerBookActivity : BaseActivity<ActivityUpdateBinding>() {

    companion object {
        const val BOOK_KEY = "BOOK_KEY"
        const val BOOK_ADD_KEY = "BOOK_ADD_KEY"
    }

    private var book: Book? = null

    private var isAdd = false

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityUpdateBinding {
        return ActivityUpdateBinding.inflate(layoutInflater)
    }

    override fun setEventView() {
        super.setEventView()
        book = intent.extras?.getParcelable(BOOK_KEY)
        isAdd = intent.extras?.getBoolean(BOOK_ADD_KEY) ?: false
        fillData()
        onClick()
    }

    private fun fillData() {
        binding.edtUpdateBookName.setText(book?.bookName)
        binding.edtUpdateBookAuthor.setText(book?.bookAuthor)
        binding.edtUpdateBookYear.setText(book?.bookYear)
        if (book?.bookCount != null) {
            binding.edtUpdateBookCount.setText("${book?.bookCount}")
        }

        if (isAdd) {
            binding.tvUpdateTitle.text = getAppString(R.string.add_book_title)
            binding.tvUpdate.text = getAppString(R.string.add_book_title)
        } else {
            binding.tvUpdateTitle.text = getAppString(R.string.update)
            binding.tvUpdate.text = getAppString(R.string.update_book)
        }
    }

    private fun onClick() {
        binding.flUpdateBack.setOnSafeClick {
            onBackPress()
        }

        binding.tvUpdate.setOnSafeClick {
            if (binding.edtUpdateBookName.text.toString().isEmpty()) {
                Toast.makeText(this, "Tên sách ko được để trống", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtUpdateBookAuthor.text.toString().isEmpty()) {
                Toast.makeText(this, "Tên tác giả ko được để trống", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtUpdateBookYear.text.toString().isEmpty()) {
                Toast.makeText(this, "Năm sản xuất ko được để trống", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtUpdateBookCount.text.toString().isEmpty()) {
                Toast.makeText(this, "Số lương sách ko được để trống", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            var newBook = book?.copy()
            if (newBook != null) {
                newBook.apply {
                    bookName = binding.edtUpdateBookName.text.toString()
                    bookAuthor = binding.edtUpdateBookAuthor.text.toString()
                    bookYear = binding.edtUpdateBookYear.text.toString()
                    bookCount = binding.edtUpdateBookCount.text.toString().toInt()
                }
                AppDatabase.instance!!.getBookDao().updateBook(newBook)
                EventBusManager.instance?.postPending(OnUpdateBook(newBook))
            } else {
                newBook = Book(
                    bookId = UUID.randomUUID().toString(),
                    bookName = binding.edtUpdateBookName.text.toString(),
                    bookAuthor = binding.edtUpdateBookAuthor.text.toString(),
                    bookCount = binding.edtUpdateBookCount.text.toString().toInt(),
                    bookYear = binding.edtUpdateBookYear.text.toString()
                )
                AppDatabase.instance!!.getBookDao().insertBook(newBook)
                EventBusManager.instance?.postPending(OnInsertBook(newBook))
            }

            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show()

           onBackPress()
        }
    }

}
