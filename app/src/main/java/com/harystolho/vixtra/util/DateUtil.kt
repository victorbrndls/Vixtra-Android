package com.harystolho.vixtra.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val DDMMYYYYHHMMFormatter =
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US)

    fun toDDMMYYYYHHMM(calendar: Calendar): String = DDMMYYYYHHMMFormatter.format(calendar.time)

    fun todayMidnight() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

}