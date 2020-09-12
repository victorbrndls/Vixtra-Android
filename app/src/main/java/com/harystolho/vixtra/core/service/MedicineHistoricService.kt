package com.harystolho.vixtra.core.service

import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.core.entity.MedicineConsumption
import com.harystolho.vixtra.core.repository.MedicineConsumptionRepository
import java.util.*

class MedicineHistoricService(
    private val medicineConsumptionRepository: MedicineConsumptionRepository
) {

    suspend fun getConsumption(): List<MedicineConsumption> {
        return medicineConsumptionRepository.get()
    }

    suspend fun recordConsumption(medicine: Medicine) {
        return medicineConsumptionRepository.save(
            MedicineConsumption(
                medicine.id,
                Calendar.getInstance()
            )
        )
    }

}