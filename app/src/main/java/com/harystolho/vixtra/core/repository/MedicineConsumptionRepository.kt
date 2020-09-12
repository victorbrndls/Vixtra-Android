package com.harystolho.vixtra.core.repository

import com.harystolho.vixtra.core.entity.MedicineConsumption

class MedicineConsumptionRepository {

    private val consumptions = mutableListOf<MedicineConsumption>()

    suspend fun get(): List<MedicineConsumption> {
        return consumptions
    }

    suspend fun save(consumption: MedicineConsumption) {
        consumptions.add(consumption)
    }

}