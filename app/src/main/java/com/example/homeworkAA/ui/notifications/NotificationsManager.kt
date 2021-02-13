package com.example.homeworkAA.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.homeworkAA.MainActivity
import com.example.homeworkAA.MoviesConstants
import com.example.homeworkAA.MoviesConstants.CANCEL_WORK_ACTION
import com.example.homeworkAA.MoviesConstants.NOTIFICATION_ID
import com.example.homeworkAA.MoviesConstants.NOTIFICATION_INTENT_REQUEST_CODE
import com.example.homeworkAA.MoviesConstants.NOTIFICATION_TITLE
import com.example.homeworkAA.R

class NotificationsManager(private val context: Context) {
    private val mNotificationManager = NotificationManagerCompat.from(context)
    private val mNotificationsReceiver: NotificationsReceiver by lazy { NotificationsReceiver() }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = MoviesConstants.VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = MoviesConstants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(MoviesConstants.CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun showNotification(message: String) {

        val openAppIntent = Intent(context, MainActivity::class.java)
        openAppIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val contentIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_INTENT_REQUEST_CODE,
            openAppIntent,
            0
        )

        val builder = NotificationCompat.Builder(context, MoviesConstants.CHANNEL_ID)
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setContentIntent(contentIntent)
            .setOngoing(true)
            .addAction(notificationAction(CANCEL_WORK_ACTION))

        mNotificationManager.notify(NOTIFICATION_ID, builder.build())

        registerActionsReceiver()
    }

    fun cancelNotifications() {
        mNotificationManager.cancelAll()
        unregisterActionsReceiver()
    }

    private fun notificationAction(action: String): NotificationCompat.Action {
        val icon = R.drawable.ic_clear

        val actionIntent = Intent().also {
            it.action = action
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_INTENT_REQUEST_CODE,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Action.Builder(icon, action, pendingIntent).build()
    }

    private fun registerActionsReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(CANCEL_WORK_ACTION)
        }
        context.registerReceiver(mNotificationsReceiver, intentFilter)
    }

    private fun unregisterActionsReceiver() {
        try {
            context.unregisterReceiver(mNotificationsReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}