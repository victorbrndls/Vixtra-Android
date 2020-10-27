package com.harystolho.vixtra.di

import com.google.firebase.firestore.FirebaseFirestore
import com.harystolho.vixtra.core.repository.firestore.FirestoreMedicineConsumptionRepository
import com.harystolho.vixtra.core.repository.firestore.FirestoreMedicineRepository
import com.harystolho.vixtra.core.service.MedicineHistoricService
import com.harystolho.vixtra.core.service.MedicineService
import com.harystolho.vixtra.presentation.add_medicine.AddMedicineViewModel
import com.harystolho.vixtra.presentation.historic.HistoricViewModel
import com.harystolho.vixtra.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {

    single { FirestoreMedicineRepository(FirebaseFirestore.getInstance()) }
    single { FirestoreMedicineConsumptionRepository(FirebaseFirestore.getInstance()) }

    single { MedicineService(get(), get()) }
    single { MedicineHistoricService(get()) }

    viewModel { AddMedicineViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { HistoricViewModel(get()) }
}