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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalc.ui.theme.TipCalcTheme

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
    // Состояние для текущей скидки
    var discountPercent by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Калькулятор счёта", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Сумма счёта") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // Логика выбора скидки при вводе блюд
        OutlinedTextField(
            value = dishCount,
            onValueChange = { input ->
                dishCount = input
                val count = input.toIntOrNull() ?: 0
                discountPercent = when {
                    count in 1..2 -> 3
                    count in 3..5 -> 5
                    count in 6..10 -> 7
                    count > 10 -> 10
                    else -> 0
                }
            },
            label = { Text("Количество блюд") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Text("Чаевые: ${tipPercent.toInt()}%")
        Slider(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            valueRange = 0f..25f,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Text("Скидка (выбирается автоматически):")
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Выбор кнопки зависит от значения discountPercent
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

        Button(onClick = { }) {
            Text("Рассчитать")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalcScreenPreview() {
    TipCalcTheme {
        TipCalcScreen()
    }
}