package com.harystolho.vixtra.core.service

import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.core.repository.MedicineRepository

class MedicineService(
    private val medicineRepository: MedicineRepository
) {

    suspend fun save(medicine: Medicine) {
        medicineRepository.save(medicine)
    }

    suspend fun getMedicines() : List<Medicine> {
        return medicineRepository.getMedicines()
    }

}