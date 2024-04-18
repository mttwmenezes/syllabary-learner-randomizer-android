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

package com.github.mathsemilio.syllabaryrandomizer.others.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.NOTIFICATION_CHANNEL_ID
import com.github.mathsemilio.syllabaryrandomizer.common.NOTIFICATION_ID
import com.github.mathsemilio.syllabaryrandomizer.common.PENDING_INTENT_REQUEST_ID
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.container.MainActivity

class TrainingNotificationHelper(private val context: Context) {

    private val launchMainActivityIntent: Intent
        get() = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

    private val trainingNotificationPendingIntent: PendingIntent
        get() = PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_ID,
            launchMainActivityIntent,
            pendingIntentFlag
        )

    private val pendingIntentFlag: Int
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE
            else
                0
        }

    fun notifyUser() {
        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        createNotificationChannel(notificationManager)

        notificationManager.notify(NOTIFICATION_ID, buildNotification().build())
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.training_reminder_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.training_reminder_channel_description)
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_training_notification)
            setContentTitle(context.getString(R.string.training_reminder_content_title))
            setContentText(context.getString(R.string.training_reminder_content_text))
            setCategory(NotificationCompat.CATEGORY_REMINDER)
            setContentIntent(trainingNotificationPendingIntent)
            priority = NotificationCompat.PRIORITY_HIGH
            setAutoCancel(true)
        }
    }
}
