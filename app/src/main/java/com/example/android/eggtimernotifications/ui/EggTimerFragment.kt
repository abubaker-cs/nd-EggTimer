/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.eggtimernotifications.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.eggtimernotifications.R
import com.example.android.eggtimernotifications.databinding.FragmentEggTimerBinding

class EggTimerFragment : Fragment() {

    private val TOPIC = "breakfast"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentEggTimerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_egg_timer, container, false
        )

        val viewModel = ViewModelProvider(this).get(EggTimerViewModel::class.java)

        binding.eggTimerViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        // Channel: Egg
        createChannel(

            // Chanel Name: egg_channel
            getString(R.string.egg_notification_channel_id),

            // Notification Channel Name: Egg
            getString(R.string.egg_notification_channel_name)
        )

        // Channel: FCM
        createChannel(

            // Chanel Name: fcm_default_channel
            getString(R.string.breakfast_notification_channel_id),

            // Notification Channel Name: Breakfast
            getString(R.string.breakfast_notification_channel_name)
        )

        return binding.root
    }

    private fun createChannel(channelId: String, channelName: String) {

        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                channelId,
                channelName,

                /**
                 * 1. High = Makes a Sound and appears in the heads-up notification
                 * 2. Default = Makes a Sound
                 * 3. Low = No Sound
                 * 4. Min = No sound and does not appear in the status bar
                 *
                 * Reference: https://m3.material.io/design/platform-guidance/android-notifications.html#settings
                 */
                NotificationManager.IMPORTANCE_HIGH
            )

                // Disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            // Enables Lights
            notificationChannel.enableLights(true)

            // Red Light
            notificationChannel.lightColor = Color.RED

            // Enable: Vibration
            notificationChannel.enableVibration(true)

            // Description
            notificationChannel.description = "Time for breakfast"

            // We are calling the system service
            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(notificationChannel)

        }

        // TODO: Step 1.6 END create a channel

    }

    companion object {
        fun newInstance() = EggTimerFragment()
    }
}

