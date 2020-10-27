package com.harystolho.vixtra.util

import java.util.*

fun Date.toCalendar() = Calendar.getInstance().apply {
    time = this@toCalendar
}