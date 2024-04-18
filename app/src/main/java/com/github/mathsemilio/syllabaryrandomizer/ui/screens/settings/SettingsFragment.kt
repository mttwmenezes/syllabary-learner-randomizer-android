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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.settings

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import com.github.mathsemilio.syllabaryrandomizer.BuildConfig
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.*
import com.github.mathsemilio.syllabaryrandomizer.common.eventbus.EventListener
import com.github.mathsemilio.syllabaryrandomizer.common.eventbus.EventSubscriber
import com.github.mathsemilio.syllabaryrandomizer.common.util.formatTimeInMillis
import com.github.mathsemilio.syllabaryrandomizer.others.notification.TrainingNotificationScheduler
import com.github.mathsemilio.syllabaryrandomizer.storage.manager.PreferencesManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.BasePreferenceFragment
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.DialogManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.MessagesManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.ToolbarVisibilityManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.permission.PermissionRequestResult
import com.github.mathsemilio.syllabaryrandomizer.ui.common.permission.PermissionHandler
import com.github.mathsemilio.syllabaryrandomizer.ui.common.permission.Permission
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.prompt.PromptDialogEvent
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.timepicker.TimePickerDialog
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.timepicker.TimePickerDialogEvent

class SettingsFragment : BasePreferenceFragment(),
    TrainingNotificationScheduler.Listener,
    PermissionHandler.Listener,
    EventListener {

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    private lateinit var trainingNotificationScheduler: TrainingNotificationScheduler
    private lateinit var toolbarVisibilityManager: ToolbarVisibilityManager
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var permissionHandler: PermissionHandler
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var timePickerDialog: TimePickerDialog

    private lateinit var trainingNotificationPreference: SwitchPreferenceCompat
    private lateinit var gameDefaultDifficultyPreference: ListPreference
    private lateinit var clearPerfectScoresPreference: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings, rootKey)

        initialize()

        trainingNotificationPreference = findPreference(TRAINING_NOTIFICATION_PREFERENCE_KEY)!!
        gameDefaultDifficultyPreference = findPreference(DEFAULT_GAME_DIFFICULTY_PREFERENCE_KEY)!!
        clearPerfectScoresPreference = findPreference(CLEAR_PERFECT_SCORES_PREFERENCE_KEY)!!

        setupClearPerfectScoresPreference()
    }

    private fun initialize() {
        trainingNotificationScheduler = compositionRoot.trainingNotificationScheduler
        toolbarVisibilityManager = compositionRoot.toolbarVisibilityManager
        preferencesManager = compositionRoot.preferencesManager
        permissionHandler = compositionRoot.permissionHandler
        messagesManager = compositionRoot.messagesManager
        dialogManager = compositionRoot.dialogManager

        eventSubscriber = compositionRoot.eventSubscriber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTrainingNotificationPreference()

        setupDefaultGameDifficultyPreference()

        setupAppThemePreference()

        findPreference<Preference>(APP_BUILD_PREFERENCE_KEY)?.summary = BuildConfig.VERSION_NAME
    }

    private fun setupTrainingNotificationPreference() {
        when (preferencesManager.trainingNotificationSwitchState) {
            true -> {
                trainingNotificationPreference.apply {
                    isChecked = true
                    title = getString(R.string.preference_title_training_reminder_checked)
                    summaryOn = getString(
                        R.string.preference_summary_training_reminder_activated,
                        preferencesManager.trainingNotificationTimeSet
                            .formatTimeInMillis(requireContext())
                    )
                }
            }

            false -> {
                trainingNotificationPreference.apply {
                    isChecked = false
                    title = getString(R.string.preference_title_training_reminder_unchecked)
                }
            }
        }
    }

    private fun setupDefaultGameDifficultyPreference() {
        gameDefaultDifficultyPreference.setSummaryProvider {
            return@setSummaryProvider when (preferencesManager.defaultDifficultyValue) {
                SHOW_DIFFICULTY_OPTIONS -> getString(R.string.difficulty_entry_default)
                DEFAULT_DIFFICULTY_BEGINNER -> getString(R.string.difficulty_beginner)
                DEFAULT_DIFFICULTY_MEDIUM -> getString(R.string.difficulty_medium)
                DEFAULT_DIFFICULTY_HARD -> getString(R.string.difficulty_hard)
                else -> throw IllegalArgumentException(ILLEGAL_DEFAULT_DIFFICULTY_VALUE)
            }
        }
    }

    private fun setupClearPerfectScoresPreference() {
        preferencesManager.perfectScoresAchieved.also { perfectScoresAchieved ->
            if (perfectScoresAchieved == 0)
                clearPerfectScoresPreference.isVisible = false
            else
                clearPerfectScoresPreference.setSummaryProvider {
                    return@setSummaryProvider getString(
                        R.string.preference_summary_clear_perfect_scores, perfectScoresAchieved
                    )
                }
        }
    }

    private fun setupAppThemePreference() {
        findPreference<Preference>(THEME_PREFERENCE_KEY)?.setSummaryProvider {
            return@setSummaryProvider when (preferencesManager.themeValue) {
                LIGHT_THEME_VALUE -> getString(R.string.app_theme_dialog_option_light_theme)
                DARK_THEME_VALUE -> getString(R.string.app_theme_dialog_option_dark_theme)
                FOLLOW_SYSTEM_THEME_VALUE -> getString(R.string.app_theme_dialog_option_follow_system)
                else -> throw IllegalArgumentException(ILLEGAL_APP_THEME_VALUE)
            }
        }
    }

    private fun onTrainingNotificationPreferenceClick() {
        if (trainingNotificationPreference.isChecked) {
            checkForPostNotificationsPermission()
        } else {
            trainingNotificationPreference.title =
                getString(R.string.preference_title_training_reminder_unchecked)

            preferencesManager.trainingNotificationSwitchState = false

            trainingNotificationScheduler.cancel()
        }
    }

    private fun checkForPostNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (permissionHandler.hasPermission(Permission.POST_NOTIFICATIONS)) {
                timePickerDialog = dialogManager.showTimePickerDialog()
            } else {
                permissionHandler.request(Permission.POST_NOTIFICATIONS)
            }
        } else {
            timePickerDialog = dialogManager.showTimePickerDialog()
        }
    }

    override fun onTrainingNotificationScheduled(timeSetByUser: Long) {
        trainingNotificationPreference.apply {
            title = getString(R.string.preference_title_training_reminder_checked)
            summaryOn = getString(
                R.string.preference_summary_training_reminder_activated,
                timeSetByUser.formatTimeInMillis(requireContext())
            )
        }

        preferencesManager.trainingNotificationSwitchState = true
        preferencesManager.trainingNotificationTimeSet = timeSetByUser

        messagesManager.showTrainingReminderSetSuccessfullyMessage()
    }

    override fun onTrainingNotificationInvalidTimeSet() {
        timePickerDialog.dismiss()

        trainingNotificationPreference.isChecked = false

        preferencesManager.trainingNotificationSwitchState = false

        messagesManager.showTrainingReminderSetFailedMessage()
    }

    override fun onPermissionRequestResult(result: PermissionRequestResult) {
        when (result) {
            PermissionRequestResult.GRANTED -> {
                timePickerDialog = dialogManager.showTimePickerDialog()
            }

            PermissionRequestResult.DENIED -> {
                dialogManager.showPostNotificationDeniedDialog()
                trainingNotificationPreference.isChecked = false
            }

            PermissionRequestResult.DENIED_PERMANENTLY -> {
                dialogManager.showPostNotificationDeniedPermanentlyDialog()
                trainingNotificationPreference.isChecked = false
            }
        }
    }

    override fun onEvent(event: Any) {
        when (event) {
            is TimePickerDialogEvent -> handleTimePickerDialogEvent(event)
            is PromptDialogEvent -> handleClearPerfectScoresPromptDialogEvent(event)
        }
    }

    private fun handleTimePickerDialogEvent(event: TimePickerDialogEvent) {
        when (event) {
            is TimePickerDialogEvent.TimeSet ->
                trainingNotificationScheduler.schedule(event.timeSetInMillis)

            TimePickerDialogEvent.Dismissed ->
                trainingNotificationPreference.isChecked = false
        }
    }

    private fun handleClearPerfectScoresPromptDialogEvent(event: PromptDialogEvent) {
        when (event) {
            PromptDialogEvent.PositiveButtonClicked -> {
                preferencesManager.clearPerfectScoresAchieved()
                findPreference<Preference>(CLEAR_PERFECT_SCORES_PREFERENCE_KEY)?.isVisible = false
            }

            PromptDialogEvent.NegativeButtonClicked -> { /* no-op - No action required */ }
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            TRAINING_NOTIFICATION_PREFERENCE_KEY -> onTrainingNotificationPreferenceClick()
            CLEAR_PERFECT_SCORES_PREFERENCE_KEY -> dialogManager.showClearPerfectScoresDialog()
            THEME_PREFERENCE_KEY -> dialogManager.showAppThemeDialog()
        }

        return super.onPreferenceTreeClick(preference)
    }

    override fun onStart() {
        super.onStart()
        trainingNotificationScheduler.addListener(this)
        permissionHandler.addListener(this)
        eventSubscriber.subscribe(this)
    }

    override fun onResume() {
        super.onResume()
        toolbarVisibilityManager.showToolbar()
    }

    override fun onStop() {
        super.onStop()
        eventSubscriber.unsubscribe(this)
        toolbarVisibilityManager.hideToolbar()
        trainingNotificationScheduler.removeListener(this)
        permissionHandler.removeListener(this)
    }
}
