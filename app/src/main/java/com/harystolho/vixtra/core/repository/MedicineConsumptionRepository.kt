package com.harystolho.vixtra.core.repository

import com.harystolho.vixtra.core.entity.MedicineConsumption
import java.util.*

class MedicineConsumptionRepository {

    private val consumptions = mutableListOf<MedicineConsumption>(
        MedicineConsumption(1, Calendar.getInstance()),
        MedicineConsumption(1, Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, 3)
        }),
        MedicineConsumption(1, Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, 6)
        }),
        MedicineConsumption(1, Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, 10)
        }),
        MedicineConsumption(1, Calendar.getInstance().apply {
            add(Calendar.DATE, -2)
        }),
        MedicineConsumption(1, Calendar.getInstance().apply {
            add(Calendar.DATE, -3)
        })
    )

    suspend fun get(): List<MedicineConsumption> {
        return consumptions
    }

    suspend fun save(consumption: MedicineConsumption) {
        consumptions.add(consumption)
    }

}