package com.harystolho.vixtra.presentation.historic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.harystolho.vixtra.R
import kotlinx.android.synthetic.main.fragment_historic.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoricFragment : Fragment() {

    companion object {
        private val INT_VALUE_FORMATTER = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
    }

    private val viewModel by viewModel<HistoricViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCharts()
        observeViewModel()
        viewModel.load()
    }

    private fun setupCharts() {
        listOf(bar_chart_hourly, bar_chart_weekly).forEach { chart ->
            chart.xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawAxisLine(false)
                setDrawGridLines(false)
            }

            chart.axisRight.isEnabled = false
            chart.axisLeft.isEnabled = false

            chart.description = Description().apply {
                isEnabled = false
            }

            chart.legend.apply {
                isEnabled = false
            }

            chart.setDrawBorders(false)

            chart.setTouchEnabled(false)
            chart.setScaleEnabled(false)

            chart.isHighlightFullBarEnabled = false
            chart.isHighlightPerDragEnabled = false
            chart.isHighlightPerTapEnabled = false
        }
    }

    private fun observeViewModel() {
        viewModel.dailyConsumption.observe(viewLifecycleOwner, Observer { models ->
            showOnChart(bar_chart_hourly, models)
        })

        viewModel.weeklyConsumption.observe(viewLifecycleOwner, Observer { models ->
            showOnChart(bar_chart_weekly, models)
        })
    }

    private fun showOnChart(chart: BarChart, models: List<ConsumptionModel>) {
        val barEntries = models.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.consumption.toFloat())
        }

        val dataSet = BarDataSet(barEntries, "" /*No label*/)
        dataSet.valueFormatter = INT_VALUE_FORMATTER

        val barData = BarData(dataSet).apply {
            setValueTextSize(11f)
        }

        chart.data = barData
        chart.xAxis.labelCount = models.size
    }

}