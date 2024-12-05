package com.hustonsolutions.adventofcode24

import kotlin.math.abs

class DayOneProcessor {
    fun doPartOne(lists: Pair<List<Int>, List<Int>>): String {
        val sortedList1 = lists.first.sorted()
        val sortedList2 = lists.second.sorted()

        return sortedList1.zip(sortedList2) { first, second ->
            abs(first - second)
        }
            .sum()
            .toString()
    }

    fun doPartTwo(lists: Pair<List<Int>, List<Int>>): String {
        val sortedList1 = lists.first.sorted()
        val sortedList2 = lists.second.sorted()

        return sortedList1.map { first ->
            first * sortedList2.count { it == first }
        }.sum().toString()
    }
}