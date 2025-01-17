package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.trackPipAnimationHintView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentNumber = ""
    private var previousNumber = ""
    private var operation = ""
    private var calculationString = ""
    private var isCalculationDone = false
    private val calculationHistory = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBtnNumberOne.setOnClickListener { appendNumber("1") }
        binding.btnBtnNumberTwo.setOnClickListener { appendNumber("2") }
        binding.btnNumberThree.setOnClickListener { appendNumber("3") }
        binding.btnBtnNumberFour.setOnClickListener { appendNumber("4") }
        binding.btnBtnNumberFive.setOnClickListener { appendNumber("5") }
        binding.btnBtnNumberSix.setOnClickListener { appendNumber("6") }
        binding.btnNumberSeven.setOnClickListener { appendNumber("7") }
        binding.btnNumberEight.setOnClickListener { appendNumber("8") }
        binding.btnNumberNine.setOnClickListener { appendNumber("9") }
        binding.btnBtnNumberZero.setOnClickListener { appendNumber("0") }
        binding.btnDot.setOnClickListener { appendNumber(".") }

        binding.btnAdd.setOnClickListener { setOperation("+") }
        binding.btnMinus.setOnClickListener { setOperation("-") }
        binding.btnMultiply.setOnClickListener { setOperation("x") }
        binding.btnDivide.setOnClickListener { setOperation("/") }

        binding.btnPercent.setOnClickListener { applyPercentage() }

        binding.btnEquals.setOnClickListener { calculateResult() }

        binding.btnAc.setOnClickListener {
            clearAll()
        }

        binding.btnDropOne.setOnClickListener {


             if (currentNumber.isNotEmpty()) {
                currentNumber = currentNumber.dropLast(1)
                calculationString = calculationString.dropLast(1)
                binding.tvResult.text = if (currentNumber.isEmpty()) "0" else calculationString

            } else if(operation.isNotEmpty() && calculationString.isNotEmpty()){
                operation = ""
                calculationString = calculationString.trimEnd().dropLast(1).trimEnd()
                binding.tvResult.text = calculationString.ifEmpty{"0"}

            }else if (isCalculationDone){
                clearAll()
             }
        }
    }

    private fun appendNumber(number: String) {
        currentNumber += number
        calculationString += number
        binding.tvResult.text = calculationString
    }

    private fun setOperation(op: String) {
        if (currentNumber.isNotEmpty() || previousNumber.isNotEmpty()) {
            if (currentNumber.isNotEmpty()) {
                previousNumber = currentNumber
            }
            currentNumber = ""
            operation = op
            calculationString += " $operation "
            binding.tvResult.text = calculationString
        }
    }

    private fun applyPercentage() {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDoubleOrNull()
            if (number != null) {
                val percentageResult = number * (number / 100)
                currentNumber = (number + percentageResult).toString()
                calculationString += "%"
                binding.tvResult.text = calculationString
            }
        }
    }

    private fun calculateResult() {
        val num1 = previousNumber.toDoubleOrNull()
        val num2 = currentNumber.toDoubleOrNull()

        if (num1 != null && num2 != null) {
            val result = when (operation) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "x" -> num1 * num2
                "/" -> if (num2 != 0.0) num1 / num2 else {
                    binding.tvResult.text = "Erro: Divisão por zero"
                    return
                }
                else -> {
                    binding.tvResult.text = "Operação inválida"
                    return
                }
            }

            calculationString += " = $result"
            binding.tvResultCalculation.text = calculationString
            binding.tvResult.text = result.toString()

            calculationHistory.add(calculationString)

            previousNumber = result.toString()
            currentNumber = ""
            operation = ""
            calculationString = result.toString()
            isCalculationDone = true
        }
    }

    private fun clearAll() {
        currentNumber = ""
        previousNumber = ""
        operation = ""
        calculationString = ""
        binding.tvResult.text = "0"
        binding.tvResultCalculation.text = ""
    }
}


