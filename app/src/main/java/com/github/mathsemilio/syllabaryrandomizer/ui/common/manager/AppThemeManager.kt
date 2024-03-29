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

package com.github.mathsemilio.syllabaryrandomizer.ui.common.manager

import androidx.appcompat.app.AppCompatDelegate
import com.github.mathsemilio.syllabaryrandomizer.common.DARK_THEME_VALUE
import com.github.mathsemilio.syllabaryrandomizer.common.FOLLOW_SYSTEM_THEME_VALUE
import com.github.mathsemilio.syllabaryrandomizer.common.LIGHT_THEME_VALUE
import com.github.mathsemilio.syllabaryrandomizer.storage.manager.PreferencesManager

class AppThemeManager(private val preferencesManager: PreferencesManager) {

    val themeValue
        get() = preferencesManager.themeValue

    fun setLightAppTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        preferencesManager.themeValue = LIGHT_THEME_VALUE
    }

    fun setDarkAppTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        preferencesManager.themeValue = DARK_THEME_VALUE
    }

    fun setFollowSystemAppTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        preferencesManager.themeValue = FOLLOW_SYSTEM_THEME_VALUE
    }

    fun setThemeFromPreference() {
        when (themeValue) {
            LIGHT_THEME_VALUE ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_THEME_VALUE ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            FOLLOW_SYSTEM_THEME_VALUE ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
