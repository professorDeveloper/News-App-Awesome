package com.azamovhudstc.newsappwithdagger2.utils

import android.content.Context
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.di.component.AppComponent

object Topics {
    lateinit var appComponent: AppComponent

    fun getTopicsList(context: Context): ArrayList<String> {
        return arrayListOf(
            "\uD83D\uDCF0   ${context.getString(R.string.general)}",
            "⚖️   ${context.getString(R.string.politics)}",
            "\uD83C\uDFC8   ${context.getString(R.string.sports)}",
            "\u200E\u200D\uD83D\uDCBC   ${context.getString(R.string.business)}",
            "✈${context.getString(R.string.travel)}",
            "\uD83E\uDD16   ${context.getString(R.string.tech)}",
            "\uD83D\uDC68\u200D⚕️   ${context.getString(R.string.health)}",
            "\uD83C\uDF54   ${context.getString(R.string.food)}",
            "\uD83D\uDD2C   ${context.getString(R.string.science)}",
            "\uD83C\uDFA1   ${context.getString(R.string.entertainment)}"
        )
    }
    fun getTopicsListText(): ArrayList<String> {
        return arrayListOf(
            "General",
            "Politics",
            "Sports",
            "Business",
            "Travel",
            "Tech",
            "Health",
            "Food",
            "Science",
            "Entertainment"
        )
    }
}