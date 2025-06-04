package com.example.myapplication2

import android.os.Bundle
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
import com.example.myapplication2.ui.theme.MyApplication2Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorUI()
        }
    }
}

@Composable
fun CalculatorUI() {
    var displayText by remember { mutableStateOf("123456789") }

    val buttons = listOf(
        listOf("C", "+/-", "%", "/"),
        listOf("7", "8", "9", "x"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", "0_continued", ".", "=")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(Color.Black),
            contentAlignment = Alignment.BottomEnd
        ) {
            BasicText(
                text = displayText,
                modifier = Modifier.padding(16.dp),
                style = LocalTextStyle.current.copy(
                    fontSize = 50.sp,
                    color = Color.White,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth()
        ) {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    row.forEachIndexed { index, label ->
                        if (label == "0_continued") return@forEachIndexed

                        val isOperator = index == 4 || label == "="

                        val weight = if (label == "0") 2f else 1f

                        Button(
                            onClick = { /* TODO */ },
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isOperator) Color.Red else Color.White,
                                contentColor = if (isOperator) Color.White else Color.Black
                            ),
                            modifier = Modifier
                                .weight(weight)
                                .fillMaxHeight()
                                .padding(2.dp)
                        ) {
                            Text(label, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorPreview() {
    CalculatorUI()
}