fun main() {

    fun updateMap(map: MutableMap<Int, MutableList<Int>>, row: String) {
        val index = row.substringAfter('|').toInt()
        val value = row.substringBefore('|').toInt()
        var list = map[index]
        if (list == null) {
            list = mutableListOf()
        }
        list.add(value)
        map[index] = list
    }

    fun parseInput(input: List<String>): Pair<Map<Int, List<Int>>, List<List<Int>>> {
        val map = mutableMapOf<Int, MutableList<Int>>()
        var i = 0
        var row = input[i]
        while (row.isNotBlank()) {
            updateMap(map, row)
            i++
            row = input[i]
        }
        i++
        row = input[i]
        val list = mutableListOf<List<Int>>()
        while (row.isNotBlank() && i < input.size - 1) {
            list.add(row.split(",").map { it.toInt() })
            i++
            row = input[i]
        }
        if (row.isNotBlank()) {
            list.add(row.split(",").map { it.toInt() })
        }
        return Pair(map, list)
    }

    fun isOrdered(row: List<Int>, map: Map<Int, List<Int>>): Boolean {
        for (i in row.indices) {
            for (j in i until row.size) {
                val list = map[row[i]]
                if (list != null && list.contains(row[j])) {
                    return false
                }
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val (map, list) = parseInput(input)
        return list.sumOf { row ->
            if (isOrdered(row, map)) {
                row[row.size / 2]
            } else {
                0
            }
        }
    }

    fun swapValues(row: MutableList<Int>, map: Map<Int, List<Int>>): MutableList<Int> {
        for (i in row.indices) {
            for (j in i until row.size) {
                val list = map[row[i]]
                if (list != null && list.contains(row[j])) {
                    val value = row[j]
                    row[j] = row[i]
                    row[i] = value
                    return row
                }
            }
        }
        return row
    }

    fun orderRow(row: List<Int>, map: Map<Int, List<Int>>): List<Int> {
        var newRow = row.toMutableList()
        while (!isOrdered(newRow, map)) {
            newRow = swapValues(newRow, map)
        }
        return newRow
    }

    fun part2(input: List<String>): Int {
        val (map, list) = parseInput(input)
        return list.sumOf { row ->
            if (isOrdered(row, map)) {
                0
            } else {
                val newRow = orderRow(row, map)
                newRow[newRow.size / 2]
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day05_test")
    val input = readInput("input/Day05")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 143)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 123)
    println("Answer part2: " + part2(input))
}
