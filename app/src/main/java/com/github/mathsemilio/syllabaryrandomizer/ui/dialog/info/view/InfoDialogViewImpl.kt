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

package com.github.mathsemilio.syllabaryrandomizer.ui.dialog.info.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.github.mathsemilio.syllabaryrandomizer.R
import com.google.android.material.button.MaterialButton

class InfoDialogViewImpl(inflater: LayoutInflater, container: ViewGroup?) : InfoDialogView() {

    private var title: TextView
    private var message: TextView
    private var button: MaterialButton

    init {
        rootView = inflater.inflate(R.layout.info_dialog, container)

        title = findViewById(R.id.title)
        message = findViewById(R.id.message)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            notify { it.onButtonClicked() }
        }
    }

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setMessage(message: String) {
        this.message.text = message
    }

    override fun setButtonText(text: String) {
        button.text = text
    }
}
