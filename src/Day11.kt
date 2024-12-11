fun main() {

    fun mutateStones(stones: List<Long>): List<Long> {
        val newStones = mutableListOf<Long>()
        for (stone in stones) {
            val stoneString = stone.toString()
            when {
                stone == 0L -> newStones.add(1L)
                stoneString.length % 2 == 0 -> {
                    newStones.add(stoneString.substring(0, stoneString.length / 2).toLong())
                    newStones.add(stoneString.substring(stoneString.length / 2).toLong())
                }

                else -> newStones.add(stone * 2024L)
            }
        }
        return newStones
    }

    fun part1(input: List<String>): Int {
        var stones = toIntMatrix(input, " ")[0].map { it.toLong() }
        (0..<25).forEach { _ ->
            stones = mutateStones(stones)
        }
        return stones.size
    }

    fun part2(input: List<String>): Int {
        var stones = toIntMatrix(input, " ")[0].map { it.toLong() }
        (0..<75).forEach { _ ->
            stones = mutateStones(stones)
        }
        return stones.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day11_test")
    val input = readInput("input/Day11")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 55312)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    println("Answer part2: " + part2(input))
}
