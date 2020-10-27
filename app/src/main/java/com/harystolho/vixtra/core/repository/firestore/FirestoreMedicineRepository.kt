package com.harystolho.vixtra.core.repository.firestore

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.util.toCalendar
import java.text.SimpleDateFormat
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "[VX]FsMedicineRepo"
private const val COLLECTION = "remedios"

@SuppressLint("SimpleDateFormat")
private val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

class FirestoreMedicineRepository(private val firestore: FirebaseFirestore) {

    suspend fun get(id: String): Medicine? {
        return suspendCoroutine { cont ->
            firestore
                .collection(COLLECTION)
                .document(id)
                .get()
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        cont.resume(null)
                        return@addOnCompleteListener
                    }

                    runCatching {
                        cont.resume(map(task.result!!))
                    }.onFailure {
                        cont.resume(null)
                    }
                }
        }
    }

    suspend fun save(medicine: Medicine) {
        return suspendCoroutine { cont ->
            firestore
                .collection(COLLECTION)
                .document(medicine.id)
                .set(unMap(medicine))
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        cont.resume(Unit)
                        Log.e(TAG, "Error saving medicine", task.exception)
                        return@addOnCompleteListener
                    }

                    cont.resume(Unit)
                }
        }
    }

    suspend fun delete(id: String) {
        return suspendCoroutine { cont ->
            firestore
                .collection(COLLECTION)
                .document(id)
                .delete()
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        cont.resume(Unit)
                        return@addOnCompleteListener
                    }

                    cont.resume(Unit)
                }
        }
    }

    suspend fun getMedicines(): List<Medicine> {
        return suspendCoroutine { cont ->
            firestore
                .collection(COLLECTION)
                .get()
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        cont.resume(emptyList())
                        Log.e(TAG, "Error fetching medicines", task.exception)
                        return@addOnCompleteListener
                    }

                    val medicines = task.result!!.documents.mapNotNull {
                        runCatching {
                            map(it)
                        }.getOrNull()
                    }

                    cont.resume(medicines)
                }
        }
    }

    private fun map(doc: DocumentSnapshot): Medicine? {
        val data = doc.data ?: return null

        return Medicine(
            data["id"] as String,
            data["nome"] as String,
            data["descricao"] as String?,
            (data["intervalo_hora"] as Long).toInt(),
            (data["repeticao"] as Long).toInt(),
            DATE_FORMATTER.parse(data["horario_consumo"] as String)!!.toCalendar()
        )
    }

    private fun unMap(medicine: Medicine): Map<String, Any?> {
        return mapOf(
            "id" to medicine.id,
            "nome" to medicine.name,
            "descricao" to medicine.description,
            "intervalo_hora" to medicine.hourInterval,
            "repeticao" to medicine.repetition,
            "horario_consumo" to DATE_FORMATTER.format(medicine.consumptionTime.time)
        )
    }

}