package com.example.dactaphanmem.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private var listFragment: List<Fragment> = emptyList()

    override fun getItemCount() = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }

    fun addListFragment(list: List<Fragment>) {
        this.listFragment = list
    }

    fun getTitle(position: Int): String{
        return when(position){
            0 -> "Home"
            else -> "Profile"
        }
    }
}
