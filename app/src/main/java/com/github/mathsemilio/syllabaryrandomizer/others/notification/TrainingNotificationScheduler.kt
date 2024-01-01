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

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.mathsemilio.syllabaryrandomizer.common.TRAINING_NOTIFICATION_WORK_TAG
import com.github.mathsemilio.syllabaryrandomizer.common.observable.BaseObservable
import java.util.concurrent.TimeUnit

class TrainingNotificationScheduler(
    private val context: Context
) : BaseObservable<TrainingNotificationScheduler.Listener>() {

    interface Listener {
        fun onTrainingNotificationScheduled(timeSetByUser: Long)

        fun onTrainingNotificationInvalidTimeSet()
    }

    private fun scheduleNotification(initialDelay: Long) {
        val notifyUserWorkRequest = OneTimeWorkRequestBuilder<TrainingNotificationWorker>()
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .addTag(TRAINING_NOTIFICATION_WORK_TAG)
            .build()

        WorkManager.getInstance(context).also { instance ->
            instance.enqueue(notifyUserWorkRequest)
        }
    }

    fun schedule(timeSetInMillis: Long) {
        if (timeSetInMillis > System.currentTimeMillis()) {
            scheduleNotification(timeSetInMillis - System.currentTimeMillis())
            notifyTrainingNotificationScheduled(timeSetInMillis)
        } else {
            notifyTrainingNotificationInvalidTimeSet()
        }
    }

    private fun notifyTrainingNotificationScheduled(timeSetInMillis: Long) {
        notify { listener ->
            listener.onTrainingNotificationScheduled(timeSetInMillis)
        }
    }

    private fun notifyTrainingNotificationInvalidTimeSet() {
        notify { listener ->
            listener.onTrainingNotificationInvalidTimeSet()
        }
    }

    fun cancel() {
        WorkManager.getInstance(context).also { instance ->
            instance.cancelAllWorkByTag(TRAINING_NOTIFICATION_WORK_TAG)
        }
    }
}
