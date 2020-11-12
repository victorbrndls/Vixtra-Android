package com.harystolho.vixtra.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
import com.harystolho.vixtra.R
import com.harystolho.vixtra.core.entity.Medicine
import com.harystolho.vixtra.presentation.add_medicine.AddMedicineActivity
import com.harystolho.vixtra.presentation.home.adapter.MedicineAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var adapter: MedicineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupAdapter()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMedicines()
    }

    private fun initViews() {
        homeAddMedicine.setOnClickListener {
            startActivity(Intent(requireContext(), AddMedicineActivity::class.java))
        }
    }

    private fun setupAdapter() {
        adapter = MedicineAdapter(emptyList(), onClick, onEdit, onDelete)
        homeMedicines.adapter = adapter
    }

    private val onClick = { medicine: Medicine -> viewModel.confirmConsume(medicine) }

    private val onEdit = { medicine: Medicine ->
        startActivity(Intent(requireContext(), AddMedicineActivity::class.java).apply {
            putExtra("id", medicine.id)
        })
    }

    private val onDelete = { medicine: Medicine -> viewModel.delete(medicine) }

    private fun observeViewModel() {
        viewModel.action.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HomeAction.ShowMedicines -> showMedicines(it.medicines)
                is HomeAction.ConfirmConsumeMedicine -> confirmConsumeMedicine(it.medicine)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            TransitionManager.beginDelayedTransition(homeProgressContainer)
            homeProgress.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun confirmConsumeMedicine(medicine: Medicine) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.generic_confirm)
            .setMessage(R.string.takemed_confirm)
            .setPositiveButton(R.string.yes) { _, _ -> viewModel.consume(medicine) }
            .setNegativeButton(R.string.no) { _, _ -> }
            .show()
    }

    private fun showMedicines(medicines: List<Medicine>) {
        adapter.setItems(medicines)
    }

}