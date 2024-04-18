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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.container.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.github.mathsemilio.syllabaryrandomizer.R

class MainActivityViewImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : MainActivityView() {

    private lateinit var appBarLayoutApp: AppBarLayout
    private lateinit var materialToolbarApp: MaterialToolbar
    private lateinit var frameLayoutFragmentContainer: FrameLayout

    init {
        rootView = layoutInflater.inflate(R.layout.activity_main, parent, false)

        initializeViews()

        attachToolbarNavigationIconOnClickListener()
    }

    private fun initializeViews() {
        appBarLayoutApp = findViewById(R.id.app_bar_layout_app)
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutFragmentContainer = findViewById(R.id.frame_layout_fragment_container)
    }

    private fun attachToolbarNavigationIconOnClickListener() {
        materialToolbarApp.setNavigationOnClickListener {
            notify { listener ->
                listener.onToolbarNavigationIconClicked()
            }
        }
    }

    override val fragmentContainerId: Int
        get() = frameLayoutFragmentContainer.id

    override fun showToolbar() {
        appBarLayoutApp.isVisible = true
    }

    override fun hideToolbar() {
        appBarLayoutApp.isVisible = false
    }
}
