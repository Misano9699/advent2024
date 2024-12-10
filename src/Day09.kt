fun main() {

    fun parseInput(row: String): List<Int> {
        return row.indices.map { row[it].toString().toInt() }
    }

    fun expandRow(row: List<Int>): List<Int?> {
        var block = true
        var id = 0
        val expandedRow = mutableListOf<Int?>()
        row.indices.forEach { i ->
            if (block) {
                (0..<row[i]).forEach { _ -> expandedRow.add(id) }
                id++
            } else {
                (0..<row[i]).forEach { _ -> expandedRow.add(null) }
            }
            block = !block
        }
        return expandedRow
    }

    fun swapRow(expandedRow: List<Int?>): List<Int> {
        val swappedRow = mutableListOf<Int>()
        var expandedRowIndex = expandedRow.size - 1
        val stop = expandedRow.count { it != null }
        expandedRow.indices.forEach { i ->
            if (expandedRow[i] != null) {
                swappedRow.add(expandedRow[i]!!)
            } else {
                while (expandedRow[expandedRowIndex] == null) {
                    expandedRowIndex--
                }
                swappedRow.add(expandedRow[expandedRowIndex]!!)
                expandedRowIndex--
            }
            if (swappedRow.size == stop) {
                return swappedRow
            }
        }
        return swappedRow
    }

    fun swapNumber(number: Int, index: Int, size: Int, swappedRow: MutableList<Int?>): MutableList<Int?> {
        var i = 0
        var j = 0
        while (i < index) {
            if (swappedRow[i] == null) {
                j++
            } else {
                j = 0
            }
            if (j == size) {
//                println("$number, $size, ${i-j+1}, ${index+1}")
                (0..<size).forEach {
                    swappedRow[i - j + it + 1] = number
                    swappedRow[index + it + 1] = null
                }
                return swappedRow
            }
            i++
        }
        return swappedRow
    }

    fun swapRowPart2(expandedRow: List<Int?>): MutableList<Int?> {
        var swappedRow = expandedRow.toMutableList()
        val numbersProcessed = mutableSetOf<Int>()
        var number: Int? = null
        var i = swappedRow.size - 1
        var size = 1
        while (i >= 0) {
            if (swappedRow[i] != null) {
                if (number == swappedRow[i]) {
                    size++
                } else {
                    if (number != null) {
                        if (!numbersProcessed.contains(number)) {
                            numbersProcessed.add(number)
                            swappedRow = swapNumber(number, i, size, swappedRow)
                        }
                    }
                    number = swappedRow[i]
                    size = 1
                }
            } else {
                if (number != null) {
                    if (!numbersProcessed.contains(number)) {
                        numbersProcessed.add(number)
                        swappedRow = swapNumber(number, i, size, swappedRow)
                    }
                }
                number = swappedRow[i]
                size = 0
            }
            i--
        }
        return swappedRow
    }

    fun checksum(swappedRow: List<Int?>): Long {
        return swappedRow.indices.sumOf {
            when (swappedRow[it]) {
                null -> 0L
                else -> swappedRow[it]!!.toLong() * it.toLong()
            }
        }
    }

    fun part1(input: List<String>): Long {
        val row = parseInput(input[0])
        val expandedRow = expandRow(row)
        val swappedRow = swapRow(expandedRow)
        return checksum(swappedRow)
    }

    fun part2(input: List<String>): Long {
        val row = parseInput(input[0])
        val expandedRow = expandRow(row)
        val swappedRow = swapRowPart2(expandedRow)
        return checksum(swappedRow)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day09_test")
//    val testInput2 = readInput("input/Day09_test_2")
    val input = readInput("input/Day09")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 1928L)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    // this works
    check(part2(testInput).also { println("Answer test input part2: $it") } == 2858L)
    // this works
//    check(part2(testInput2).also { println("Answer test input part2: $it") } == 5799706413896802L)
    // but this doesn't work
    println("Answer part2: " + part2(input))
}
