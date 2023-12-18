package com.example.dactaphanmem.presentation.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.BaseActivity
import com.example.dactaphanmem.common.OnUpdateBook
import com.example.dactaphanmem.common.eventbus.EventBusManager
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.databinding.ActivityUpdateBinding

class UpdateActivity : BaseActivity<ActivityUpdateBinding>() {

    companion object {
        const val BOOK_KEY = "BOOK_KEY"
    }

    private var book: Book? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityUpdateBinding {
        return ActivityUpdateBinding.inflate(layoutInflater)
    }

    override fun setEventView() {
        super.setEventView()
        book = intent.extras?.getParcelable(BOOK_KEY)
        fillData()
        onClick()
    }

    private fun fillData() {
        binding.edtUpdateBookName.setText(book?.bookName)
        binding.edtUpdateBookAuthor.setText(book?.bookAuthor)
        binding.edtUpdateBookYear.setText(book?.bookYear)
        binding.edtUpdateBookCount.setText(book?.bookCount.toString())
    }

    private fun onClick() {
        binding.flUpdateBack.setOnSafeClick {
            onBackPress()
        }

        binding.tvUpdate.setOnSafeClick {
            if (binding.edtUpdateBookName.text.toString().isEmpty()){
                Toast.makeText(this, "Tên sách ko được để trống", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtUpdateBookAuthor.text.toString().isEmpty()){
                Toast.makeText(this, "Tên tác giả ko được để trống", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtUpdateBookYear.text.toString().isEmpty()){
                Toast.makeText(this, "Năm sản xuất ko được để trống", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtUpdateBookCount.text.toString().isEmpty()){
                Toast.makeText(this, "Số lương sách ko được để trống", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            val newBook = book?.copy()
            newBook?.let {
                it.bookName = binding.edtUpdateBookName.text.toString()
                it.bookAuthor = binding.edtUpdateBookAuthor.text.toString()
                it.bookYear = binding.edtUpdateBookYear.text.toString()
                it.bookCount = binding.edtUpdateBookCount.text.toString().toInt()
            }

            if (newBook != null) {
                AppDatabase.instance!!.getBookDao().updateBook(newBook)
                EventBusManager.instance?.postPending(OnUpdateBook(newBook))
            }
            onBackPress()
        }
    }
}
