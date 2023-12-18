package com.example.dactaphanmem.presentation.main.home.moreaction

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dactaphanmem.common.BaseFragment
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.databinding.MoreFragmentBinding

class MoreFragment : BaseFragment<MoreFragmentBinding>() {

    var listener: IMoreListener? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MoreFragmentBinding {
        return MoreFragmentBinding.inflate(inflater, container, false)
    }

    override fun setEventView() {
        super.setEventView()
        binding.flMoreClose.setOnSafeClick {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.tvMoreUpdate.setOnSafeClick {
            listener?.onUpdate()
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.tvDelete.setOnSafeClick {
            listener?.onDelete()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    interface IMoreListener {
        fun onUpdate()
        fun onDelete()
    }
}
