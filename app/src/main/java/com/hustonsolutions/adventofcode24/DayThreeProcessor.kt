package com.hustonsolutions.adventofcode24

class DayThreeProcessor {
    fun doPartOne(input: String): String {
        val products = Regex("mul\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)")
            .findAll(input)
            .map { match ->
                match.groupValues[1].toInt() * match.groupValues[2].toInt()
            }
        return products.sum().toString()
    }

    fun doPartTwo(input: String): String {
        val doIndices = Regex("do\\s*\\(\\s*\\)")
            .findAll(input)
            .map { it.range.first }
            .toMutableList()
        val dontIndices = Regex("don\'t\\s*\\(\\s*\\)")
            .findAll(input)
            .map { it.range.first }
            .toMutableList()
        val enabledRanges = mutableListOf<IntRange>()
        var enabled = true
        var startIndex = 0
        while (doIndices.isNotEmpty() && dontIndices.isNotEmpty()) {
            if (enabled) {
                val nextDont = dontIndices.removeFirstOrNull() ?: break
                doIndices.removeIf { it < nextDont }
                enabledRanges.add(startIndex until nextDont)
                startIndex = nextDont
            } else {
                val nextDo = doIndices.removeFirstOrNull() ?: break
                dontIndices.removeIf { it < nextDo }
                startIndex = nextDo
            }
            enabled = !enabled
        }
        enabledRanges.add(startIndex until input.length)

        val products = Regex("mul\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)")
            .findAll(input)
            .map { match ->
                // Remove ranges that are before the current match
                enabledRanges.removeIf { it.last < match.range.first }
                // Get the next or active range
                val nextOrActiveRange = enabledRanges.firstOrNull() ?: return@map 0

                if (match.range.first in nextOrActiveRange) {
                    match.groupValues[1].toInt() * match.groupValues[2].toInt()
                } else {
                    // Haven't reached the next enabled range yet
                    0
                }
            }
        return products.sum().toString()
    }

    private fun takeIfValid(
        intColumns: List<Int>
    ): List<Int>? {
        val differenceFromPrevious = intColumns.zipWithNext { a, b -> b - a }
        return intColumns.takeIf {
            val maxDiff = differenceFromPrevious.max()
            val minDiff = differenceFromPrevious.min()
            maxDiff <= 3 && minDiff >= 1 || maxDiff <= -1 && minDiff >= -3
        }
    }
}