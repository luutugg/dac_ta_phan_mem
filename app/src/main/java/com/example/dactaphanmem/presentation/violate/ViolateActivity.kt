package com.example.dactaphanmem.presentation.violate

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
import com.example.dactaphanmem.common.ResolveViolate
import com.example.dactaphanmem.common.eventbus.EventBusManager
import com.example.dactaphanmem.common.gone
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.common.show
import com.example.dactaphanmem.data.relation.MyBookWithViolate
import com.example.dactaphanmem.databinding.ViolateActivityBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.eventbus.EventBus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViolateActivity : BaseActivity<ViolateActivityBinding>() {

    private val viewModel by viewModels<ViolateViewModel>()

    private val adapter by lazy { ViolateAdapter() }

    override fun inflateLayout(layoutInflater: LayoutInflater): ViolateActivityBinding {
        return ViolateActivityBinding.inflate(layoutInflater)
    }

    override fun setEventView() {
        super.setEventView()
        binding.flViolateBack.setOnSafeClick {
            onBackPress()
        }
    }

    override fun setUpAdapter() {
        super.setUpAdapter()
        binding.rvViolate.adapter = this@ViolateActivity.adapter
        binding.rvViolate.layoutManager = LinearLayoutManager(this)

        addListener()
    }

    override fun observerData() {
        super.observerData()
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.violateState.collect {
                    adapter.submitList(it)
                    if (it.isNullOrEmpty()) {
                        binding.tvViolateNone.show()
                    } else {
                        binding.tvViolateNone.gone()
                    }
                }
            }
        }
    }

    private fun addListener() {
        adapter.listener = object : ViolateAdapter.IViolateListener {
            override fun onGiveUp(myBookWithViolate: MyBookWithViolate) {
                viewModel.resolveViolate(myBookWithViolate)
            }
        }
    }

    override fun onBackPress() {
        EventBusManager.instance?.postPending(ResolveViolate(true))
        super.onBackPress()
    }
}
