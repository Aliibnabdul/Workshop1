package com.example.homeworkAA.ui.notifications

import android.app.Notification
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
import com.example.homeworkAA.R

class NotificationsManager(private val context: Context) {
    private val mNotificationManager = NotificationManagerCompat.from(context)
    private val mNotificationsReceiver: NotificationsReceiver by lazy { NotificationsReceiver(this) }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_name)
            val description = context.getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(MoviesConstants.CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun showNotification(message: String) {
        mNotificationManager.notify(NOTIFICATION_ID, createNotification(message))
        registerActionsReceiver()
    }

    private fun createNotification(message: String): Notification {
        return NotificationCompat.Builder(context, MoviesConstants.CHANNEL_ID)
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_work)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setContentIntent(createOpenAppIntent())
            .setOngoing(true)
            .addAction(createNotificationAction(CANCEL_WORK_ACTION))
            .build()
    }

    fun cancelNotifications() {
        mNotificationManager.cancelAll()
        unregisterActionsReceiver()
    }

    private fun createOpenAppIntent(): PendingIntent{
        val openAppIntent = Intent(context, MainActivity::class.java)
        openAppIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        return PendingIntent.getActivity(
            context,
            NOTIFICATION_INTENT_REQUEST_CODE,
            openAppIntent,
            0
        )
    }

    private fun createNotificationAction(action: String): NotificationCompat.Action {
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