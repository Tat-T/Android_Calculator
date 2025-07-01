package com.example.myapplication2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
class CalculatorViewModel : ViewModel() {

    private val _display = MutableStateFlow("0")
    val display: StateFlow<String> = _display.asStateFlow()

    private var currentInput = ""
    private var lastOperand = ""
    private var lastOperator: String? = null

    fun onButtonClick(label: String) {
        when (label) {
            "C" -> clear()
            "+/-" -> toggleSign()
            "%" -> applyPercent()
            "=" -> calculateResult()
            "/", "x", "-", "+" -> handleOperator(label)
            "." -> appendDot()
            else -> appendNumber(label)
        }
        updateDisplay()
    }

    private fun clear() {
        currentInput = ""
        lastOperand = ""
        lastOperator = null
    }

    private fun toggleSign() {
        currentInput = if (currentInput.startsWith("-")) {
            currentInput.removePrefix("-")
        } else {
            "-$currentInput"
        }
    }

    private fun applyPercent() {
        currentInput = currentInput.toDoubleOrNull()?.div(100)?.toString() ?: ""
    }

    private fun appendDot() {
        if (!currentInput.contains(".")) {
            currentInput += if (currentInput.isEmpty()) "0." else "."
        }
    }

    private fun appendNumber(digit: String) {
        if (digit == "0" && currentInput == "0") return
        currentInput += digit
    }

    private fun handleOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (lastOperand.isNotEmpty() && lastOperator != null) {
                calculateResult()
            } else {
                lastOperand = currentInput
                currentInput = ""
            }
        }
        lastOperator = operator
    }

    private fun calculateResult() {
        val first = lastOperand.toDoubleOrNull()
        val second = currentInput.toDoubleOrNull()
        val op = lastOperator

        if (first != null && second != null && op != null) {
            val result = when (op) {
                "+" -> first + second
                "-" -> first - second
                "x" -> first * second
                "/" -> if (second != 0.0) first / second else "Error"
                else -> "Error"
            }
            val resultStr = if (result is Double)
                if (result % 1 == 0.0) result.toInt().toString() else result.toString()
            else
                result.toString()

            currentInput = resultStr
            lastOperand = ""
            lastOperator = null
        }
    }

    private fun updateDisplay() {
        _display.value = if (currentInput.isEmpty()) "0" else currentInput
    }
}