/*
Copyright 2020 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.github.mathsemilio.syllabaryrandomizer.common.util

import android.os.Build
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.DARK_THEME_VALUE
import com.github.mathsemilio.syllabaryrandomizer.common.FOLLOW_SYSTEM_THEME_VALUE
import com.github.mathsemilio.syllabaryrandomizer.common.ILLEGAL_APP_THEME_VALUE
import com.github.mathsemilio.syllabaryrandomizer.common.LIGHT_THEME_VALUE

fun getThemeOptionsArrayResourceId(): Int {
    return when (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        true -> R.array.app_theme_array_sdk_version_below_q
        else -> R.array.app_theme_array
    }
}

fun getDefaultThemeOptionFrom(themeValue: Int): Int {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        when (themeValue) {
            LIGHT_THEME_VALUE -> 0
            DARK_THEME_VALUE -> 1
            else -> throw IllegalArgumentException(ILLEGAL_APP_THEME_VALUE)
        }
    } else {
        when (themeValue) {
            LIGHT_THEME_VALUE -> 0
            DARK_THEME_VALUE -> 1
            FOLLOW_SYSTEM_THEME_VALUE -> 2
            else -> throw IllegalArgumentException(ILLEGAL_APP_THEME_VALUE)
        }
    }
}
