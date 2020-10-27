package com.harystolho.vixtra.core.repository.firestore

import android.annotation.SuppressLint
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.harystolho.vixtra.core.entity.MedicineConsumption
import com.harystolho.vixtra.util.toCalendar
import java.text.SimpleDateFormat
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val COLLECTION = "consumo_remedios"

@SuppressLint("SimpleDateFormat")
private val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

class FirestoreMedicineConsumptionRepository(private val firestore: FirebaseFirestore) {

    suspend fun get(): List<MedicineConsumption> {
        return suspendCoroutine { cont ->
            firestore
                .collection(COLLECTION)
                .get()
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        cont.resume(emptyList())
                        return@addOnCompleteListener
                    }

                    val consumptions = task.result!!.documents.mapNotNull {
                        runCatching {
                            map(it)
                        }.getOrNull()
                    }

                    cont.resume(consumptions)
                }
        }

    }

    suspend fun save(consumption: MedicineConsumption) {
        return suspendCoroutine { cont ->
            firestore
                .collection(COLLECTION)
                .document()
                .set(unMap(consumption))
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        cont.resume(Unit)
                        return@addOnCompleteListener
                    }

                    cont.resume(Unit)
                }
        }

    }

    private fun map(doc: DocumentSnapshot): MedicineConsumption? {
        val data = doc.data ?: return null

        return MedicineConsumption(
            data["id_remedio"] as String,
            DATE_FORMATTER.parse(data["horario_consumo"] as String)!!.toCalendar()
        )
    }

    private fun unMap(medicine: MedicineConsumption): Map<String, Any?> {
        return mapOf(
            "id_remedio" to medicine.medicineId,
            "horario_consumo" to DATE_FORMATTER.format(medicine.consumption.time)
        )
    }

}