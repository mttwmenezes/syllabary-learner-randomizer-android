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

import com.github.mathsemilio.syllabaryrandomizer.ui.common.provider.BackPressedCallbackProvider
import com.github.mathsemilio.syllabaryrandomizer.domain.eventchannel.GameEventChannel
import com.github.mathsemilio.syllabaryrandomizer.domain.backend.GameBackend
import com.github.mathsemilio.syllabaryrandomizer.others.notification.TrainingNotificationScheduler
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.syllabary.SyllabarySoundsPlayer
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.DialogManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.MessagesManager

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    private val application
        get() = activityCompositionRoot.application

    private val fragmentManager
        get() = activityCompositionRoot.fragmentManager

    val gameEventChannel
        get() = GameEventChannel(GameBackend())

    val backPressedCallbackProvider
        get() = BackPressedCallbackProvider

    val dialogManager
        get() = DialogManager(fragmentManager, application)

    val eventPublisher
        get() = activityCompositionRoot.eventPublisher

    val eventSubscriber
        get() = activityCompositionRoot.eventSubscriber

    val messagesManager
        get() = MessagesManager(application)

    val permissionHandler
        get() = activityCompositionRoot.permissionHandler

    val preferencesManager
        get() = activityCompositionRoot.preferencesManager

    val screensNavigator
        get() = activityCompositionRoot.screensNavigator

    val soundEffectsPlayer
        get() = activityCompositionRoot.soundEffectsPlayer

    val syllabarySoundsPlayer
        get() = SyllabarySoundsPlayer(application)

    val appThemeManager
        get() = activityCompositionRoot.appThemeManager

    val trainingNotificationScheduler
        get() = TrainingNotificationScheduler(application)

    val toolbarVisibilityManager
        get() = activityCompositionRoot.toolbarVisibilityManager
}
