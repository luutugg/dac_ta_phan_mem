package com.example.dactaphanmem.presentation.util

import android.annotation.SuppressLint
import android.util.Log
import com.example.dactaphanmem.common.LONG_DEFAULT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object TimeUtils {

    const val STRING_HOUR_MINUTES = "HH:mm"
    const val STRING_ISO_T_Z = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    const val STRING_ISO_T = "yyyy-MM-dd'T'HH:mm:ss"
    const val STRING_ISO_DATE_AND_TIME = "dd/MM/yyyy HH:mm:ss"
    const val STRING_ISO_YYYY_MM_DD = "yyyy-MM-dd"
    const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val ISO_FORMAT_V1 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    const val DATE_FORMAT = "dd/MM/yyyy"
    const val DATE_FORMAT_V4 = "yyyy/MM/dd"
    const val DATE_FORMAT_V2 = "dd-MM-yyyy"
    const val DATE_FORMAT_V3 = "yyyy-MM-dd"
    private const val DASHBOARD_TIME_ZONE = "GMT+7"

    private const val TIME_DAC_TA = "EEE MMM dd HH:mm:ss zzz yyyy"

    fun convertToHHmm(time: Long): String {
        val date = Date(checkIsMillisecond(time))
        val sdf = SimpleDateFormat(STRING_HOUR_MINUTES, Locale.getDefault())
        return sdf.format(date)
    }

    fun convertDateToMillis(time: String): Long {
        val sdf = SimpleDateFormat(ISO_FORMAT, Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(time)
        return date?.time ?: 0L
    }

    fun convertTimezoneToMillisByString(format: String, time: String): String {
        val formatDateTime = SimpleDateFormat(STRING_ISO_T, Locale.getDefault())
        formatDateTime.timeZone = TimeZone.getTimeZone("GMT")

        var formatReturn: SimpleDateFormat? = null
        var newDate: Date?
        formatReturn = SimpleDateFormat(format, Locale.getDefault())
        try {
            newDate = formatDateTime.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
            val formatDateTime2 = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            formatDateTime2.timeZone = TimeZone.getTimeZone("GMT")
            formatReturn = SimpleDateFormat(format, Locale.getDefault())
            try {
                newDate = formatDateTime2.parse(time)
            } catch (e: Exception) {
                e.printStackTrace()
                return time
            }
            return newDate?.let { formatReturn.format(it) } ?: time
        } catch (e: Exception) {
            e.printStackTrace()
            return time
        }
        return newDate?.let { formatReturn.format(it) } ?: time
    }

    fun convertTimezoneToMillis(format: String, time: String): Long {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        val date = sdf.parse(time)
        return date?.time ?: 0L
    }

    fun convertStringToStringDate(inputStr: String, format: String): String {
        var date = Date()
        var outputStr = ""
        try {
            val isoDateFormat = SimpleDateFormat(ISO_FORMAT_V1, Locale.US)
            isoDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            date = isoDateFormat.parse(inputStr) as Date
            if (getCalendarFromIso8601SSSZString(inputStr)!!.time == getToday()) {
                outputStr = formatDate(date, DATE_FORMAT) ?: ""
            } else {
                outputStr = formatDate(date, format) ?: ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return inputStr
        }
        return outputStr

    }

    fun formatDate(date: Date, formatPattern: String): String? {
        val format = SimpleDateFormat(formatPattern, Locale.getDefault())
        return format.format(date)
    }

    private fun getCalendarFromIso8601SSSZString(isoDateString: String?): Calendar? {
        return if (isoDateString.isNullOrBlank()) null else {
            try {
                val dateFormat = SimpleDateFormat(ISO_FORMAT_V1, Locale.getDefault())
                val date = dateFormat.parse(isoDateString)
                Calendar.getInstance().apply {
                    if (date != null) {
                        time = date
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun getToday(): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone(DASHBOARD_TIME_ZONE))
    }

    fun convertLongToString(input: Long, output: String): String {
        val date = Date(input)
        val df2 = SimpleDateFormat(output, Locale.getDefault())
        return df2.format(date)
    }

    fun convertStringToLong(input: String): Long {
        val f = SimpleDateFormat(DATE_FORMAT)
        val d = f.parse(input)
        return d?.time ?: LONG_DEFAULT
    }

    fun convertStringToLongFormat(input: String, formatPattern: String): Long {
        val f = SimpleDateFormat(formatPattern)
        val d = f.parse(input)
        return d?.time ?: LONG_DEFAULT
    }

    private fun checkIsMillisecond(time: Long): Long {
        val secondsOfYear = 86400000
        val now = System.currentTimeMillis()
        val todayStart = now - (now % secondsOfYear)
        if (time >= todayStart) {
            return time
        }
        return time * 1000L
    }

    fun getTimeAfter(startTime: Long, settingTime: Int): Long {
        return when (settingTime) {
            //sau 15 phút
            1 -> startTime + 15 * 60 * 1000
            //sau 1h
            2 -> startTime + 1 * 60 * 60 * 1000
            // sau 4h
            3 -> startTime + 4 * 60 * 60 * 1000
            //sau 8h
            4 -> startTime + 8 * 60 * 60 * 1000
            //cho đến khi mở lại
            0 -> startTime
            else -> startTime
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTimeDactaToDDMMYY(date: Date): String {
        val outputPattern = "dd/MM/yyyy"
        val outputFormat = SimpleDateFormat(outputPattern)
        val date = Calendar.getInstance().time
        return outputFormat.format(date)
    }

    fun checkGreaterTime(time: String): Boolean {
        val dateString1 = time
        val dateString2 = convertTimeDactaToDDMMYY(Calendar.getInstance().time)
        val pattern = "dd/MM/yyyy"

        val sdf = SimpleDateFormat(pattern)

        try {
            val date1 = sdf.parse(dateString1)
            val date2 = sdf.parse(dateString2)
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.time = date1
            cal2.time = date2
            return cal1 < cal2
        } catch (e: ParseException) {
            throw e
        }
    }

    fun minusTime(time: String): String {
        val dateString1 = time
        val dateString2 = convertTimeDactaToDDMMYY(Calendar.getInstance().time)
        val pattern = "dd/MM/yyyy"

        val sdf = SimpleDateFormat(pattern)

        try {
            val date1 = sdf.parse(dateString1)
            val date2 = sdf.parse(dateString2)
            val diffInMilliseconds = date2.time - date1.time
            val diffInDays: Long = TimeUnit.MILLISECONDS.toDays(diffInMilliseconds)
            return "$diffInDays ngày"
        } catch (e: ParseException) {
            throw e
        }
    }
}
