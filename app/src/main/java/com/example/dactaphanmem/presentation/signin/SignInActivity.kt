package com.example.dactaphanmem.presentation.signin

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dactaphanmem.common.BaseActivity
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.databinding.SignInActivityBinding
import com.example.dactaphanmem.presentation.main.MainActivity
import kotlinx.coroutines.launch

class SignInActivity : BaseActivity<SignInActivityBinding>() {

    companion object {
        const val DATA_FROM_LOGOUT_KEY = "DATA_FROM_LOGOUT_KEY"
    }

    private var isFromLogOut = false

    override fun inflateLayout(layoutInflater: LayoutInflater): SignInActivityBinding {
        return SignInActivityBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SignInViewModel>()

    override fun setEventView() {

        isFromLogOut = intent.getBooleanExtra(DATA_FROM_LOGOUT_KEY, false)

        binding.tvSignIn.setOnSafeClick {
            if (binding.edtSignInUserName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "User name không được null", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtSignInUserPassword.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "User password không được null", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }
            viewModel.signIn(
                binding.edtSignInUserName.text.toString(),
                binding.edtSignInUserPassword.text.toString()
            )
        }
    }

    override fun observerData() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.signInState.collect {
                    when (it) {
                        true -> {
                            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                            viewModel.reset()
                        }

                        false -> {
                            Toast.makeText(
                                this@SignInActivity,
                                "User không tồn tại",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.reset()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun onBackPress() {
        if (isFromLogOut) {
            finishAffinity()
        } else {
            super.onBackPress()
        }
    }
}
