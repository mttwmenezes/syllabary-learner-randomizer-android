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

package com.github.mathsemilio.syllabaryrandomizer.ui.common.permission

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.mathsemilio.syllabaryrandomizer.common.observable.BaseObservable

class PermissionHandler(
    private val activity: AppCompatActivity
) : BaseObservable<PermissionHandler.Listener>() {

    interface Listener {
        fun onPermissionRequestResult(result: PermissionRequestResult)
    }

    private lateinit var permission: Permission

    fun request(permission: Permission) {
        this.permission = permission
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission.androidName),
            permission.requestCode
        )
    }

    fun hasPermission(permission: Permission): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission.androidName
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun onRequestPermissionResult(
        requestCode: Int,
        androidPermissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (androidPermissions.isEmpty() || grantResults.isEmpty())
            notify { it.onPermissionRequestResult(PermissionRequestResult.DENIED) }

        if (validRequestCode(requestCode)) {
            when {
                permissionGranted(grantResults) ->
                    notify { it.onPermissionRequestResult(PermissionRequestResult.GRANTED) }

                shouldDisplayPermissionRequestRationale(androidPermissions.first()) ->
                    notify { it.onPermissionRequestResult(PermissionRequestResult.DENIED) }

                else ->
                    notify { it.onPermissionRequestResult(PermissionRequestResult.DENIED_PERMANENTLY) }
            }
        }
    }

    private fun validRequestCode(requestCode: Int) = permission.requestCode == requestCode

    private fun permissionGranted(grantResults: IntArray): Boolean {
        return grantResults.first() == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldDisplayPermissionRequestRationale(androidPermission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermission)
    }
}
