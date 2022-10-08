package ru.therapyapp.data_core.utils

import java.text.SimpleDateFormat
import java.util.*


fun Date.getStringDateRepresentation(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.time)
}
