package com.hustonsolutions.adventofcode24

class DayFourProcessor {
    fun doPartOne(input: List<String>): String {
        val rowsOfCells = input.map { it.toCharArray().toList() }
        val maxColumns = rowsOfCells.maxOf { it.size }

        val rowsInput = input
        val forwardsCount = rowsInput.sumOf { aRow ->
            Regex("XMAS")
                .findAll(aRow)
                .count()
        }
        val backwardsCount = rowsInput.sumOf { aRow ->
            Regex("SAMX")
                .findAll(aRow)
                .count()
        }

        val columnsInput = mutableListOf<String>()
        for (i in 0 until maxColumns) {
            val column = rowsOfCells.map { it.getOrNull(i) ?: " " }.joinToString("")
            columnsInput.add(column)
        }
        val downCount = columnsInput.sumOf { aRow ->
            Regex("XMAS")
                .findAll(aRow)
                .count()
        }
        val upCount = columnsInput.sumOf { aRow ->
            Regex("SAMX")
                .findAll(aRow)
                .count()
        }

        val yEqualsXInput = mutableListOf<String>()
        val reversedRowsOfCells = rowsOfCells.reversed()
        for (columnIndex in -1 * rowsOfCells.size until maxColumns) {
            val column = reversedRowsOfCells.mapIndexed { rowIndex, chars ->
                chars.getOrNull(columnIndex + rowIndex) ?: " "
            }.joinToString("")
            yEqualsXInput.add(column)
        }
        val stonksUpCount = yEqualsXInput.sumOf { aRow ->
            Regex("XMAS")
                .findAll(aRow)
                .count()
        }
        val stonksUpBackwardsCount = yEqualsXInput.sumOf { aRow ->
            Regex("SAMX")
                .findAll(aRow)
                .count()
        }

        val yEqualsNegativeXInput = mutableListOf<String>()
        for (columnIndex in -1 * rowsOfCells.size until maxColumns) {
            val column = rowsOfCells.mapIndexed { rowIndex, chars ->
                chars.getOrNull(columnIndex + rowIndex) ?: " "
            }.joinToString("")
            yEqualsNegativeXInput.add(column)
        }
        val stonksDownCount = yEqualsNegativeXInput.sumOf { aRow ->
            Regex("XMAS")
                .findAll(aRow)
                .count()
        }
        val stonksDownBackwardsCount = yEqualsNegativeXInput.sumOf { aRow ->
            Regex("SAMX")
                .findAll(aRow)
                .count()
        }
        return (
            forwardsCount +
            backwardsCount +
            downCount +
            upCount +
            stonksUpCount +
            stonksUpBackwardsCount +
            stonksDownCount +
            stonksDownBackwardsCount
        ).toString()
    }

    data class Coordinate(val x: Int, val y: Int)

    fun doPartTwo(input: List<String>): String {
        val rowsOfCells = input.map { it.toCharArray().toList() }
        val maxColumns = rowsOfCells.maxOf { it.size }

        val rowsInput = input
        val forwardsCenters = rowsInput.mapIndexed { rowIndex, aRow ->
            Regex("MAS")
                .findAll(aRow)
                .map { Coordinate(x = it.range.first + 1, y = rowIndex) }
                .toList()
        }.flatten()
        val backwardsCenters = rowsInput.mapIndexed { rowIndex, aRow ->
            Regex("SAM")
                .findAll(aRow)
                .map { Coordinate(x = it.range.first + 1, y = rowIndex) }
                .toList()
        }.flatten()
        val horizontalCenters = forwardsCenters + backwardsCenters

        val columnsInput = mutableListOf<String>()
        for (i in 0 until maxColumns) {
            val column = rowsOfCells.map { it.getOrNull(i) ?: " " }.joinToString("")
            columnsInput.add(column)
        }
        val downCenters = columnsInput.mapIndexed { columnIndex, aColumn ->
            Regex("MAS")
                .findAll(aColumn)
                .map { Coordinate(x = columnIndex, y = it.range.first + 1) }
                .toList()
        }.flatten()
        val upCenters = columnsInput.mapIndexed { columnIndex, aColumn ->
            Regex("SAM")
                .findAll(aColumn)
                .map { Coordinate(x = columnIndex, y = it.range.first + 1) }
                .toList()
        }.flatten()
        val verticalCenters = downCenters + upCenters

        val pluses = horizontalCenters.filter { it in verticalCenters }

        val yEqualsXInput = mutableListOf<String>()
        val reversedRowsOfCells = rowsOfCells.reversed()
        val startingYEqualsXIndex = -1 * rowsOfCells.size
        for (columnIndex in startingYEqualsXIndex until maxColumns) {
            val column = reversedRowsOfCells.mapIndexed { rowIndex, chars ->
                chars.getOrNull(columnIndex + rowIndex) ?: " "
            }.joinToString("")
            yEqualsXInput.add(column)
        }
        val stonksUpCenters = yEqualsXInput.mapIndexed { diagonalIndex, aDiagonal ->
            Regex("MAS")
                .findAll(aDiagonal)
                .map {
                    val relativeIndex = it.range.first + 1
                    val absoluteYIndex = rowsOfCells.size - 1 - relativeIndex
                    val absoluteXIndex = startingYEqualsXIndex + diagonalIndex + relativeIndex
                    Coordinate(x = absoluteXIndex, y = absoluteYIndex)
                }
                .toList()
        }.flatten()
        val stonksUpBackwardsCenters = yEqualsXInput.mapIndexed { diagonalIndex, aDiagonal ->
            Regex("SAM")
                .findAll(aDiagonal)
                .map {
                    val relativeIndex = it.range.first + 1
                    val absoluteYIndex = rowsOfCells.size - 1 - relativeIndex
                    val absoluteXIndex = startingYEqualsXIndex + diagonalIndex + relativeIndex
                    Coordinate(x = absoluteXIndex, y = absoluteYIndex)
                }
                .toList()
        }.flatten()
        val yEqualsXCenters = stonksUpCenters + stonksUpBackwardsCenters

        val yEqualsNegativeXInput = mutableListOf<String>()
        val startingYEqualsNegativeXIndex = -1 * rowsOfCells.size
        for (columnIndex in startingYEqualsNegativeXIndex until maxColumns) {
            val column = rowsOfCells.mapIndexed { rowIndex, chars ->
                chars.getOrNull(columnIndex + rowIndex) ?: " "
            }.joinToString("")
            yEqualsNegativeXInput.add(column)
        }
        val stonksDownCenters = yEqualsNegativeXInput.mapIndexed { diagonalIndex, aDiagonal ->
            Regex("MAS")
                .findAll(aDiagonal)
                .map {
                    val relativeIndex = it.range.first + 1
                    val absoluteYIndex = relativeIndex
                    val absoluteXIndex = startingYEqualsNegativeXIndex + diagonalIndex + relativeIndex
                    Coordinate(x = absoluteXIndex, y = absoluteYIndex)
                }
                .toList()
        }.flatten()
        val stonksDownBackwardsCenters = yEqualsNegativeXInput.mapIndexed { diagonalIndex, aDiagonal ->
            Regex("SAM")
                .findAll(aDiagonal)
                .map {
                    val relativeIndex = it.range.first + 1
                    val absoluteYIndex = relativeIndex
                    val absoluteXIndex = startingYEqualsNegativeXIndex + diagonalIndex + relativeIndex
                    Coordinate(x = absoluteXIndex, y = absoluteYIndex)
                }
                .toList()
        }.flatten()
        val yEqualsNegativeXCenters = stonksDownCenters + stonksDownBackwardsCenters

        val exes = yEqualsXCenters.filter { it in yEqualsNegativeXCenters }
        return (exes.count()).toString()
    }
}