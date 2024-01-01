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

object SingleOptionPromptDialogBuilder {

    private lateinit var title: String
    private lateinit var message: String
    private lateinit var positiveButtonText: String
    private var isCancelable = false

    fun withTitle(title: String): SingleOptionPromptDialogBuilder {
        this.title = title
        return this
    }

    fun withMessage(message: String): SingleOptionPromptDialogBuilder {
        this.message = message
        return this
    }

    fun withPositiveButtonText(positiveButtonText: String): SingleOptionPromptDialogBuilder {
        this.positiveButtonText = positiveButtonText
        return this
    }

    fun setIsCancelableTo(isCancelable: Boolean): SingleOptionPromptDialogBuilder {
        this.isCancelable = isCancelable
        return this
    }

    fun build(): PromptDialog {
        return PromptDialog.newInstance(
            title,
            message,
            positiveButtonText,
            negativeButtonText = null,
            isCancelable
        )
    }
}
