package com.example.dactaphanmem.presentation.violate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.getAppString
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.data.relation.MyBookWithViolate
import com.example.dactaphanmem.databinding.ViolateItemBinding
import com.example.dactaphanmem.presentation.util.TimeUtils

class ViolateAdapter :
    ListAdapter<MyBookWithViolate, ViolateAdapter.ViolateVH>(ViolateDiffCallback()) {

     var listener: IViolateListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViolateVH {
        return ViolateVH(
            ViolateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViolateVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViolateVH(private val binding: ViolateItemBinding) :
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
                R.string.violate_overtime,
                TimeUtils.minusTime(data.myBook.myBookDateGiveBack.toString())
            )
            binding.tvViolateTime.text = "Lần thứ ${data.myBook.myBookNumberOder}"
        }
    }

    class ViolateDiffCallback : DiffUtil.ItemCallback<MyBookWithViolate>() {
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
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    interface IViolateListener {
        fun onGiveUp(myBookWithViolate: MyBookWithViolate)
    }
}
