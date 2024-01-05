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

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.*
import com.github.mathsemilio.syllabaryrandomizer.common.util.getDialogTag
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.info.InfoDialog
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.prompt.builder.SingleOptionPromptDialogBuilder
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.prompt.builder.TwoOptionPromptDialogBuilder
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.theme.ThemeDialog
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.timepicker.TimePickerDialog

class DialogManager(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {
    val dialogTag
        get() = fragmentManager.getDialogTag()

    fun showCorrectAnswerDialog() {
        val promptDialog = SingleOptionPromptDialogBuilder
            .withTitle(context.getString(R.string.alert_dialog_correct_answer_title))
            .withMessage(context.getString(R.string.alert_dialog_correct_answer_message))
            .withPositiveButtonText(context.getString(R.string.alert_dialog_continue_button_text))
            .setIsCancelableTo(false)
            .build()

        promptDialog.show(fragmentManager, TAG_CORRECT_ANSWER_DIALOG)
    }

    fun showWrongAnswerDialog(correctRomanization: String) {
        val promptDialog = SingleOptionPromptDialogBuilder
            .withTitle(context.getString(R.string.alert_dialog_wrong_answer_title))
            .withMessage(context.getString(
                    R.string.alert_dialog_wrong_answer_message,
                    correctRomanization
                ))
            .withPositiveButtonText(context.getString(R.string.alert_dialog_continue_button_text))
            .setIsCancelableTo(false)
            .build()

        promptDialog.show(fragmentManager, TAG_WRONG_ANSWER_DIALOG)
    }

    fun showTimeOverDialog(correctRomanization: String) {
        val promptDialog = SingleOptionPromptDialogBuilder
            .withTitle(context.getString(R.string.alert_dialog_time_over_title))
            .withMessage(context.getString(
                    R.string.alert_dialog_wrong_answer_message,
                    correctRomanization
                ))
            .withPositiveButtonText(context.getString(R.string.alert_dialog_continue_button_text))
            .setIsCancelableTo(false)
            .build()

        promptDialog.show(fragmentManager, TAG_TIME_OVER_DIALOG)
    }

    fun showGamePausedDialog() {
        val promptDialog = SingleOptionPromptDialogBuilder
            .withTitle(context.getString(R.string.alert_dialog_game_paused_title))
            .withMessage(context.getString(R.string.alert_dialog_game_paused_message))
            .withPositiveButtonText(context.getString(R.string.alert_dialog_continue_button_text))
            .setIsCancelableTo(false)
            .build()

        promptDialog.show(fragmentManager, TAG_GAME_PAUSED_DIALOG)
    }

    fun showExitGameDialog() {
        val promptDialog = TwoOptionPromptDialogBuilder
            .withTitle(context.getString(R.string.alert_dialog_exit_game_title))
            .withMessage(context.getString(R.string.alert_dialog_exit_game_message))
            .withPositiveButtonText(context.getString(R.string.alert_dialog_continue_button_text))
            .withNegativeButtonText(context.getString(R.string.alert_dialog_exit_button_text))
            .setIsCancelableTo(false)
            .build()

        promptDialog.show(fragmentManager, TAG_EXIT_GAME_DIALOG)
    }

    fun showClearPerfectScoresDialog() {
        val promptDialog = TwoOptionPromptDialogBuilder
            .withTitle(context.getString(R.string.clear_perfect_scores_dialog_title))
            .withMessage(context.getString(R.string.clear_perfect_scores_dialog_message))
            .withPositiveButtonText(context.getString(R.string.clear_perfect_scores_dialog_positive_button_text))
            .withNegativeButtonText(context.getString(R.string.alert_dialog_cancel_button_text))
            .setIsCancelableTo(true)
            .build()

        promptDialog.show(fragmentManager, null)
    }

    fun showAppThemeDialog() {
        val appThemeDialog = ThemeDialog()
        appThemeDialog.show(fragmentManager, null)
    }

    fun showTimePickerDialog(): TimePickerDialog {
        val timePickerDialog = TimePickerDialog()
        timePickerDialog.show(fragmentManager, null)
        return timePickerDialog
    }

    fun showPostNotificationDeniedDialog() {
        val dialog = InfoDialog.newInstance(
            context.getString(R.string.post_notifications_dialog_title),
            context.getString(R.string.post_notifications_dialog_message),
            context.getString(R.string.post_notifications_dialog_button_text)
        )

        dialog.show(fragmentManager, null)
    }

    fun showPostNotificationDeniedPermanentlyDialog() {
        val dialog = InfoDialog.newInstance(
            context.getString(R.string.post_notifications_dialog_title),
            context.getString(R.string.post_notification_manually_dialog_message),
            context.getString(R.string.post_notifications_dialog_button_text)
        )

        dialog.show(fragmentManager, null)
    }
}
