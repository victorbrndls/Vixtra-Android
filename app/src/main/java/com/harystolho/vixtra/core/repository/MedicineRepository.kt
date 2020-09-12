package com.harystolho.vixtra.core.repository

import com.harystolho.vixtra.core.entity.Medicine

class MedicineRepository {

    private val medicines = mutableListOf<Medicine>()

    fun get(id: Long): Medicine? {
        return medicines.firstOrNull { it.id == id }
    }

    suspend fun save(medicine: Medicine) {
        delete(medicine.id)
        medicines.add(medicine)
    }

    suspend fun delete(id: Long) {
        val toKeep = medicines.filter { it.id != id }
        medicines.clear()
        medicines.addAll(toKeep)
    }

    suspend fun getMedicines(): List<Medicine> {
        return listOf(*medicines.toTypedArray())
    }

}