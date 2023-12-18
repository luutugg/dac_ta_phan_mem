package com.example.dactaphanmem.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.getAppString
import com.example.dactaphanmem.data.entity.History
import com.example.dactaphanmem.databinding.HistoryItemBinding

class HistoryAdapter: ListAdapter<History, HistoryAdapter.HistoryVH>(HistoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
        return HistoryVH(HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class HistoryVH(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: History) {
            binding.tvHistoryName.text = getAppString(R.string.history_name, data.userName.toString())
            binding.tvHistoryPhone.text =
                getAppString(R.string.history_phone_number, data.userPhone.toString())
            binding.tvHistoryBookName.text =
                getAppString(R.string.history_book_name, data.bookName.toString())
            binding.tvHistoryBookCount.text =
                getAppString(R.string.history_book_count, data.bookCount.toString())
            binding.tvHistoryDate.text =
                getAppString(R.string.history_date, data.historyDate.toString())

            binding.tvHistoryState.text = getAppString(R.string.history_state, if (data.historyType) {
                "Mượn sách"
            } else {
                "Trả sách"
            })

            binding.tvHistoryTime.text = "Lần thứ: ${data.bookTime}"
        }
    }

    class HistoryDiffCallBack : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return false
        }
    }
}
