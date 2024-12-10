import kotlin.math.pow

fun main() {

    fun parseInput(input: List<String>): List<Pair<Long, List<Long>>> {
        return input.map {
            val sum = it.substringBefore(":").toLong()
            val operands = it.substringAfter(": ").split(" ").map(String::toLong)
            Pair(sum, operands)
        }
    }

    fun createListOfOperators(size: Int, radix: Int): List<List<Char>> {
        val result = mutableListOf<List<Char>>()
        for (i in 0 until radix.toDouble().pow(size.toDouble()).toInt()) {
            result.add(i.toString(radix).padStart(size, '0').map { it })
        }
        return result
    }

    fun calculate(operators: List<Char>, equation: Pair<Long, List<Long>>): Boolean {
        var sum = equation.second[0]
        operators.indices.forEach { index ->
            when (operators[index]) {
                '0' -> sum += equation.second[index + 1]
                '1' -> sum *= equation.second[index + 1]
                // part 2 only
                '2' -> sum = (sum.toString() + equation.second[index + 1].toString()).toLong()
            }
        }
        return sum == equation.first
    }

    fun isValidEquation(equation: Pair<Long, List<Long>>, radix: Int): Boolean {
        val operators = createListOfOperators(equation.second.size - 1, radix)
        operators.forEach {
            val isValid = calculate(it, equation)
            if (isValid) {
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Long {
        val equations = parseInput(input)
        return equations.filter {
            isValidEquation(it, 2)
        }.sumOf {
            it.first
        }
    }

    fun part2(input: List<String>): Long {
        val equations = parseInput(input)
        return equations.filter {
            isValidEquation(it, 3)
        }.sumOf {
            it.first
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day07_test")
    val input = readInput("input/Day07")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 3749L)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 11387L)
    println("Answer part2: " + part2(input))
}
