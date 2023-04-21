package com.azamovhudstc.newsappwithdagger2.themes

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.azamovhudstc.newsappwithdagger2.R

class NightTheme : MyAppTheme {

    companion object {
        const val ThemeId = 1
    }

    override fun id(): Int {
        return ThemeId
    }

    override fun fragmentBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.fragment_back_night)
    }

    override fun activityBottomNavViewBackColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.bottom_nav_view_back_color_night)
    }

    override fun fragmentLargeTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.text_large_color_night)
    }

    override fun fragmentSmallTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.text_small_color_night)
    }

    override fun fragmentSearchCardColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.bottom_nav_view_back_color_night)
    }

    override fun fragmentCheckboxDrawable(context: Context): Drawable {
        return ContextCompat.getDrawable(context, R.drawable.topic_checkbox_back_night)!!
    }

    override fun fragmentBackDrawable(context: Context): Drawable {
        return ContextCompat.getDrawable(context, R.drawable.layout_back_night)!!
    }

}