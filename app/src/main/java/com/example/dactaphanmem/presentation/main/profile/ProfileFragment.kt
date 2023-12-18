package com.example.dactaphanmem.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dactaphanmem.R
import com.example.dactaphanmem.common.BaseFragment
import com.example.dactaphanmem.common.getAppString
import com.example.dactaphanmem.common.gone
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.common.show
import com.example.dactaphanmem.databinding.ProfileFragmentBinding
import com.example.dactaphanmem.presentation.AppPreferences
import com.example.dactaphanmem.presentation.borrow.BorrowActivity
import com.example.dactaphanmem.presentation.history.HistoryActivity
import com.example.dactaphanmem.presentation.signin.SignInActivity
import com.example.dactaphanmem.presentation.violate.ViolateActivity

class ProfileFragment : BaseFragment<ProfileFragmentBinding>() {

    private val userInfo = AppPreferences.userInfo!!

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProfileFragmentBinding {
        return ProfileFragmentBinding.inflate(inflater, container, false)
    }

    override fun setEventView() {
        super.setEventView()
        checkAccount()
        fillDataUser()
        setOnClick()
    }

    private fun setOnClick() {
        binding.tvProfileLogout.setOnSafeClick {
            val intent = Intent(requireActivity(), SignInActivity::class.java)
            intent.putExtra(SignInActivity.DATA_FROM_LOGOUT_KEY, true)
            startActivity(intent)
        }

        binding.tvProfileViolate.setOnSafeClick {
            startActivity(Intent(requireContext(), ViolateActivity::class.java))
        }

        binding.tvProfileBookBorrowed.setOnSafeClick {
            startActivity(Intent(requireContext(), BorrowActivity::class.java))
        }

        binding.tvProfileHistory.setOnSafeClick {
            startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }
    }

    private fun fillDataUser() {
        binding.tvProfileName.text = userInfo.userName.toString()
        binding.tvProfilePhoneNumber.text = userInfo.userPhoneNumber.toString()
        binding.tvProfileGrade.text = userInfo.userGrade.toString()
        binding.tvProfileAccountType.text = if (userInfo.isUserNormal) {
            getAppString(R.string.account_student)
        } else {
            getAppString(R.string.account_librarian)
        }
    }

    private fun checkAccount() {
        if (userInfo.isUserNormal) {
            binding.tvProfileBookBorrowed.show()
            binding.tvProfileViolate.show()
            binding.tvProfileHistory.gone()
        } else {
            binding.tvProfileBookBorrowed.gone()
            binding.tvProfileViolate.gone()
            binding.tvProfileHistory.show()
        }
    }
}
