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

package com.github.mathsemilio.syllabaryrandomizer.storage.source

import android.content.Context
import android.os.Build
import androidx.preference.PreferenceManager
import com.github.mathsemilio.syllabaryrandomizer.common.*

class PreferencesSource(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPreferences.edit()

    var trainingNotificationSwitchState: Boolean
        get() = sharedPreferences.getBoolean(NOTIFICATION_SWITCH_STATE_KEY, false)
        set(value) {
            editor.apply {
                putBoolean(NOTIFICATION_SWITCH_STATE_KEY, value)
                apply()
            }
        }

    var trainingNotificationTimeSet: Long
        get() = sharedPreferences.getLong(NOTIFICATION_TIME_SET_KEY, 0L)
        set(value) {
            editor.apply {
                putLong(NOTIFICATION_TIME_SET_KEY, value)
                apply()
            }
        }

    var themeValue: Int
        get() {
            return sharedPreferences.getInt(
                THEME_KEY,
                when (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    true -> 0
                    false -> 2
                }
            )
        }
        set(value) {
            editor.apply {
                putInt(THEME_KEY, value)
                apply()
            }
        }

    val perfectScoresAchieved
        get() = sharedPreferences.getInt(PERFECT_SCORES_KEY, 0)

    val defaultDifficultyOption: String
        get() = sharedPreferences.getString(
            DEFAULT_GAME_DIFFICULTY_PREFERENCE_KEY,
            SHOW_DIFFICULTY_OPTIONS
        ) ?: SHOW_DIFFICULTY_OPTIONS

    val isSoundEffectsEnabled
        get() = sharedPreferences.getBoolean(SOUND_EFFECTS_PREFERENCE_KEY, true)

    val isSyllabarySoundsEnabled
        get() = sharedPreferences.getBoolean(SYLLABARY_SOUNDS_PREFERENCE, true)

    fun incrementPerfectScoreAchieved() {
        editor.apply {
            putInt(PERFECT_SCORES_KEY, perfectScoresAchieved.inc())
            apply()
        }
    }

    fun clearPerfectScoresAchieved() {
        editor.apply {
            putInt(PERFECT_SCORES_KEY, 0)
            apply()
        }
    }
}
