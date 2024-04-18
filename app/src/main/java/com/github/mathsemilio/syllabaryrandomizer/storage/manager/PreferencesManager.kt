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

package com.github.mathsemilio.syllabaryrandomizer.storage.manager

import android.content.Context
import com.github.mathsemilio.syllabaryrandomizer.storage.source.PreferencesSource

class PreferencesManager(context: Context) {

    private val preferencesEndpoint = PreferencesSource(context)

    val defaultDifficultyValue
        get() = preferencesEndpoint.defaultDifficultyOption

    val isSoundEffectsEnabled
        get() = preferencesEndpoint.isSoundEffectsEnabled

    val isSyllabarySoundsEnabled
        get() = preferencesEndpoint.isSyllabarySoundsEnabled

    var trainingNotificationSwitchState: Boolean
        get() = preferencesEndpoint.trainingNotificationSwitchState
        set(value) {
            preferencesEndpoint.trainingNotificationSwitchState = value
        }

    var trainingNotificationTimeSet: Long
        get() = preferencesEndpoint.trainingNotificationTimeSet
        set(value) {
            preferencesEndpoint.trainingNotificationTimeSet = value
        }

    val perfectScoresAchieved
        get() = preferencesEndpoint.perfectScoresAchieved

    var themeValue: Int
        get() = preferencesEndpoint.themeValue
        set(value) {
            preferencesEndpoint.themeValue = value
        }

    fun incrementPerfectScoresAchieved() {
        preferencesEndpoint.incrementPerfectScoreAchieved()
    }

    fun clearPerfectScoresAchieved() {
        preferencesEndpoint.clearPerfectScoresAchieved()
    }
}
