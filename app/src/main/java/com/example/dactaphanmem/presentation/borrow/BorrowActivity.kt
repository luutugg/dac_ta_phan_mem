package com.example.dactaphanmem.presentation.borrow

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.BaseActivity
import com.example.dactaphanmem.common.ResolveViolate
import com.example.dactaphanmem.common.eventbus.EventBusManager
import com.example.dactaphanmem.common.gone
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.common.show
import com.example.dactaphanmem.data.relation.MyBookWithViolate
import com.example.dactaphanmem.databinding.BorrowActivityBinding
import com.example.dactaphanmem.presentation.borrow.giveback.ConfirmGiveBackBook
import kotlinx.coroutines.launch

class BorrowActivity : BaseActivity<BorrowActivityBinding>() {

    private val viewModel by viewModels<BorrowViewModel>()

    private val adapter by lazy { BorrowAdapter() }

    override fun inflateLayout(layoutInflater: LayoutInflater): BorrowActivityBinding {
        return BorrowActivityBinding.inflate(layoutInflater)
    }


    override fun setEventView() {
        super.setEventView()
        viewModel.getAllMyBook()
        binding.flBorrowBack.setOnSafeClick {
            onBackPress()
        }
    }

    override fun setUpAdapter() {
        super.setUpAdapter()
        binding.rvBorrow.apply {
            adapter = this@BorrowActivity.adapter
            layoutManager = LinearLayoutManager(this@BorrowActivity)
        }

        adapter.listener = object : BorrowAdapter.IBorrowListener {
            override fun onGiveUp(myBookWithViolate: MyBookWithViolate) {
                if (myBookWithViolate.myBook.myBookCount == 1) {
                    viewModel.giveBackBook(myBookWithViolate)
                } else {
                    val fragment = ConfirmGiveBackBook.getInstance(myBookWithViolate)
                    supportFragmentManager.beginTransaction()
                        .add(R.id.clBorrowRoot, fragment)
                        .addToBackStack(fragment.tag.toString())
                        .commit()
                }
            }
        }
    }

    override fun observerData() {
        super.observerData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.borrowState.collect {
                    adapter.submitList(it)
                    if (it.isNullOrEmpty()) {
                        binding.tvBorrowNone.show()
                    } else {
                        binding.tvBorrowNone.gone()
                    }
                }
            }
        }
    }

    override fun onBackPress() {
        EventBusManager.instance?.postPending(ResolveViolate(true))
        super.onBackPress()
    }

}
