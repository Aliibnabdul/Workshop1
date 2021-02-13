package com.example.homeworkAA.ui.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.WorkManager
import com.example.homeworkAA.MoviesConstants.CANCEL_WORK_ACTION
import com.example.homeworkAA.MoviesConstants.WORK_TAG

class NotificationsReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val action = intent.action
        if (action != null) {
            when (action) {
                CANCEL_WORK_ACTION -> {
                    Log.d("RECEIVER_TAG", "onReceive(): CANCEL_WORK_ACTION")

                    val workManager = WorkManager.getInstance(context)
                    workManager.cancelAllWorkByTag(WORK_TAG)

                    val notificationManager = NotificationsManager(context)
                    notificationManager.cancelNotifications()
                }
            }
        }
    }
}