package com.example.skillsbuild

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skillsbuild.ui.theme.SkillsBuildTheme
import com.example.skillsbuild.ui.HistoryAdapter
import com.example.skillsbuild.viewmodel.HistoryViewModel
import com.example.skillsbuild.viewmodel.HistoryViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use the XML layout/entry point (res/layout/activity_main.xml)
        setContentView(R.layout.activity_main)

        val edit1 = findViewById<EditText>(R.id.editTextNumber)
        val edit2 = findViewById<EditText>(R.id.editTextNumber2)
        val button = findViewById<Button>(R.id.button2)
        val result = findViewById<TextView>(R.id.textView)
        val clearBtn = findViewById<Button>(R.id.clearHistoryBtn)
        val recycler = findViewById<RecyclerView>(R.id.historyRecyclerView)

        // Setup RecyclerView
        val adapter = HistoryAdapter()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // Setup ViewModel
        val factory = HistoryViewModelFactory(application)
        historyViewModel = ViewModelProvider(this, factory).get(HistoryViewModel::class.java)

        // Observe history
        lifecycleScope.launch {
            historyViewModel.history.collectLatest { list ->
                adapter.submitList(list)
            }
        }

        clearBtn.setOnClickListener {
            historyViewModel.clearHistory()
        }

        button.setOnClickListener {
            val s1 = edit1.text.toString().trim()
            val s2 = edit2.text.toString().trim()

            if (s1.isEmpty() || s2.isEmpty()) {
                if (s1.isEmpty()) edit1.error = getString(R.string.hint_first_number)
                if (s2.isEmpty()) edit2.error = getString(R.string.hint_second_number)
                result.text = getString(R.string.label_answer)
                return@setOnClickListener
            }

            val n1 = s1.toDoubleOrNull()
            val n2 = s2.toDoubleOrNull()

            if (n1 == null || n2 == null) {
                result.text = getString(R.string.label_answer) + ": " + getString(R.string.invalid_input)
            } else {
                val sum = n1 + n2
                val formatted = if (sum % 1.0 == 0.0) sum.toLong().toString() else String.format("%.2f", sum)
                result.text = getString(R.string.label_answer) + ": $formatted"
                hideKeyboard()

                // Save to history
                val expression = "$s1 + $s2"
                val timestamp = System.currentTimeMillis()
                historyViewModel.addCalculation(expression, formatted, timestamp)
            }
        }
    }

    private fun hideKeyboard() {
        val view = currentFocus ?: View(this)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SkillsBuildTheme {
        Greeting("Android")
    }
}
