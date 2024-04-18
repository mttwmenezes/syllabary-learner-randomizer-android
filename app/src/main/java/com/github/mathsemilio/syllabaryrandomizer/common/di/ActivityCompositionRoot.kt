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

package com.github.mathsemilio.syllabaryrandomizer.common.di

import androidx.appcompat.app.AppCompatActivity
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.player.SoundEffectsPlayer
import com.github.mathsemilio.syllabaryrandomizer.storage.manager.PreferencesManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.AppThemeManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.delegate.FragmentContainerDelegate
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.ToolbarVisibilityManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.navigation.FragmentTransactionManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.navigation.ScreensNavigator
import com.github.mathsemilio.syllabaryrandomizer.ui.common.permission.PermissionHandler

class ActivityCompositionRoot(
    private val appCompatActivity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
   val permissionHandler by lazy {
       PermissionHandler(appCompatActivity)
   }

    val preferencesManager by lazy {
        PreferencesManager(application)
    }

    val screensNavigator by lazy {
        ScreensNavigator(
            FragmentTransactionManager(
                fragmentManager,
                appCompatActivity as FragmentContainerDelegate
            )
        )
    }

    val soundEffectsPlayer by lazy {
        SoundEffectsPlayer(application)
    }

    val appThemeManager by lazy {
        AppThemeManager(preferencesManager)
    }

    val toolbarVisibilityManager by lazy {
        ToolbarVisibilityManager()
    }

    val application
        get() = compositionRoot.application

    val eventPublisher
        get() = compositionRoot.eventPublisher

    val eventSubscriber
        get() = compositionRoot.eventSubscriber

    val fragmentManager
        get() = appCompatActivity.supportFragmentManager
}
