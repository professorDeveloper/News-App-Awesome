package com.azamovhudstc.newsappwithdagger2.ui.fregment.home

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.App
import com.azamovhudstc.newsappwithdagger2.ui.adapters.NewsAdapter
import com.azamovhudstc.newsappwithdagger2.ui.adapters.PagerNewsAdapter
import com.azamovhudstc.newsappwithdagger2.data.local.dao.NewsDao
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News
import com.azamovhudstc.newsappwithdagger2.databinding.FragmentHomeBinding
import com.azamovhudstc.newsappwithdagger2.databinding.ItemTabBinding
import com.azamovhudstc.newsappwithdagger2.themes.MyAppTheme
import com.azamovhudstc.newsappwithdagger2.ui.activity.MainView
import com.azamovhudstc.newsappwithdagger2.ui.fregment.article.ArticleFragment
import com.azamovhudstc.newsappwithdagger2.ui.fregment.search.SearchFragment
import com.azamovhudstc.newsappwithdagger2.utils.LocaleHelper
import com.azamovhudstc.newsappwithdagger2.utils.NetworkHelper
import com.azamovhudstc.newsappwithdagger2.utils.NewsResource
import com.azamovhudstc.newsappwithdagger2.utils.Topics.appComponent
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : ThemeFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var homeViewModel: HomeViewModel
    @Inject lateinit var newsDao: NewsDao
    @Inject lateinit var sPref: SharedPreferences
    @Inject lateinit var networkHelper: NetworkHelper
    private lateinit var pagerNewsAdapter: PagerNewsAdapter
    private lateinit var newsAdapter: NewsAdapter
    private var isObserved = false
    private var tabItemColor: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        appComponent.inject(this)
        (activity as MainView?)?.showBottomBar()

        if (!isObserved) {
            initAdapters()
        }
        observeBaseNews()
        setUI()
        setOnClickListeners()

        return binding.root
    }

    override fun syncTheme(appTheme: AppTheme) {
        val myAppTheme = appTheme as MyAppTheme
        context?.let {
            // set background color
            binding.root.setBackgroundColor(myAppTheme.fragmentBackgroundColor(it))
            binding.titleTv.setTextColor(myAppTheme.fragmentLargeTextColor(it))
            binding.searchCard.setCardBackgroundColor(myAppTheme.fragmentSearchCardColor(it))
            binding.recommendTv.setTextColor(myAppTheme.fragmentLargeTextColor(it))
            binding.descTv.setTextColor(myAppTheme.fragmentSmallTextColor(it))
            tabItemColor = myAppTheme.activityBottomNavViewBackColor(it)
            setTabs()
        }
    }

    private fun observeBaseNews() {
        lifecycleScope.launchWhenCreated {
            launch {
                homeViewModel.stateFlow.collect {
                    when (it) {
                        is NewsResource.Idle -> {
                            binding.progressBarHrv.visibility = View.GONE
                        }
                        is NewsResource.Loading -> {
                            pagerNewsAdapter.submitList(emptyList())
                            binding.progressBarHrv.visibility = View.VISIBLE
                        }
                        is NewsResource.Success -> {
                            pagerNewsAdapter.submitList(it.list)
                            binding.progressBarHrv.visibility = View.GONE
                        }
                        is NewsResource.Error -> {
                            Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT)
                                .show()
                            binding.progressBarHrv.visibility = View.GONE
                        }
                    }
                }
            }
            launch {
                homeViewModel.recommendedState.collect {
                    when (it) {
                        is NewsResource.Idle -> {
                            binding.progressBarRrv.visibility = View.GONE
                        }
                        is NewsResource.Loading -> {
                            binding.progressBarRrv.visibility = View.VISIBLE
                            binding.recommendTv.visibility = View.VISIBLE
                        }
                        is NewsResource.Success -> {
                            newsAdapter.submitList(it.list)
                            binding.recommendTv.visibility = View.VISIBLE
                            binding.progressBarRrv.visibility = View.GONE
                        }
                        is NewsResource.Error -> {
                            binding.recommendTv.visibility = View.GONE
                            binding.progressBarRrv.visibility = View.GONE
                            Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
        isObserved = true
    }

    private fun setOnClickListeners() {
        binding.apply {
            searchTv.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        SearchFragment()
                    )
                    .addToBackStack("search")
                    .commit()
            }
        }
    }

    private fun setUI() {
        binding.apply {
            binding.horizontalRv.adapter = pagerNewsAdapter
            binding.recommendRv.adapter = newsAdapter
        }
    }

    private fun initAdapters() {
        pagerNewsAdapter = PagerNewsAdapter(
            object : PagerNewsAdapter.OnItemClickListener {
                override fun onItemClicked(news: News) {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            ArticleFragment::class.java,
                            bundleOf(Pair("news", news))
                        )
                        .addToBackStack("article")
                        .commit()
                }
            },
            newsDao
        )
        newsAdapter = NewsAdapter(object : NewsAdapter.OnItemClickListener {
            override fun onItemClicked(news: News) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        ArticleFragment::class.java,
                        bundleOf(Pair("news", news))
                    )
                    .addToBackStack("article")
                    .commit()
            }
        })

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTabs() {
        val gson = Gson()
        val jsonText: String = sPref.getString("topic", "")!!
        val list = gson.fromJson(
            jsonText,
            Array<String>::class.java
        )
        val builder = StringBuilder()
        list.forEachIndexed { index, s ->
            if (index != list.size - 1)
                builder.append("$s,")
            else builder.append(s)
        }

        var language = LocaleHelper.getLanguage(binding.root.context)
        if (language!!.isEmpty()) {
            language = "en"
        }

        val pagesList = resources.getStringArray(R.array.category_list)
        val categoryNameList = resources.getStringArray(R.array.category_name_list)

        homeViewModel.getNews(pagesList[0].lowercase(), language)

        homeViewModel.getRecommendedNews(builder.toString(), language)
        val count: Int = pagesList.size
        for (i in 0 until count) {
            binding.tabLayout.addTab(binding.tabLayout.newTab(), i)
            val tabView: View = ItemTabBinding.inflate(layoutInflater).root
            val textView = tabView.findViewById<TextView>(R.id.tab_title)
            val cardView = tabView.findViewById<CardView>(R.id.card_view)
            textView.text = categoryNameList[i]
            if (i == 0) {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
                textView.setTextColor(Color.WHITE)
            } else {
                tabItemColor?.let {
                    cardView.setCardBackgroundColor(it)
                }
            }
            tabView.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                    return if (networkHelper.isNetworkConnected()) {
                        false
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            R.string.not_connected,
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                }

            })
            binding.tabLayout.getTabAt(i)?.customView = tabView
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabView = tab.customView!!
                val cardView = tabView.findViewById<CardView>(R.id.card_view)
                val textView = tabView.findViewById<TextView>(R.id.tab_title)

                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.blue
                    )
                )
                textView.setTextColor(Color.WHITE)

                homeViewModel.getNews(pagesList[tab.position].lowercase(), language)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabView = tab.customView!!
                val cardView = tabView.findViewById<CardView>(R.id.card_view)
                val textView = tabView.findViewById<TextView>(R.id.tab_title)

                tabItemColor?.let {
                    cardView.setCardBackgroundColor(it)
                }
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_blue))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }
}