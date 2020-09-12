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

            action.value = HomeAction.ShowMedicines(medicineService.getMedicines())

            isLoading.value = false
        }
    }

}

sealed class HomeAction {
    class ShowMedicines(val medicines: List<Medicine>) : HomeAction()
}