package com.hustonsolutions.adventofcode24

import android.content.Context
import android.util.Log

class AdventParser {
    fun parseInput1(context: Context): Pair<List<Int>, List<Int>> {
        val rawRes = R.raw.day_one // Replace with your file name
        val result = Pair(mutableListOf<Int>(), mutableListOf<Int>())
        context.resources.openRawResource(rawRes).bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val columns = line.trim().split("\\s+".toRegex()) // Split by spaces or tabs
                if (columns.isNotEmpty()) {
                    if (columns.size > 2) {
                        Log.i(
                            "AdventParser",
                            "Strange line with too many columns. Using first two. Line: $line"
                        )
                    }

                    val first = columns[0].toIntOrNull()
                    val second = columns[1].toIntOrNull()
                    if (first != null) {
                        result.first.add(first)
                    } else {
                        Log.i("AdventParser", "No first data in line: $line")
                    }
                    if (second != null) {
                        result.second.add(second)
                    } else {
                        Log.i("AdventParser", "No second data in line: $line")
                    }
                } else {
                    Log.i("AdventParser", "Skipping malformed line: $line")
                }
            }
        }
        return result
    }

    fun parseInput2(context: Context): List<List<Int>> {
        val rawRes = R.raw.day_two // Replace with your file name
        val result = mutableListOf<List<Int>>()
        context.resources.openRawResource(rawRes).bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val columns = line.trim().split("\\s+".toRegex()) // Split by spaces or tabs
                if (columns.isNotEmpty()) {
//                    if (columns.size > 0) {
//                        Log.i(
//                            "AdventParser",
//                            "Strange line with no columns. $line"
//                        )
//                    }

                    val intColumns = columns.map { it.toInt() }
                    result.add(intColumns)
                } else {
                    Log.i("AdventParser", "Skipping malformed line: $line")
                }
            }
        }
        return result
    }

    fun parseInput3(context: Context): String {
        val rawRes = R.raw.day_three // Replace with your file name
        return context.resources.openRawResource(rawRes).bufferedReader().readText()
    }

    fun parseInput4(context: Context): List<String> {
        val rawRes = R.raw.day_four // Replace with your file name
        return context.resources.openRawResource(rawRes).bufferedReader().readLines()
    }

    fun parseInput5(context: Context): Pair<List<PageOrderRule>,List<PrintUpdate>> {
        val rawRes = R.raw.day_five // Replace with your file name
        val pageOrderRules = mutableListOf<PageOrderRule>()
        val printUpdates = mutableListOf<PrintUpdate>()
        context.resources.openRawResource(rawRes).bufferedReader().forEachLine { line ->
            // Parse page order rules
            Regex("(\\d+)\\|(\\d+)").find(line)?.let { match ->
                val (earlierPage, laterPage) = match.destructured
                if (earlierPage.isNotEmpty() && laterPage.isNotEmpty()) {
                    pageOrderRules.add(PageOrderRule(earlierPage.toInt(), laterPage.toInt()))
                } else {
                    Log.i("AdventParser", "Skipping malformed line: $line")
                }
            }

            // Parse updates to print
                ?: line.split(",")
                    .mapNotNull { it.toIntOrNull() }
                    .takeIf { it.isNotEmpty() }
                    ?.let { pageNumbers ->
                        printUpdates.add(PrintUpdate(pageNumbers))
                    }
        }
        return Pair(pageOrderRules, printUpdates)
    }
}

data class PageOrderRule(
    val earlierPage: Int,
    val laterPage: Int,
)

data class PrintUpdate(
    val pages: List<Int>,
)