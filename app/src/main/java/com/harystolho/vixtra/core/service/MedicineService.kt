package com.harystolho.vixtra.core.service

import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.core.repository.firestore.FirestoreMedicineRepository
import java.util.*

class MedicineService(
    private val medicineRepository: FirestoreMedicineRepository,
    private val medicineHistoricService: MedicineHistoricService
) {

    suspend fun save(medicine: Medicine) {
        medicineRepository.save(medicine)
    }

    suspend fun delete(id: String) {
        medicineRepository.delete(id)
    }

    suspend fun getMedicine(id: String): Medicine? {
        return medicineRepository.get(id)
    }

    suspend fun getMedicines(): List<Medicine> {
        return medicineRepository.getMedicines()
    }

    suspend fun consume(id: String) {
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