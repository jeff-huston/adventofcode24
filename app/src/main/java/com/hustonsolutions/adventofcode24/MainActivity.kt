package com.hustonsolutions.adventofcode24

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hustonsolutions.adventofcode24.ui.theme.AdventOfCode24Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = AdventParser().parseInput4(context = this)
        val processor = DayFourProcessor()
        val result1 = processor.doPartOne(data)
        Log.i("Part One Result", result1)
        val result2 = processor.doPartTwo(data)
        Log.i("Part Two Result", result2)

        setContent {
            AdventOfCode24Theme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp), // Space between items
                    horizontalAlignment = Alignment.CenterHorizontally // Align items horizontally
                ) {
                    Text("Part 1: $result1", Modifier.background(Color.LightGray))
                    Text("Part 2: $result2", Modifier.background(Color.Cyan))
                }
            }
        }

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
    AdventOfCode24Theme {
        Greeting("Android")
    }
}