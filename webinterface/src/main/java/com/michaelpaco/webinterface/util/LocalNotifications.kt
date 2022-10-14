package com.michaelpaco.webinterface.util

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat.getLongDateFormat
import android.text.format.DateFormat.getTimeFormat
import androidx.core.app.NotificationCompat
import com.michaelpaco.webinterface.MyApplication
import com.michaelpaco.webinterface.R
import com.michaelpaco.webinterface.model.data.NotificationEntity
import com.michaelpaco.webinterface.util.Const.ALERT_POSITIVE_BUTTON
import com.michaelpaco.webinterface.util.Const.CHANNEL_DESCRIPTION
import com.michaelpaco.webinterface.util.Const.CHANNEL_NAME
import com.michaelpaco.webinterface.util.Const.LOCAL_NOTIFICATIONS_CHANNEL_ID
import com.michaelpaco.webinterface.util.Date.convertToTimeInMillis
import java.util.Date

class LocalNotifications {
    private val notificationManager = MyApplication.application().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationsChannel()
    }

    fun addNotification(notification: NotificationEntity) {
        val userNotification = NotificationCompat.Builder(MyApplication.application(), LOCAL_NOTIFICATIONS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_contest)
            .setContentTitle(notification.title)
            .setContentText(notification.message)
            .build()

        notificationManager.notify(
            notification.id,
            userNotification
        )
    }

    fun setNotification(notification: NotificationEntity, context: Context, shouldDelete: Boolean = false) {
        val intent = Intent(MyApplication.application(), NotificationScheduler::class.java)
        intent.putExtra("title", notification.title)
        intent.putExtra("message", notification.message)
        intent.putExtra("displayTime", notification.displayTime + ":00")
        intent.putExtra("id", notification.id)

        val pendingIntent = PendingIntent.getBroadcast(
            MyApplication.application(),
            notification.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = MyApplication.application().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeInMillis = convertToTimeInMillis(notification.displayTime)

        if (shouldDelete) {
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)

            showAlert(timeInMillis, notification.title, notification.message, context, shouldDelete)
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
            showAlert(timeInMillis, notification.title, notification.message, context, shouldDelete)
        }
    }

    private fun showAlert(time: Long, title: String, message: String, context: Context, isDeleted: Boolean = false) {
        val date = Date(time)
        val dateFormat = getLongDateFormat(MyApplication.application())
        val timeFormat = getTimeFormat(MyApplication.application())

        val alertTitle = if (isDeleted) {
            context.getString(R.string.notification_deleted)
        } else {
            context.getString(R.string.notification_added)
        }

        AlertDialog.Builder(context)
            .setTitle(alertTitle)
            .setMessage(
                "Title: " + title +
                    "\nMessage: " + message +
                    "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton(ALERT_POSITIVE_BUTTON) { _, _ -> }
            .show()
    }

    private fun createNotificationsChannel() {
        val channel = NotificationChannel(
            LOCAL_NOTIFICATIONS_CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = CHANNEL_DESCRIPTION

        val notificationManager = MyApplication.application().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
