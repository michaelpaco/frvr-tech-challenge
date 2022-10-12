package com.michaelpaco.webinterface.util

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.util.Calendar

object Date {
    @SuppressLint("NewApi")
    fun convertToTimeInMillis(date: String): Long {
        val parsedDate = LocalDateTime.parse(date)
        val calendar = Calendar.getInstance()

        calendar.set(
            parsedDate.year,
            parsedDate.monthValue - 1,
            parsedDate.dayOfMonth,
            parsedDate.hour,
            parsedDate.minute
        )

        return calendar.timeInMillis
    }
}
