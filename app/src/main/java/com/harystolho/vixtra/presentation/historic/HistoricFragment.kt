package com.harystolho.vixtra.presentation.historic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.harystolho.vixtra.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoricFragment : Fragment() {

    private val viewModel by viewModel<HistoricViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historic, container, false)
    }
}