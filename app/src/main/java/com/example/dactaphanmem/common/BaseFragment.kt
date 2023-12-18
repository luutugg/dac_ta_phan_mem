package com.example.dactaphanmem.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null

    val binding get() = _binding!!

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateViewBinding(inflater, container)
        getData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventView()
        setUpAdapter()
        observerData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun setEventView(){}

    open fun setUpAdapter(){}

    open fun observerData(){}

    open fun getData(){}

    fun onBackPress(){
        (requireActivity() as? BaseActivity<*>)?.onBackPress()
    }
}
