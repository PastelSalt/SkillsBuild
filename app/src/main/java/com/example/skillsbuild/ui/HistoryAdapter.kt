package com.example.skillsbuild.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsbuild.R
import com.example.skillsbuild.data.Calculation
import java.text.DateFormat
import java.util.*

class HistoryAdapter : ListAdapter<Calculation, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Calculation>() {
            override fun areItemsTheSame(oldItem: Calculation, newItem: Calculation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Calculation, newItem: Calculation): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val calculation = getItem(position)
        holder.bind(calculation)
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val calculationText: TextView = itemView.findViewById(R.id.calculation_text)
        private val resultText: TextView = itemView.findViewById(R.id.result_text)
        private val dateText: TextView = itemView.findViewById(R.id.date_text)

        fun bind(calc: Calculation) {
            calculationText.text = calc.expression
            resultText.text = calc.result
            val df = DateFormat.getDateTimeInstance()
            dateText.text = df.format(Date(calc.timestamp))
        }
    }
}
