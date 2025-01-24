package com.example.calculator

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calculationHistory = intent.getStringArrayListExtra("LIST_OF_CALCULATION") ?: arrayListOf()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, calculationHistory)
        binding.lvHistory.adapter = adapter

    }
}