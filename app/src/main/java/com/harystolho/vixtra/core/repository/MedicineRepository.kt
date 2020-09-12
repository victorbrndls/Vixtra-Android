package com.harystolho.vixtra.core.repository

import com.harystolho.vixtra.core.entity.Medicine

class MedicineRepository {

    private val medicines = mutableListOf<Medicine>()

    suspend fun save(medicine: Medicine) {
        val toKeep = medicines.filter { it.id != medicine.id }
        medicines.clear()

        medicines.addAll(toKeep)
        medicines.add(medicine)
    }

}