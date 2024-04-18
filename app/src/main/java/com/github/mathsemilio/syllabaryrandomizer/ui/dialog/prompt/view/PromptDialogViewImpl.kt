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

package com.github.mathsemilio.syllabaryrandomizer.ui.dialog.prompt.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.github.mathsemilio.syllabaryrandomizer.R

class PromptDialogViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : PromptDialogView() {

    private lateinit var textViewPromptDialogTitle: TextView
    private lateinit var textViewPromptDialogMessage: TextView
    private lateinit var buttonPromptDialogPositive: MaterialButton
    private lateinit var buttonPromptDialogNegative: MaterialButton

    init {
        rootView = layoutInflater.inflate(R.layout.prompt_dialog, container, false)

        initializeViews()
    }

    private fun initializeViews() {
        textViewPromptDialogTitle = findViewById(R.id.text_view_prompt_dialog_title)
        textViewPromptDialogMessage = findViewById(R.id.text_view_prompt_dialog_message)
        buttonPromptDialogPositive = findViewById(R.id.button_prompt_dialog_positive)
        buttonPromptDialogNegative = findViewById(R.id.button_prompt_dialog_negative)
    }

    override fun setTitle(title: String) {
        textViewPromptDialogTitle.text = title
    }

    override fun setMessage(message: String) {
        textViewPromptDialogMessage.text = message
    }

    override fun setPositiveButtonText(positiveButtonText: String) {
        buttonPromptDialogPositive.apply {
            text = positiveButtonText
            setOnClickListener { notifyPositiveButtonClicked() }
        }
    }

    private fun notifyPositiveButtonClicked() {
        notify { listener ->
            listener.onPositiveButtonClicked()
        }
    }

    override fun setNegativeButtonText(negativeButtonText: String?) {
        if (negativeButtonText != null)
            buttonPromptDialogNegative.apply {
                text = negativeButtonText
                setOnClickListener { notifyNegativeButtonClicked() }
            }
        else
            buttonPromptDialogNegative.isVisible = false
    }

    private fun notifyNegativeButtonClicked() {
        notify { listener ->
            listener.onNegativeButtonClicked()
        }
    }
}
