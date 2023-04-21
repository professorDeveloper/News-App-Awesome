package com.azamovhudstc.newsappwithdagger2.themes

import android.content.Context
import android.graphics.drawable.Drawable
import com.dolatkia.animatedThemeManager.AppTheme

interface MyAppTheme : AppTheme {

    fun fragmentBackgroundColor(context: Context): Int
    fun activityBottomNavViewBackColor(context: Context): Int
    fun fragmentLargeTextColor(context: Context): Int
    fun fragmentSmallTextColor(context: Context): Int
    fun fragmentSearchCardColor(context: Context): Int
    fun fragmentCheckboxDrawable(context: Context): Drawable
    fun fragmentBackDrawable(context: Context): Drawable
}