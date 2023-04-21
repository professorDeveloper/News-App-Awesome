package com.azamovhudstc.newsappwithdagger2.ui.fregment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.databinding.FragmentLanguageBinding
import com.azamovhudstc.newsappwithdagger2.themes.MyAppTheme
import com.azamovhudstc.newsappwithdagger2.ui.activity.MainView
import com.azamovhudstc.newsappwithdagger2.utils.LocaleHelper
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment

class LanguageFragment : ThemeFragment() {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(layoutInflater, container, false)
        (activity as MainView?)?.hideBottomBar()

        initRG()
        setOnClickListeners()


        return binding.root
    }

    private fun initRG() {
        val language = LocaleHelper.getLanguage(binding.root.context)
        when (language) {
            "tr" -> binding.turkishRb.isChecked = true
            "de" -> binding.germanRb.isChecked = true
            "es" -> binding.spanishRb.isChecked = true
            "ru" -> binding.russianRb.isChecked = true
            "cn" -> binding.chinaRb.isChecked = true
            else -> {}
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            backIv.setOnClickListener { (activity as MainView?)?.backPressed() }

            radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
                val lang = when (checkedId) {
                    R.id.english_rb -> ""
                    R.id.turkish_rb -> "tr"
                    R.id.german_rb -> "de"
                    R.id.spanish_rb -> "es"
                    R.id.russian_rb -> "ru"
                    R.id.china_rb -> "cn"
                    else -> ""
                }

                LocaleHelper.setLocale(binding.root.context, lang)
                (activity as MainView?)?.restart()
            }

        }
    }

    override fun syncTheme(appTheme: AppTheme) {

        val myAppTheme = appTheme as MyAppTheme
        context?.let {
            // set background color
            binding.root.setBackgroundColor(myAppTheme.fragmentBackgroundColor(it))
            binding.titleTv.setTextColor(myAppTheme.fragmentLargeTextColor(it))
        }

        //     syncStatusBarIconColors(appTheme)
    }
}