fun main() {

    fun stonesToMap(stones: List<Long>): Map<Long, Long> {
        return stones.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
    }

    fun mutateMap(map: Map<Long, Long>): Map<Long, Long> {
        val newMap = mutableMapOf<Long, Long>()
        for ((stone, count) in map) {
            val stoneString = stone.toString()
            when {
                stone == 0L -> newMap[1L] = when {
                    newMap[1L] != null -> newMap[1L]!! + count
                    else -> count
                }

                stoneString.length % 2 == 0 -> {
                    val stoneLeft = stoneString.substring(0, stoneString.length / 2).toLong()
                    val stoneRight = stoneString.substring(stoneString.length / 2).toLong()
                    newMap[stoneLeft] =
                        when {
                            newMap.containsKey(stoneLeft) -> newMap[stoneLeft]!! + count
                            else -> count
                        }
                    newMap[stoneRight] =
                        when {
                            newMap.containsKey(stoneRight) -> newMap[stoneRight]!! + count
                            else -> count
                        }
                }

                else -> {
                    val number = stone * 2024L
                    newMap[number] =
                        when {
                            newMap.containsKey(number) -> newMap[number]!! + count
                            else -> count
                        }
                }
            }
        }
        return newMap
    }

    fun part1(input: List<String>): Long {
        val stones = toIntMatrix(input, " ")[0].map { it.toLong() }
        var map = stonesToMap(stones)
        (0..<25).forEach { _ ->
            map = mutateMap(map)
        }
        return map.values.sum()
    }

    fun part2(input: List<String>): Long {
        val stones = toIntMatrix(input, " ")[0].map { it.toLong() }
        var map = stonesToMap(stones)
        (0..<75).forEach { _ ->
            map = mutateMap(map)
        }
        return map.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day11_test")
    val input = readInput("input/Day11")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 55312L)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    println("Answer part2: " + part2(input))
}
