package com.example.dactaphanmem.presentation.main.home.warning

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dactaphanmem.common.BaseFragment
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.databinding.WarningFragmentBinding
import com.example.dactaphanmem.presentation.violate.ViolateActivity

class WarningFragment : BaseFragment<WarningFragmentBinding>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): WarningFragmentBinding {
        return WarningFragmentBinding.inflate(inflater, container, false)
    }

    override fun setEventView() {
        super.setEventView()
        binding.tvWarningCancel.setOnSafeClick {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.tvWarning.setOnSafeClick {
            startActivity(Intent(requireActivity(), ViolateActivity::class.java))
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
