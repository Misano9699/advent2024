import kotlin.math.abs

fun main() {

    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        val (left, right) = input
            .map { Pair(it.substringBefore(' ').trim().toInt(), it.substringAfter(' ').trim().toInt()) }
            .toList()
            .unzip()
        return Pair(left.sorted(), right.sorted())
    }

    fun part1(input: List<String>): Int {
        val (left, right) = parseInput(input)
        return left.indices.sumOf { abs(left[it] - right[it]) }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = parseInput(input)
        return left.indices.sumOf { index -> left[index] * right.count { it == left[index] } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day01_test")
    val input = readInput("input/Day01")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 11)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 31)
    println("Answer part2: " + part2(input))
}
