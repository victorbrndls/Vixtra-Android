package com.harystolho.vixtra.presentation.add_medicine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.core.service.MedicineService
import kotlinx.coroutines.launch
import java.util.*

class AddMedicineViewModel(
    private val medicineService: MedicineService
) : ViewModel() {

    private val model = AddMedicineModel()
    private var loaded: Medicine? = null

    val action = MutableLiveData<AddMedicineAction>()

    val isLoading = MutableLiveData(false)
    val error = MutableLiveData<AddMedicineError>()

    fun load(id: String) {
        viewModelScope.launch {
            val med = medicineService.getMedicine(id)!!
            loaded = med

            model.medicine = med.name
            model.description = med.description
            model.hourInterval = med.hourInterval
            model.repetition = med.repetition
            model.startTime = med.consumptionTime

            action.value = AddMedicineAction.ShowModel(model)
        }
    }

    fun updateModel(
        medicine: String? = null,
        description: String? = null,
        hourInterval: Int? = null,
        repetition: Int? = null,
        startTime: Calendar? = null
    ) {
        medicine?.let { model.medicine = it }
        description?.let { model.description = it }
        hourInterval?.let { model.hourInterval = it }
        repetition?.let { model.repetition = it }
        startTime?.let { model.startTime = it }
    }

    fun save() {
        if (!isValid()) return
        isLoading.value = true

        viewModelScope.launch {
            val medicine = createMedicine()
            medicineService.save(medicine)

            isLoading.value = false
            action.value = AddMedicineAction.Finish
        }
    }

    private fun createMedicine(): Medicine {
        val consumptionTime = model.startTime ?: Calendar.getInstance()
        val interval = model.hourInterval ?: 8

        consumptionTime.add(Calendar.HOUR_OF_DAY, interval)

        return Medicine(
            loaded?.id ?: UUID.randomUUID().toString(),
            model.medicine!!,
            model.description,
            interval,
            model.repetition,
            consumptionTime
        )
    }

    private fun isValid(): Boolean {
        if (model.medicine.isNullOrEmpty()) {
            error.value = AddMedicineError.MEDICINE_FIELD
            return false
        }

        if (model.hourInterval == null || model.hourInterval!! < 0) {
            error.value = AddMedicineError.HOUR_FIELD
            return false
        }

        return true
    }

}

class AddMedicineModel(
    var medicine: String? = null,
    var description: String? = null,
    var hourInterval: Int? = null,
    var startTime: Calendar? = null,
    var repetition: Int = 1
)

sealed class AddMedicineAction {
    object Finish : AddMedicineAction()
    data class ShowModel(val model: AddMedicineModel) : AddMedicineAction()
}

enum class AddMedicineError {
    MEDICINE_FIELD, HOUR_FIELD
}