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

package com.github.mathsemilio.syllabaryrandomizer.ui.dialog.theme

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.util.getDefaultThemeOptionFrom
import com.github.mathsemilio.syllabaryrandomizer.common.util.getThemeOptionsArrayResourceId
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.AppThemeManager
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.BaseDialogFragment

class ThemeDialog : BaseDialogFragment() {

    private lateinit var appThemeManager: AppThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appThemeManager = compositionRoot.appThemeManager
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            val builder = MaterialAlertDialogBuilder(activity).apply {
                setTitle(getString(R.string.alert_dialog_title_select_an_option_below))
                setSingleChoiceItems(
                    getThemeOptionsArrayResourceId(),
                    getDefaultThemeOptionFrom(appThemeManager.themeValue)
                )
                { _, which ->
                    when (which) {
                        0 -> appThemeManager.setLightAppTheme()
                        1 -> appThemeManager.setDarkAppTheme()
                        2 -> appThemeManager.setFollowSystemAppTheme()
                    }
                }
                setNegativeButton(getString(R.string.alert_dialog_cancel_button_text))
                { _, _ -> dialog?.cancel() }
            }
            builder.create()
        }
    }
}
