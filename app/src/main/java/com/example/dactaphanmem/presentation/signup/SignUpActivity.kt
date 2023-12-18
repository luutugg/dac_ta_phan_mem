package com.example.dactaphanmem.presentation.signup

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dactaphanmem.common.BaseActivity
import com.example.dactaphanmem.common.checkBeforeBack
import com.example.dactaphanmem.common.setOnSafeClick
import com.example.dactaphanmem.databinding.SignUpActivityBinding
import com.example.dactaphanmem.presentation.main.MainActivity
import com.example.dactaphanmem.presentation.signin.SignInActivity
import kotlinx.coroutines.launch

class SignUpActivity : BaseActivity<SignUpActivityBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): SignUpActivityBinding {
        return SignUpActivityBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SignUpViewModel>()

    override fun setEventView() {

        binding.tvSignUpHaveAccount.setOnSafeClick {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        }

        binding.tvSignUp.setOnSafeClick {

            if (binding.edtSignUpUserName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "User name không được null", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtSignUpUserPassword.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "User password không được null", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtSignUpConfirmPassword.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Confirm không được null", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtSignUpConfirmPassword.text.toString() != binding.edtSignUpUserPassword.text.toString()) {
                Toast.makeText(this, "Confirm không đúng", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtSignUpUserPhoneNumber.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "User phone number không được null", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            if (binding.edtSignUpUserGrade.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "User grade không được null", Toast.LENGTH_SHORT).show()
                return@setOnSafeClick
            }

            viewModel.signUp(
                userName = binding.edtSignUpUserName.text.toString(),
                password = binding.edtSignUpUserPassword.text.toString(),
                phoneNumber = binding.edtSignUpUserPhoneNumber.text.toString(),
                grade = binding.edtSignUpUserGrade.text.toString()
            )
        }
    }

    override fun observerData() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.signUpState.collect {
                    when (it) {
                        true -> {
                            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                            viewModel.reset()
                        }

                        false -> {
                            Toast.makeText(
                                this@SignUpActivity,
                                "User name đã tồn tại",
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
        super.onBackPress()
        checkBeforeBack {
            finishAffinity()
        }
    }
}
