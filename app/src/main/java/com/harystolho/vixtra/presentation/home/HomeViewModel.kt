package com.harystolho.vixtra.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.core.service.MedicineService
import kotlinx.coroutines.launch

class HomeViewModel(
    private val medicineService: MedicineService
) : ViewModel() {

    val action = MutableLiveData<HomeAction>()

    val isLoading = MutableLiveData(false)

    fun loadMedicines() {
        viewModelScope.launch {
            isLoading.value = true

            val medicines = medicineService.getMedicines().sortedBy { it.consumptionTime }
            action.value = HomeAction.ShowMedicines(medicines)

            isLoading.value = false
        }
    }

    fun confirmConsume(medicine: Medicine) {
        action.value = HomeAction.ConfirmConsumeMedicine(medicine)
    }

    fun consume(medicine: Medicine) {
        viewModelScope.launch {
            loading { medicineService.consume(medicine.id) }
            loadMedicines()
        }
    }

    fun delete(medicine: Medicine) {
        viewModelScope.launch {
            loading { medicineService.delete(medicine.id) }
            loadMedicines()
        }
    }

    private suspend fun loading(block: suspend () -> Unit) {
        isLoading.value = true
        block()
        isLoading.value = false
    }

}

sealed class HomeAction {
    class ShowMedicines(val medicines: List<Medicine>) : HomeAction()
    class ConfirmConsumeMedicine(val medicine: Medicine) : HomeAction()
}