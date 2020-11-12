package com.harystolho.vixtra.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.harystolho.vixtra.R
import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.util.DateUtil
import kotlinx.android.synthetic.main.layout_medicine_item.view.*
import java.util.*

class MedicineAdapter(
    medicines: List<Medicine>,
    private val onClick: (Medicine) -> Unit,
    private val onEdit: (Medicine) -> Unit,
    private val onDelete: (Medicine) -> Unit
) : RecyclerView.Adapter<MedicineViewHolder>() {

    private val _medicines = mutableListOf(*medicines.toTypedArray())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_medicine_item, parent, false)
        return MedicineViewHolder(view, onClick, onEdit, onDelete)
    }

    override fun getItemCount() = _medicines.size

    fun setItems(medicines: List<Medicine>) {
        _medicines.clear()
        _medicines.addAll(medicines)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(_medicines[position])
    }

}

class MedicineViewHolder(
    view: View,
    private val onClick: (Medicine) -> Unit,
    private val onEdit: (Medicine) -> Unit,
    private val onDelete: (Medicine) -> Unit
) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(medicine: Medicine) {
        itemView.setOnClickListener { onClick(medicine) }
        itemView.medi_edit.setOnClickListener { onEdit(medicine) }
        itemView.medi_delete.setOnClickListener { onDelete(medicine) }

        itemView.medi_name.text = medicine.name
        itemView.medi_description.text = medicine.description
        itemView.medi_interval.text = medicine.hourInterval.toString() + " hrs"
        itemView.medi_consumption_time.text = DateUtil.toDDMMYYYYHHMM(medicine.consumptionTime)

        if (medicine.consumptionTime.time.time < Date().time) {
            itemView.setBackgroundColor(
                ResourcesCompat.getColor(itemView.context.resources, R.color.red, null)
            )
        } else {
            itemView.setBackgroundColor(
                ResourcesCompat.getColor(itemView.context.resources, R.color.white, null)
            )
        }
    }

}