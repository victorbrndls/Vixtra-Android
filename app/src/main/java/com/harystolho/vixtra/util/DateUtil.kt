package com.harystolho.vixtra.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val DDMMYYYYHHMMFormatter =
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US)

    fun toDDMMYYYYHHMM(calendar: Calendar): String = DDMMYYYYHHMMFormatter.format(calendar.time)

}