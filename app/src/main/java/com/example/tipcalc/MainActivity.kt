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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Калькулятор чаевых", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Поле ввода суммы счёта
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Сумма счёта") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))

        // Поле ввода количества блюд
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Количество блюд") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))

        // Слайдер
        Text(text = "Чаевые: 0%")
        Slider(
            value = 0f,
            onValueChange = { },
            valueRange = 0f..25f,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        // Группа радиокнопок для скидки
        Text(text = "Скидка по количеству блюд:", style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = false, onClick = null)
            Text("3%")
            Spacer(Modifier.padding(4.dp))
            RadioButton(selected = false, onClick = null)
            Text("5%")
            Spacer(Modifier.padding(4.dp))
            RadioButton(selected = false, onClick = null)
            Text("7%")
            Spacer(Modifier.padding(4.dp))
            RadioButton(selected = false, onClick = null)
            Text("10%")
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = { }) {
            Text("Рассчитать")
        }
        Spacer(Modifier.height(16.dp))
        Text(text = "Итог: ")
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalcScreenPreview() {
    TipCalcTheme {
        TipCalcScreen()
    }
}