package com.azamovhudstc.newsappwithdagger2.ui.fregment.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.App
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News
import com.azamovhudstc.newsappwithdagger2.databinding.FragmentArticleBinding
import com.azamovhudstc.newsappwithdagger2.repositories.NewsRepository
import com.azamovhudstc.newsappwithdagger2.themes.MyAppTheme
import com.azamovhudstc.newsappwithdagger2.ui.activity.MainView
import com.azamovhudstc.newsappwithdagger2.utils.Topics.appComponent
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import javax.inject.Inject

private const val ARG_PARAM1 = "news"

class ArticleFragment : ThemeFragment(), AppBarLayout.OnOffsetChangedListener {

    private var news: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            news = it.getSerializable(ARG_PARAM1) as News
        }
    }

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
 //   private var state: State? = null

    @Inject
    lateinit var repository: NewsRepository
    private var localNews: News? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        appComponent.inject(this)
        (activity as MainView?)?.hideBottomBar()
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.mytoolbar)
        binding.appBar.addOnOffsetChangedListener(this)

        if (news != null) {
            setData()
        }
        setOnClickListeners()

        return binding.root
    }

    override fun syncTheme(appTheme: AppTheme) {

        val myAppTheme = appTheme as MyAppTheme
        context?.let {
            binding.root.setBackgroundColor(myAppTheme.fragmentBackgroundColor(it))
            binding.nestedScrollView.background = myAppTheme.fragmentBackDrawable(it)
            binding.resultTv.setTextColor(myAppTheme.fragmentLargeTextColor(it))
            binding.contentTv.setTextColor(myAppTheme.fragmentSmallTextColor(it))
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            backIv.setOnClickListener {
                (activity as MainView?)?.backPressed()
            }
            saveIv.setOnClickListener {
                if (localNews!!.isSaved) {
                    binding.saveIv.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                    localNews!!.isSaved = false
                    repository.insertNews(localNews!!)
                } else {
                    binding.saveIv.setImageResource(R.drawable.ic_baseline_bookmark_24)
                    localNews?.isSaved = true
                    repository.insertNews(localNews!!)
                }
            }

            shareIv.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, news?.title)
                    putExtra(Intent.EXTRA_TEXT, news?.description)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun setData() {
        localNews = repository.getNewsByUUID(news?.uuid!!)
        if (localNews != null && localNews!!.isSaved) {
            binding.saveIv.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }else{
            localNews = news
        }
        binding.apply {
            if (news!!.image_url != null && news!!.image_url!!.isNotEmpty()) {
                Picasso.get().load(news!!.image_url).into(imageView)
            }
            sourceBtn.text = news!!.source ?: "Nuntium"
            titleTv.text = news?.title ?: ""
            if (news!!.snippet != null && news!!.snippet!!.isNotEmpty()) {
                contentTv.text = news?.snippet
            } else contentTv.text = news?.description
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        /*if (verticalOffset == 0) {
            if (state != State.EXPANDED) {*//*
                setVisibilityLayoutViews(View.VISIBLE)
                setVisibilityToolbarViews(View.GONE)*//*
            }
            state = State.EXPANDED
        } else if (abs(verticalOffset) >= appBarLayout?.totalScrollRange!!) {
            if (state != State.COLLAPSED) {*//*
                setVisibilityLayoutViews(View.GONE)
                setVisibilityToolbarViews(View.VISIBLE)*//*
            }
            state = State.COLLAPSED
        } else {
            if (state != State.IDLE) {
            }
            state = State.IDLE
        }*/
    }
}
/*

private enum class State {
    EXPANDED, COLLAPSED, IDLE
}*/
