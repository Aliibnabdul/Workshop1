package com.example.homeworkAA.ui.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.WorkManager
import com.example.homeworkAA.MoviesConstants.CANCEL_WORK_ACTION
import com.example.homeworkAA.MoviesConstants.WORK_TAG

class NotificationsReceiver(private val notificationsManager: NotificationsManager) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        when (intent.action) {
            CANCEL_WORK_ACTION -> {
                Log.d("RECEIVER_TAG", "onReceive(): CANCEL_WORK_ACTION")

                val workManager = WorkManager.getInstance(context)
                workManager.cancelAllWorkByTag(WORK_TAG)

                notificationsManager.cancelNotifications()
            }
        }
    }
}