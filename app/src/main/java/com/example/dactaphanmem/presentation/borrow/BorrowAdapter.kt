package com.example.dactaphanmem.presentation.borrow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.getAppString
import com.example.dactaphanmem.common.gone
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.data.relation.MyBookWithViolate
import com.example.dactaphanmem.databinding.ViolateItemBinding
import com.example.dactaphanmem.presentation.util.TimeUtils

class BorrowAdapter : ListAdapter<MyBookWithViolate, BorrowAdapter.BorrowVH>(BorrowDiffCallback()) {

    var listener: IBorrowListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowVH {
        return BorrowVH(
            ViolateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BorrowVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class BorrowVH(private val binding: ViolateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.fltvViolateAdd.setOnSafeClick {
                listener?.onGiveUp(getItem(absoluteAdapterPosition))
            }
        }

        fun onBind(data: MyBookWithViolate) {
            fillData(data)
        }

        private fun fillData(data: MyBookWithViolate) {
            binding.tvViolateName.text =
                getAppString(R.string.book_name, data.myBook.myBookName.toString())
            binding.tvViolateAuthor.text =
                getAppString(R.string.book_author, data.myBook.myBookAuthor.toString())
            binding.tvViolateYear.text =
                getAppString(R.string.book_year, data.myBook.myBookYear.toString())
            binding.tvViolateCount.text =
                getAppString(R.string.book_count, data.myBook.myBookCount.toString())
            binding.tvViolateReason.text = getAppString(
                R.string.borrow_time,
                data.myBook.myBookDateGiveBack.toString()
            )
            binding.tvViolateTime.text = "Lần thứ ${data.myBook.myBookNumberOder}"
        }
    }

    class BorrowDiffCallback : DiffUtil.ItemCallback<MyBookWithViolate>() {
        override fun areItemsTheSame(
            oldItem: MyBookWithViolate,
            newItem: MyBookWithViolate
        ): Boolean {
            return oldItem.myBook.myBookId == newItem.myBook.myBookId
        }

        override fun areContentsTheSame(
            oldItem: MyBookWithViolate,
            newItem: MyBookWithViolate
        ): Boolean {
            return oldItem.myBook.myBookCount == newItem.myBook.myBookCount
        }
    }

    interface IBorrowListener {
        fun onGiveUp(myBookWithViolate: MyBookWithViolate)
    }
}
