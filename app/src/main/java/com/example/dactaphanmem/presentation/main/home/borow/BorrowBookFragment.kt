package com.example.dactaphanmem.presentation.main.home.borow

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.BaseFragment
import com.example.dactaphanmem.common.OnDeleteBook
import com.example.dactaphanmem.common.OnUpdateBook
import com.example.dactaphanmem.common.eventbus.EventBusManager
import com.example.dactaphanmem.common.getAppString
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.data.entity.Book
import com.example.dactaphanmem.databinding.BorrowBookFragmentBinding
import kotlinx.coroutines.launch

class BorrowBookFragment : BaseFragment<BorrowBookFragmentBinding>() {

    companion object {
        private const val BOOK_KEY = "BOOK_KEY"

        fun getInstance(book: Book): BorrowBookFragment {
            val fragment = BorrowBookFragment()
            val bundle = bundleOf(
                BOOK_KEY to book
            )
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel by viewModels<BorrowViewModel>()

    private var book: Book? = null

    override fun getData() {
        super.getData()
        book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(BOOK_KEY, Book::class.java)
        } else {
            arguments?.getParcelable(BOOK_KEY)
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BorrowBookFragmentBinding {
        return BorrowBookFragmentBinding.inflate(inflater, container, false)
    }

    override fun setEventView() {
        super.setEventView()
        fillDataBook()
        onClick()
    }

    private fun fillDataBook() {
        binding.tvBorrowName.text = getAppString(R.string.book_name, book?.bookName.toString())
        binding.tvBorrowAuthor.text =
            getAppString(R.string.book_author, book?.bookAuthor.toString())
        binding.tvBorrowYear.text = getAppString(R.string.book_year, book?.bookYear.toString())
    }

    private fun onClick() {
        binding.tvBorrowCancel.setOnSafeClick {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.tvBorrow.setOnSafeClick {
            if (binding.edtBorrowCount.text.toString().isEmpty()) {
                Toast.makeText(context, "Chưa có số lượng mượn", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }
            if (book?.bookCount != null && binding.edtBorrowCount.text.toString()
                    .toInt() > book?.bookCount!!
            ) {
                Toast.makeText(context, "Số lượng lớn hơn số lượng sách có", Toast.LENGTH_SHORT)
                    .show()
                return@setOnSafeClick
            }
            book?.let {
                viewModel.borrowBook(it, binding.edtBorrowCount.text.toString().toInt())
            }
        }
    }

    override fun observerData() {
        super.observerData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.borrowState.collect {
                    if (it == true) {
                        val bookCallBack = book?.copy()
                        bookCallBack?.bookCount = (book?.bookCount?:0) - binding.edtBorrowCount.text.toString().toInt()
                        if (bookCallBack?.bookCount == 0){
                            EventBusManager.instance?.postPending(OnDeleteBook(bookCallBack?.bookId))
                        }else{
                            EventBusManager.instance?.postPending(OnUpdateBook(bookCallBack))
                        }
                        Toast.makeText(requireContext(), "Mượn thành công", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }
        }
    }
}
