package com.azamovhudstc.newsappwithdagger2.ui.fregment.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.App
import com.azamovhudstc.newsappwithdagger2.ui.adapters.NewsAdapter
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News
import com.azamovhudstc.newsappwithdagger2.databinding.FragmentSearchBinding
import com.azamovhudstc.newsappwithdagger2.themes.MyAppTheme
import com.azamovhudstc.newsappwithdagger2.ui.fregment.article.ArticleFragment
import com.azamovhudstc.newsappwithdagger2.ui.activity.MainActivity
import com.azamovhudstc.newsappwithdagger2.ui.activity.MainView
import com.azamovhudstc.newsappwithdagger2.utils.LocaleHelper
import com.azamovhudstc.newsappwithdagger2.utils.NewsResource
import com.azamovhudstc.newsappwithdagger2.utils.Topics.appComponent
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchFragment : ThemeFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var isFragmentCreated = true

    @Inject
    lateinit var searchViewModel: SearchViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var isNotObserved = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        appComponent.inject(this)
        (activity as MainView?)?.hideBottomBar()
        setUpSearchItem()

        if (isNotObserved) {
            observeViewModel()
        }

        binding.rv.adapter = newsAdapter


        return binding.root
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.stateFlow.collect {
                when (it) {
                    is NewsResource.Idle -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    is NewsResource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        newsAdapter.submitList(emptyList())
                    }
                    is NewsResource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (it.list.isEmpty()){
                            Toast.makeText(
                                binding.root.context,
                                "News not found!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        newsAdapter.submitList(it.list)
                    }
                    is NewsResource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        isNotObserved = false
    }


    private fun setUpSearchItem() {
        if (isFragmentCreated) {
            binding.searchEt.post {
                binding.searchEt.requestFocus()
                val imgr =
                    (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imgr.showSoftInput(binding.searchEt, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        var language = LocaleHelper.getLanguage(binding.root.context)
        if (language!!.isEmpty()){ language = "en" }

        binding.searchEt.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_DONE) {
                    searchViewModel.getSearchedNews(p0?.text.toString(), language)
                    hideKeyboard()
                    return true
                }
                return false
            }
        })
        isFragmentCreated = false
    }


    private fun hideKeyboard() {
        (activity as MainActivity).currentFocus?.let { view ->
            val imm =
                (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun syncTheme(appTheme: AppTheme) {

        val myAppTheme = appTheme as MyAppTheme
        context?.let {
            // set background color
            binding.root.setBackgroundColor(myAppTheme.fragmentBackgroundColor(it))
            binding.searchCard.setCardBackgroundColor(myAppTheme.fragmentSearchCardColor(it))
            binding.titleTv.setTextColor(myAppTheme.fragmentLargeTextColor(it))
            binding.descTv.setTextColor(myAppTheme.fragmentSmallTextColor(it))
        }
    }

}