package com.example.dactaphanmem.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dactaphanmem.common.BaseActivity
import com.example.dactaphanmem.data.database.AppDatabase
import com.example.dactaphanmem.databinding.SplashActivityBinding
import com.example.dactaphanmem.presentation.signup.SignUpActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<SplashActivityBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): SplashActivityBinding {
        return SplashActivityBinding.inflate(layoutInflater)
    }

    override fun setEventView() {
        super.setEventView()
        lifecycleScope.launch {
            AppDatabase.instance!!.getUserDao().getAllUser().forEach {
                Log.d("ZZZZZZZ", "setEventView: $it")
            }
            delay(1000)
            startActivity(Intent(this@SplashActivity,SignUpActivity::class.java))
        }
    }
}
