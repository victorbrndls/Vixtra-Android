package com.harystolho.vixtra.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val DDMMYYYYHHMMSSFormatter =
        SimpleDateFormat("dd/MM/yyyy HH:mm:kk", Locale.US)

    fun toDDMMYYYYHHMMSS(calendar: Calendar): String = DDMMYYYYHHMMSSFormatter.format(calendar.time)

}