package com.harystolho.vixtra.core.entity

import java.util.*

data class Medicine(
    val id: Long,
    val name: String,
    val description: String?,
    var hourInterval: Int,
    var repetition: Int,
    var consumptionTime: Calendar
)