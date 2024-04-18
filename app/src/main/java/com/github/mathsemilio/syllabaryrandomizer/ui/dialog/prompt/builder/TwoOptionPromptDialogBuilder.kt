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

package com.github.mathsemilio.syllabaryrandomizer.ui.dialog.prompt.builder

import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.prompt.PromptDialog

object TwoOptionPromptDialogBuilder {

    private lateinit var title: String
    private lateinit var message: String
    private lateinit var positiveButtonText: String
    private lateinit var negativeButtonText: String
    private var isCancelable = false

    fun withTitle(title: String): TwoOptionPromptDialogBuilder {
        TwoOptionPromptDialogBuilder.title = title
        return this
    }

    fun withMessage(message: String): TwoOptionPromptDialogBuilder {
        TwoOptionPromptDialogBuilder.message = message
        return this
    }

    fun withPositiveButtonText(positiveButtonText: String): TwoOptionPromptDialogBuilder {
        TwoOptionPromptDialogBuilder.positiveButtonText = positiveButtonText
        return this
    }

    fun withNegativeButtonText(negativeButtonText: String): TwoOptionPromptDialogBuilder {
        TwoOptionPromptDialogBuilder.negativeButtonText = negativeButtonText
        return this
    }

    fun setIsCancelableTo(isCancelable: Boolean): TwoOptionPromptDialogBuilder {
        TwoOptionPromptDialogBuilder.isCancelable = isCancelable
        return this
    }

    fun build(): PromptDialog {
        return PromptDialog.newInstance(
            title,
            message,
            positiveButtonText,
            negativeButtonText,
            isCancelable
        )
    }
}
