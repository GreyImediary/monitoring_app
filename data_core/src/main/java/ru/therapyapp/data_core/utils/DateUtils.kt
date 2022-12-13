package ru.therapyapp.data_core.utils

import java.text.SimpleDateFormat
import java.util.*


fun Date.getStringDateRepresentation(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.time)
}

fun Date.getStringDateWithHour(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return SimpleDateFormat("dd.MM.yyyy HH", Locale.getDefault()).format(calendar.time)
}

fun Date.getStringDateWithTime(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(calendar.time)
}

fun getStringDateFromLong(date: Long): String {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(date))
}

fun getStringDateWithTimeFromLong(date: Long): String {
    return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(date))
}

fun getDateFromString(date: String): Date? {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(date)
}
