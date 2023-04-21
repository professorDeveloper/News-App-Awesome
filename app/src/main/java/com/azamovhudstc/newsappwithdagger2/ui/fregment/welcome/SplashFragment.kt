package com.azamovhudstc.newsappwithdagger2.ui.fregment.welcome

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.databinding.FragmentSplashBinding
import com.azamovhudstc.newsappwithdagger2.App
import com.azamovhudstc.newsappwithdagger2.ui.activity.MainActivity
import com.azamovhudstc.newsappwithdagger2.ui.activity.WelcomeActivity
import com.azamovhudstc.newsappwithdagger2.utils.Topics.appComponent
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment
import javax.inject.Inject

class SplashFragment : ThemeFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var sPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        appComponent.inject(this)


        val user = sPref.getBoolean("isUserRegistered", false)
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            if (!user) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_welcome, OnBoardingFragment())
                    .commit()
            } else {
                (activity as WelcomeActivity?)?.finish()
                val intent =
                    Intent(binding.root.context.applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

        handler.postDelayed(runnable, 1500)
        return binding.root
    }

    override fun syncTheme(appTheme: AppTheme) {

    }
}