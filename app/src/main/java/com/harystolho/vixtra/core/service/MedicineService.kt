package com.harystolho.vixtra.core.service

import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.core.repository.MedicineRepository
import java.util.*

class MedicineService(
    private val medicineRepository: MedicineRepository,
    private val medicineHistoricService: MedicineHistoricService
) {

    suspend fun save(medicine: Medicine) {
        medicineRepository.save(medicine)
    }

    suspend fun delete(id: Long) {
        medicineRepository.delete(id)
    }

    suspend fun getMedicines(): List<Medicine> {
        return medicineRepository.getMedicines()
    }

    suspend fun consume(id: Long) {
        val medicine = medicineRepository.get(id) ?: return

        medicineHistoricService.recordConsumption(medicine)

        if (medicine.repetition == 1) {
            delete(id)
            return
        }

        val newMed = medicine.copy(
            repetition = medicine.repetition - 1,
            consumptionTime = (medicine.consumptionTime.clone() as Calendar).apply {
                add(Calendar.HOUR_OF_DAY, medicine.hourInterval)
            }
        )

        save(newMed)
    }

}