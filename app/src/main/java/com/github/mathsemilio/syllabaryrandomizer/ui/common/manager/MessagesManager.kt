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
import android.widget.Toast
import com.github.mathsemilio.syllabaryrandomizer.R

class MessagesManager(private val context: Context) {

    fun showTrainingReminderSetSuccessfullyMessage() {
        Toast.makeText(
            context,
            context.getString(R.string.training_reminder_set_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun showTrainingReminderSetFailedMessage() {
        Toast.makeText(
            context,
            context.getString(R.string.training_reminder_invalid_time_message),
            Toast.LENGTH_LONG
        ).show()
    }
}
