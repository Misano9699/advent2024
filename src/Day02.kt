import kotlin.math.abs

fun main() {

    fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { line -> line.split(" ").map { it.toInt() } }
    }

    fun isSafe(row : List<Int>) : Boolean {
        val map = (0..row.size - 2).map {
            row[it] - row[it + 1]
        }
        return (map.all { it > 0 } || map.all { it < 0 }) && map.all { abs(it) <= 3 }
    }

    fun part1(input: List<String>): Int {
        val list = parseInput(input)
        return list.map { row -> isSafe(row) }
            .count { it }
    }

    fun part2(input: List<String>): Int {
        val list = parseInput(input)
        return list.map { row ->
            var i = 0
            var mutableList = row.toMutableList()
            while (!isSafe(mutableList) && i < row.size) {
                mutableList = row.toMutableList()
                mutableList.removeAt(i)
                i++
            }
            isSafe(mutableList)
        }.count { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day02_test")
    val input = readInput("input/Day02")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 2)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 4)
    println("Answer part2: " + part2(input))
}
