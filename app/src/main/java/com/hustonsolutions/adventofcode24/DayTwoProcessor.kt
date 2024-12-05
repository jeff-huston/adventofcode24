package com.hustonsolutions.adventofcode24

class DayTwoProcessor {
    fun doPartOne(lists: List<List<Int>>): String {
        val result = mutableListOf<List<Int>>()
        lists.forEach { intColumns ->
            takeIfValid(intColumns)?.also { result.add(it) }
        }
        return result.count().toString()
    }

    fun doPartTwo(lists: List<List<Int>>): String {
        val result = mutableListOf<List<Int>>()
        lists.forEach { intColumns ->
            takeIfValid(intColumns)?.also {
                result.add(it)
                return@forEach
            }

            for (i in intColumns.indices) {
                val dampenedColumns = intColumns.toMutableList()
                dampenedColumns.removeAt(i)
                takeIfValid(dampenedColumns)?.also {
                    result.add(it)
                    return@forEach
                }
            }
        }

        return result.count().toString()
    }

//            val correctedColumns = intColumns.toMutableList()
//            val initialDifferenceFromPrevious = correctedColumns.zipWithNext { a, b -> b - a }
//
//            val tooLargeIncrease = initialDifferenceFromPrevious.find { it > 3 }
//            val tooLargeDecrease = initialDifferenceFromPrevious.find { it < -3 }
//            val increasingCount = initialDifferenceFromPrevious.count { it > 0 }
//            val decreasingCount = initialDifferenceFromPrevious.count { it < 0 }
//            val repetitionCount = initialDifferenceFromPrevious.count { it == 0 }
//
//            when {
//                tooLargeIncrease != null -> {
//                    val index = 1 + initialDifferenceFromPrevious.indexOf(tooLargeIncrease)
//                    correctedColumns.removeAt(index)
//                }
//
//                tooLargeDecrease != null -> {
//                    val index = 1 + initialDifferenceFromPrevious.indexOf(tooLargeDecrease)
//                    correctedColumns.removeAt(index)
//                }
//
//                repetitionCount > 0 -> {
//                    val index = 1 + initialDifferenceFromPrevious.indexOfFirst { it == 0 }
//                    correctedColumns.removeAt(index)
//                }
//
//                increasingCount > 1 && decreasingCount > 1 -> {
//                    if (increasingCount > decreasingCount) {
//                        // Remove first decrease
//                        val index = 1 + initialDifferenceFromPrevious.indexOfFirst { it < 0 }
//                        correctedColumns.removeAt(index)
//                    } else {
//                        // Remove first increase
//                        val index = 1 + initialDifferenceFromPrevious.indexOfFirst { it > 0 }
//                        correctedColumns.removeAt(index)
//                    }
//                }
//            }
//
//            // Analyze again
//            val dampenedDifferenceFromPrevious = correctedColumns.zipWithNext { a, b -> b - a }
//            correctedColumns.takeIf {
//                val maxDiff = dampenedDifferenceFromPrevious.max()
//                val minDiff = dampenedDifferenceFromPrevious.min()
//                maxDiff <= 3 && minDiff >= 1 || maxDiff <= -1 && minDiff >= -3
//            }?.also { result.add(it) }
//        }
//    }

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