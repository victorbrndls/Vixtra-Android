package com.harystolho.vixtra.presentation.add_medicine

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.harystolho.vixtra.R
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_add_medicine.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AddMedicineActivity : AppCompatActivity() {

    private val viewModel by viewModel<AddMedicineViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_medicine)

        setupToolbar(getString(R.string.addmed_title))
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        addmed_medicine.doAfterTextChanged { viewModel.updateModel(medicine = it?.toString()) }
        addmed_description.doAfterTextChanged { viewModel.updateModel(description = it?.toString()) }
        addmed_interval.doAfterTextChanged {
            viewModel.updateModel(hourInterval = it?.toString()?.toIntOrNull())
        }
        addmed_start_time_layout.setOnClickListener { showStartTimePicker() }
        addmed_repetition.doAfterTextChanged {
            viewModel.updateModel(repetition = it?.toString()?.toIntOrNull())
        }

        addmed_save.setOnClickListener {
            cleanErrors()
            viewModel.save()
        }
    }

    private fun showStartTimePicker() {
        TimePickerDialog.newInstance({ _, h, m, s ->
            val time = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, h)
                set(Calendar.MINUTE, m)
                set(Calendar.SECOND, s)
            }

            viewModel.updateModel(startTime = time)
        }, true).apply {
            version = TimePickerDialog.Version.VERSION_2
        }.show(supportFragmentManager, null)
    }

    private fun setupToolbar(title: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun observeViewModel() {
        viewModel.action.observe(this, Observer {
            when (it) {
                AddMedicineAction.Finish -> {
                    Toast.makeText(this, R.string.generic_save_success, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })

        viewModel.isLoading.observe(this, Observer {
            addmed_progress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer {
            when (it) {
                AddMedicineError.MEDICINE_FIELD -> TODO()
                AddMedicineError.HOUR_FIELD -> TODO()
            }
        })
    }

    private fun cleanErrors() {
        addmed_medicine_layout.error = null
        addmed_description_layout.error = null
        addmed_interval_layout.error = null
        addmed_repetition_layout.error = null
    }

}