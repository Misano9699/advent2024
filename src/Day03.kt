fun main() {

    val regex = "mul\\([0-9]+\\,[0-9]+\\)".toRegex()
    val regex2 = "(do\\(\\))|(don't\\(\\))|(mul\\([0-9]+\\,[0-9]+\\))".toRegex()
    fun multiply(value: String): Int {
        val numbers = value.substringAfter("mul(")
        val firstNumber = numbers.substringBefore(",").toInt()
        val secondNumber = numbers.substringAfter(",").substringBefore(")").toInt()
        return firstNumber * secondNumber
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { row ->
            regex.findAll(row).sumOf { matchResult ->
                multiply(matchResult.value)
            }
        }
    }

    fun part2(input: List<String>): Int {
        val row = input.joinToString("")
        val findAll = regex2.findAll(row)
        var calculate = true
        return findAll.sumOf {
            when {
                it.value.startsWith("don't()") -> {
                    calculate = false
                    0
                }

                it.value.startsWith("do()") -> {
                    calculate = true
                    0
                }

                else -> if (calculate) {
                    multiply(it.value)
                } else {
                    0
                }
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("input/Day03_test")
    val testInput2 = readInput("input/Day03_test_2")
    val input = readInput("input/Day03")

    println("---- PART 1 ----")
    check(part1(testInput1).also { println("Answer test input part1: $it") } == 161)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput2).also { println("Answer test input part2: $it") } == 48)
    println("Answer part2: " + part2(input))
}
