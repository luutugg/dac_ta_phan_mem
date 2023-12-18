package com.example.dactaphanmem.presentation.borrow.giveback

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.BaseFragment
import com.example.dactaphanmem.common.getAppString
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.data.relation.MyBookWithViolate
import com.example.dactaphanmem.databinding.ConfirmGiveBackFragmentBinding
import com.example.dactaphanmem.presentation.borrow.BorrowViewModel

class ConfirmGiveBackBook : BaseFragment<ConfirmGiveBackFragmentBinding>() {

    companion object {
        private const val GIVE_BACK_KEY = "GIVE_BACK_KEY"

        fun getInstance(myBookWithConfirmGiveBack: MyBookWithViolate): ConfirmGiveBackBook {
            val fragment = ConfirmGiveBackBook()
            fragment.arguments = bundleOf(GIVE_BACK_KEY to myBookWithConfirmGiveBack)
            return fragment
        }
    }

    private val viewModel by activityViewModels<BorrowViewModel>()

    private var myBookWithConfirmGiveBack: MyBookWithViolate? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ConfirmGiveBackFragmentBinding {
        return ConfirmGiveBackFragmentBinding.inflate(inflater, container, false)
    }

    override fun getData() {
        super.getData()
        myBookWithConfirmGiveBack = arguments?.getParcelable(GIVE_BACK_KEY)
    }

    override fun setEventView() {
        super.setEventView()
        myBookWithConfirmGiveBack?.let {
            fillData(data = it)
        }

        binding.tvConfirmGiveBackCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.tvConfirmGiveBack.setOnSafeClick {
            if (binding.edtConfirmGiveBackCount.text.toString().isEmpty()) {
                Toast.makeText(context, "Chưa nhập số lượng trả", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (myBookWithConfirmGiveBack?.myBook?.myBookCount != null &&
                binding.edtConfirmGiveBackCount.text.toString()
                    .toInt() > myBookWithConfirmGiveBack?.myBook?.myBookCount!!
            ) {
                Toast.makeText(context, "Số lượng lớn hơn số sách đã mượn", Toast.LENGTH_SHORT)
                    .show()
                return@setOnSafeClick
            }
            myBookWithConfirmGiveBack?.let { it1 ->
                viewModel.giveBackBook(it1, binding.edtConfirmGiveBackCount.text.toString().toInt())
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun fillData(data: MyBookWithViolate) {
        binding.tvConfirmGiveBackName.text =
            getAppString(R.string.book_name, data.myBook.myBookName.toString())
        binding.tvConfirmGiveBackAuthor.text =
            getAppString(R.string.book_author, data.myBook.myBookAuthor.toString())
        binding.tvConfirmGiveBackYear.text =
            getAppString(R.string.book_year, data.myBook.myBookYear.toString())
        binding.tvConfirmGiveBackDateConfirmGiveBack.text = getAppString(
            R.string.borrow_time,
            data.myBook.myBookDateGiveBack.toString()
        )
        binding.edtConfirmGiveBackCount.setText(data.myBook.myBookCount.toString())
    }
}
