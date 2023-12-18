package com.example.dactaphanmem.presentation.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.BaseActivity
import com.example.dactaphanmem.common.gone
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.common.show
import com.example.dactaphanmem.databinding.HistoryActivityBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : BaseActivity<HistoryActivityBinding>() {

    private val viewModel by viewModels<HistoryViewModel>()

    private val adapter by lazy { HistoryAdapter() }

    override fun inflateLayout(layoutInflater: LayoutInflater): HistoryActivityBinding {
        return HistoryActivityBinding.inflate(layoutInflater)
    }

    override fun setEventView() {
        super.setEventView()
        binding.flHistoryBack.setOnSafeClick {
            onBackPress()
        }
    }

    override fun setUpAdapter() {
        super.setUpAdapter()
        binding.rvHistory.adapter = this@HistoryActivity.adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
    }

    override fun observerData() {
        super.observerData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.historyState.collect {
                    adapter.submitList(it)
                    if (it.isNullOrEmpty()){
                        binding.tvHistoryNone.show()
                    }else{
                        binding.tvHistoryNone.gone()
                    }
                }
            }
        }
    }
}
