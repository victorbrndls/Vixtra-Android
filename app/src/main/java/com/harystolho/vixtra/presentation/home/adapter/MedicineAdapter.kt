package com.harystolho.vixtra.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harystolho.vixtra.R
import com.harystolho.vixtra.core.entity.Medicine

class MedicineAdapter(
    private val medicines: List<Medicine>
) : RecyclerView.Adapter<MedicineViewHolder>() {

    private val _medicines = mutableListOf(*medicines.toTypedArray())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_medicine_item, parent, false)
        return MedicineViewHolder(view)
    }

    override fun getItemCount() = _medicines.size

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(_medicines[position])
    }

}

class MedicineViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(medicine: Medicine) {

    }

}