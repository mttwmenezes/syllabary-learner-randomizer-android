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

package com.github.mathsemilio.syllabaryrandomizer.common.util

import android.content.Context
import android.media.SoundPool
import androidx.fragment.app.FragmentManager

fun SoundPool.playSFX(soundEffectID: Int, volume: Float) {
    play(soundEffectID, volume, volume, 1, 0, 1F)
}

fun Long.formatTimeInMillis(context: Context): String {
    return when (android.text.format.DateFormat.is24HourFormat(context)) {
        true -> android.text.format.DateFormat.format("HH:mm", this).toString()
        false -> android.text.format.DateFormat.format("h:mm a", this).toString()
    }
}

fun FragmentManager.getDialogTag(): String {
    var dialogTag = ""

    this.fragments.forEach { fragment ->
        dialogTag = fragment.tag.toString()
    }

    return dialogTag
}
