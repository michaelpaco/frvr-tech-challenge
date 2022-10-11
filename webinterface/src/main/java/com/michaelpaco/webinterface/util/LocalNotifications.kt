package com.michaelpaco.webinterface.util

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.format.DateFormat.getLongDateFormat
import android.text.format.DateFormat.getTimeFormat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.michaelpaco.webinterface.MyApplication
import com.michaelpaco.webinterface.R
import com.michaelpaco.webinterface.model.data.NotificationEntity
import com.michaelpaco.webinterface.util.Date.convertToTimeInMillis
import java.util.Date

class LocalNotifications {
    private val notificationManager = MyApplication.application().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val LOCAL_NOTIFICATIONS_CHANNEL_ID = "local_notifications_channel"
    }

    init {
        createNotificationsChannel()
    }

    fun registerNotification(notification: NotificationEntity) {
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun schedule(notification: NotificationEntity, context: Context) {
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
        val time = convertToTimeInMillis(notification.displayTime)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

        showAlert(time, notification.title, notification.message, context)
    }

    private fun showAlert(time: Long, title: String, message: String, context: Context) {
        val date = Date(time)
        val dateFormat = getLongDateFormat(MyApplication.application())
        val timeFormat = getTimeFormat(MyApplication.application())

        AlertDialog.Builder(context)
            .setTitle("notification scheduled")
            .setMessage(
                "Title: " + title +
                    "\nMessage: " + message +
                    "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }

    private fun createNotificationsChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                LOCAL_NOTIFICATIONS_CHANNEL_ID,
                "FRVR",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Used to show user defined notifications"

            val notificationManager = MyApplication.application().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
