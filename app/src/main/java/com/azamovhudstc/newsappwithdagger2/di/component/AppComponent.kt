package com.azamovhudstc.newsappwithdagger2.di.component

import com.azamovhudstc.newsappwithdagger2.di.module.DatabaseModule
import com.azamovhudstc.newsappwithdagger2.di.module.NetworkModule
import com.azamovhudstc.newsappwithdagger2.ui.fregment.article.ArticleFragment
import com.azamovhudstc.newsappwithdagger2.ui.fregment.bookmark.BookmarkFragment
import com.azamovhudstc.newsappwithdagger2.ui.fregment.category.CategoryFragment
import com.azamovhudstc.newsappwithdagger2.ui.fregment.home.HomeFragment
import com.azamovhudstc.newsappwithdagger2.ui.activity.MainActivity
import com.azamovhudstc.newsappwithdagger2.ui.fregment.profile.ProfileFragment
import com.azamovhudstc.newsappwithdagger2.ui.fregment.welcome.SplashFragment
import com.azamovhudstc.newsappwithdagger2.ui.fregment.welcome.TopicFragment
import com.azamovhudstc.newsappwithdagger2.ui.fregment.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(articleFragment: ArticleFragment)

    fun inject(searchFragment: SearchFragment)

    fun inject(topicFragment: TopicFragment)

    fun inject(splashFragment: SplashFragment)

    fun inject(bookmarkFragment: BookmarkFragment)

    fun inject(categoryFragment: CategoryFragment)

    fun inject(profileFragment: ProfileFragment)


}