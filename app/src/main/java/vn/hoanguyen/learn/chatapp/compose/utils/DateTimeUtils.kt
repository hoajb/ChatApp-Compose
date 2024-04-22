package vn.hoanguyen.learn.chatapp.compose.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DateTimeUtils @Inject constructor() {
    fun formatTimestamp(timestamp: Long): String {
        val currentDate = Calendar.getInstance()
        val timestampDate = Calendar.getInstance().apply { timeInMillis = timestamp }

        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd MMM yyyy h:mm a", Locale.getDefault())

        return if (currentDate.get(Calendar.YEAR) == timestampDate.get(Calendar.YEAR) &&
            currentDate.get(Calendar.DAY_OF_YEAR) == timestampDate.get(Calendar.DAY_OF_YEAR)
        ) {
            // If the timestamp is on the current date
            timeFormat.format(Date(timestamp))
        } else {
            // If the timestamp is on a different date
            dateFormat.format(Date(timestamp))
        }
    }
}