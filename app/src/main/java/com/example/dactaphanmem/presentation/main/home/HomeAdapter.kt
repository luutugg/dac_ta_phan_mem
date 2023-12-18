package com.example.dactaphanmem.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.getAppString
import com.example.dactaphanmem.common.gone
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.common.show
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.databinding.BookItemBinding
import com.example.dactaphanmem.presentation.AppPreferences

class HomeAdapter : ListAdapter<Book, HomeAdapter.BookVh>(BookDiffCallback()) {

    var listener: IHomeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVh {
        return BookVh(BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BookVh, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class BookVh(private val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.flBookAdd.setOnSafeClick {
                listener?.onBorrow(getItem(absoluteAdapterPosition))
            }

            binding.flBookMore.setOnSafeClick {
                listener?.onMore(getItem(absoluteAdapterPosition))
            }
        }

        fun onBind(data: Book) {
            fillData(data)
            checkAccount()
        }

        private fun fillData(data: Book) {
            binding.tvBookName.text = getAppString(R.string.book_name, data.bookName.toString())
            binding.tvBookAuthor.text =
                getAppString(R.string.book_author, data.bookAuthor.toString())
            binding.tvBookYear.text = getAppString(R.string.book_year, data.bookYear.toString())
            binding.tvBookCount.text = getAppString(R.string.book_count, data.bookCount.toString())
        }

        private fun checkAccount() {
            if (AppPreferences.userInfo!!.isUserNormal) {
                binding.flBookAdd.show()
                binding.flBookMore.gone()
            } else {
                binding.flBookAdd.gone()
                binding.flBookMore.show()
            }
        }
    }

    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.bookId == newItem.bookId
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.bookCount == newItem.bookCount
                    && oldItem.bookName == newItem.bookName
                    && oldItem.bookAuthor == newItem.bookAuthor
                    && oldItem.bookYear == newItem.bookYear
        }
    }

    interface IHomeListener {
        fun onBorrow(book: Book)
        fun onMore(book: Book)
    }
}
