package com.example.dactaphanmem.presentation.main

import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.example.dactaphanmem.common.BaseActivity
import com.example.dactaphanmem.common.checkBeforeBack
import com.example.dactaphanmem.databinding.ActivityMainBinding
import com.example.dactaphanmem.presentation.main.home.HomeFragment
import com.example.dactaphanmem.presentation.main.profile.ProfileFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var adapter: PageAdapter? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setEventView() {
        super.setEventView()
        adapter = PageAdapter(this)
        adapter!!.addListFragment(
            listOf(
                HomeFragment(),
                ProfileFragment()
            )
        )
        binding.vpMain.isUserInputEnabled = false
        binding.vpMain.adapter = this@MainActivity.adapter
        TabLayoutMediator(binding.tlMain, binding.vpMain) { tab, position ->
            tab.text = adapter!!.getTitle(position)
        }.attach()
    }

    override fun onBackPress() {
        checkBeforeBack {
            finishAffinity()
        }
    }
}
