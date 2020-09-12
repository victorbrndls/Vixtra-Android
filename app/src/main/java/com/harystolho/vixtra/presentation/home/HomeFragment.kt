package com.harystolho.vixtra.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        adapter = MedicineAdapter(emptyList())
        homeMedicines.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.action.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HomeAction.ShowMedicines -> showMedicines(it.medicines)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun showMedicines(medicines: List<Medicine>) {
        adapter.setItems(medicines)
    }

}