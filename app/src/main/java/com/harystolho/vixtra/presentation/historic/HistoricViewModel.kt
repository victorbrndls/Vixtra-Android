package com.harystolho.vixtra.presentation.historic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harystolho.vixtra.core.entity.MedicineConsumption
import com.harystolho.vixtra.core.service.MedicineHistoricService
import com.harystolho.vixtra.util.DateUtil
import kotlinx.coroutines.launch
import java.util.*

class HistoricViewModel(
    private val medicineHistoricService: MedicineHistoricService
) : ViewModel() {

    val dailyConsumption = MutableLiveData<List<ConsumptionModel>>()
    val weeklyConsumption = MutableLiveData<List<ConsumptionModel>>()

    fun load() {
        viewModelScope.launch {
            dailyConsumption.value = daily(
                medicineHistoricService.getConsumption(DateUtil.todayMidnight())
            )

            weeklyConsumption.value = weekly(
                medicineHistoricService.getConsumption(DateUtil.todayMidnight().apply {
                    add(Calendar.DATE, -6)
                })
            )
        }
    }

    private fun daily(consumptions: List<MedicineConsumption>): List<ConsumptionModel> {
        val group = consumptions.groupBy {
            (it.consumption.clone() as Calendar).apply {
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        val daily = group.map {
            ConsumptionModel(it.value.size, it.key)
        }

        val template = (0..23).map {
            ConsumptionModel(0, DateUtil.todayMidnight().apply {
                add(Calendar.HOUR_OF_DAY, it)
            })
        }

        return template.map { templateEntry ->
            daily.firstOrNull { it.date == templateEntry.date } ?: templateEntry
        }
    }

    private fun weekly(consumptions: List<MedicineConsumption>): List<ConsumptionModel> {
        val group = consumptions.groupBy {
            (it.consumption.clone() as Calendar).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        val weekly = group.map {
            ConsumptionModel(it.value.size, it.key)
        }

        val template = (6 downTo 0).map {
            ConsumptionModel(0, DateUtil.todayMidnight().apply {
                add(Calendar.DATE, -it)
            })
        }

        return template.map { templateEntry ->
            weekly.firstOrNull { it.date == templateEntry.date } ?: templateEntry
        }
    }

}

data class ConsumptionModel(
    val consumption: Int,
    val date: Calendar
)