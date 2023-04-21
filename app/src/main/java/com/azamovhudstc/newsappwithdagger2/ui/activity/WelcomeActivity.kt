package com.azamovhudstc.newsappwithdagger2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.databinding.ActivityWelcomeBinding
import com.azamovhudstc.newsappwithdagger2.ui.fregment.welcome.SplashFragment

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_welcome, SplashFragment())
            .commit()
    }
}