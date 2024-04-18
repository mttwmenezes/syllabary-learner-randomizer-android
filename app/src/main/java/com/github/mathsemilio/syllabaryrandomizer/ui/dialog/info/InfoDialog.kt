/*
Copyright 2023 Matheus Menezes

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

package com.github.mathsemilio.syllabaryrandomizer.ui.dialog.info

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.github.mathsemilio.syllabaryrandomizer.common.ARG_DIALOG_MESSAGE
import com.github.mathsemilio.syllabaryrandomizer.common.ARG_DIALOG_TITLE
import com.github.mathsemilio.syllabaryrandomizer.common.ARG_IS_CANCELABLE
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.BaseDialogFragment
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.info.view.InfoDialogView
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.info.view.InfoDialogViewImpl
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InfoDialog : BaseDialogFragment(), InfoDialogView.Listener {

    private lateinit var view: InfoDialogView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        view = InfoDialogViewImpl(LayoutInflater.from(requireContext()), null).apply {
            setTitle(requireArguments().getString(ARG_DIALOG_TITLE).orEmpty())
            setMessage(requireArguments().getString(ARG_DIALOG_MESSAGE).orEmpty())
            setButtonText(requireArguments().getString(ARG_BUTTON_TEXT).orEmpty())
        }

        val dialogBuilder = MaterialAlertDialogBuilder(requireContext()).apply {
            setView(view.rootView)
            isCancelable = requireArguments().getBoolean(ARG_IS_CANCELABLE)
        }

        return dialogBuilder.create()
    }

    override fun onButtonClicked() = dismiss()

    override fun onStart() {
        super.onStart()
        view.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
    }

    companion object {
        private const val ARG_BUTTON_TEXT = "button_text"

        @JvmStatic
        fun newInstance(
            title: String,
            message: String,
            buttonText: String,
            isCancelable: Boolean = true
        ): InfoDialog {
            return InfoDialog().apply {
                arguments = Bundle(4).apply {
                    putString(ARG_DIALOG_TITLE, title)
                    putString(ARG_DIALOG_MESSAGE, message)
                    putString(ARG_BUTTON_TEXT, buttonText)
                    putBoolean(ARG_IS_CANCELABLE, isCancelable)
                }
            }
        }
    }
}
