package com.example.tipcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalc.ui.theme.TipCalcTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalcScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TipCalcScreen(modifier: Modifier = Modifier) {
    var billAmount by remember { mutableStateOf("") }
    var dishCount by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(0f) }
    var discountPercent by remember { mutableStateOf(0) }
    var resultText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Калькулятор счёта", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Валидация: разрешаем только цифры и точку для суммы
        OutlinedTextField(
            value = billAmount,
            onValueChange = { input ->
                if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*$"))) {
                    billAmount = input
                }
            },
            label = { Text("Сумма счёта") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // Валидация: разрешаем только цифры для количества
        OutlinedTextField(
            value = dishCount,
            onValueChange = { input ->
                if (input.isEmpty() || input.all { it.isDigit() }) {
                    dishCount = input
                    val count = input.toIntOrNull() ?: 0
                    discountPercent = when {
                        count in 1..2 -> 3
                        count in 3..5 -> 5
                        count in 6..10 -> 7
                        count > 10 -> 10
                        else -> 0
                    }
                }
            },
            label = { Text("Количество блюд") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        // Слайдер с шагом в 1% и точным отображением
        Text("Чаевые: ${tipPercent.roundToInt()}%")
        Slider(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            valueRange = 0f..25f,
            steps = 24, // Делает слайдер дискретным (от 0 до 25 ровно)
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Text("Скидка (выбирается автоматически):")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = discountPercent == 3, onClick = null)
            Text("3% ")
            RadioButton(selected = discountPercent == 5, onClick = null)
            Text("5% ")
            RadioButton(selected = discountPercent == 7, onClick = null)
            Text("7% ")
            RadioButton(selected = discountPercent == 10, onClick = null)
            Text("10%")
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val bill = billAmount.toDoubleOrNull()
            if (bill == null || bill <= 0) {
                resultText = "Введите корректную сумму"
                return@Button
            }

            // Берем ровно округленное значение чаевых для точного расчета
            val exactTip = tipPercent.roundToInt()

            val tipAmount = bill * (exactTip / 100.0)
            val discountAmount = bill * (discountPercent / 100.0)
            val total = bill + tipAmount - discountAmount

            // Форматируем вывод с указанием процентов
            resultText = String.format(
                "Счёт: %.2f\nЧаевые (%d%%): %.2f\nСкидка (%d%%): -%.2f\nИтого: %.2f",
                bill, exactTip, tipAmount, discountPercent, discountAmount, total
            )
        }) {
            Text("Рассчитать")
        }

        Spacer(Modifier.height(16.dp))

        // Текст выровнен по левому краю
        Text(
            text = resultText,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalcScreenPreview() {
    TipCalcTheme {
        TipCalcScreen()
    }
}