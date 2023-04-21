package com.azamovhudstc.newsappwithdagger2.ui.fregment.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.databinding.FragmentOnBoardingBinding
import com.azamovhudstc.newsappwithdagger2.ui.adapters.OnBoardingAdapter
import com.azamovhudstc.newsappwithdagger2.ui.transformer.CardTransformer
import com.azamovhudstc.newsappwithdagger2.ui.transformer.HorizontalMarginItemDecoration
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)

        setUpPager()

        binding.materialButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_welcome, WelcomeFragment())
                .addToBackStack("welcome")
                .commit()
        }

        return binding.root
    }

    private fun setUpPager() {
        binding.apply {
            val list = arrayListOf(
                "https://static.toiimg.com/photo/84475061.cms",
                "https://cdn.vox-cdn.com/thumbor/YqtFL7c39ikKKr8P2zNXhxuD7QE=/0x0:3888x2592/1200x800/filters:focal(1633x985:2255x1607)/cdn.vox-cdn.com/uploads/chorus_image/image/66646657/shutterstock_302433650.0.jpg",
                "https://wamu.org/wp-content/uploads/2020/06/200601_DCProtest_Turner_26-1500x1000.jpg"
                )
            val pagerAdapter = OnBoardingAdapter(list)
            indicatorView.apply {
                setNormalSlideWidth(30f)
                setCheckedSlideWidth(70f)
                setSliderHeight(30f)
                setSlideMode(IndicatorSlideMode.SCALE)
                setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                setupWithViewPager(viewPager)
                setPageSize(list.size)
                notifyDataChanged()
            }
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = 1
            viewPager.setPageTransformer(CardTransformer(root.context))

            val itemDecoration = HorizontalMarginItemDecoration(
                root.context,
                R.dimen.viewpager_current_item_horizontal_margin
            )
            viewPager.addItemDecoration(itemDecoration)
        }
    }
}