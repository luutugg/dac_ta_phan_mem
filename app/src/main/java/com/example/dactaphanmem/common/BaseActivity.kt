package com.example.dactaphanmem.common

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        setEventView()
        setUpAdapter()
        observerData()
        setOnBackPress()
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    private fun setOnBackPress() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPress()
            }
        }
        onBackPressedDispatcher.addCallback(callBack)
    }

    open fun onBackPress() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    open fun setEventView() {}

    open fun setUpAdapter() {}

    open fun observerData() {}

}
