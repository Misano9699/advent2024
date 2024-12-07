fun main() {

    val xmas = mapOf(Pair('X', 'M'),Pair('M', 'A'), Pair('A', 'S'))

    fun parseInput(input: List<String>): List<List<Char>> {
        return input.map { it.toCharArray().toList() }
    }

    fun findChar(direction: Direction, char: Char, i: Int, j: Int, matrix: List<List<Char>>): Int {
        // stop condities
        if (i < 0 || i >= matrix.size || j < 0 || j >= matrix[i].size) {
            return 0
        }
        // ok, we zitten binnen de matrix, dus gaan we verder

        return when {
            char == 'S' && matrix[i][j] == 'S' -> {
                1
            }
            char == 'S'  -> 0
            else -> {
                if (char == matrix[i][j]) {
                    return when (direction) {
                        Direction.UPLEFT -> findChar(Direction.UPLEFT, xmas.get(char)!!, i-1, j-1, matrix)
                        Direction.UP -> findChar(Direction.UP, xmas.get(char)!!, i-1, j, matrix)
                        Direction.UPRIGHT -> findChar(Direction.UPRIGHT, xmas.get(char)!!, i-1, j+1, matrix)
                        Direction.LEFT -> findChar(Direction.LEFT, xmas.get(char)!!, i, j-1, matrix)
                        Direction.RIGHT -> findChar(Direction.RIGHT, xmas.get(char)!!, i, j+1, matrix)
                        Direction.DOWNLEFT -> findChar(Direction.DOWNLEFT, xmas.get(char)!!, i+1, j-1, matrix)
                        Direction.DOWN -> findChar(Direction.DOWN, xmas.get(char)!!, i+1, j, matrix)
                        Direction.DOWNRIGHT -> findChar(Direction.DOWNRIGHT, xmas.get(char)!!, i+1, j+1, matrix)
                    }
                }
                return 0
            }
        }
    }

    fun part1(input: List<String>): Int {
        val matrix = parseInput(input)
        var counter = 0
        matrix.indices.forEach { i ->
            matrix[i].indices.forEach { j ->
                counter += findChar(Direction.UPLEFT, 'X', i,j, matrix)
                counter += findChar(Direction.UP, 'X', i,j, matrix)
                counter += findChar(Direction.UPRIGHT, 'X', i,j, matrix)
                counter += findChar(Direction.LEFT, 'X', i,j, matrix)
                counter += findChar(Direction.RIGHT, 'X', i,j, matrix)
                counter += findChar(Direction.DOWNLEFT, 'X', i,j, matrix)
                counter += findChar(Direction.DOWN, 'X', i,j, matrix)
                counter += findChar(Direction.DOWNRIGHT, 'X', i,j, matrix)
            }
        }
        return counter
    }

    fun findMAS(i: Int, j: Int, matrix: List<List<Char>>): Int {
        // stop condities
        if (i == 0 || i == matrix.size-1 || j == 0 || j == matrix[i].size - 1) {
            return 0
        }
        // check if corners are m, s
        return when {
            matrix[i-1][j-1] == 'M' && matrix[i+1][j+1] == 'S' -> {
              println("Found M - S")
              if ((matrix[i+1][j-1] == 'M' && matrix[i-1][j+1] == 'S') ||
                  (matrix[i-1][j+1] == 'M' && matrix[i+1][j-1] == 'S'))  {
                  1
              } else {
                  0
              }
            }
            matrix[i+1][j+1] == 'M' && matrix[i-1][j-1] == 'S' -> {
              println("Found S - M")
                if ((matrix[i+1][j-1] == 'M' && matrix[i-1][j+1] == 'S') ||
                    (matrix[i-1][j+1] == 'M' && matrix[i+1][j-1] == 'S'))  {
                    1
                } else {
                    0
                }
            }
            else -> 0
        }
    }

    fun part2(input: List<String>): Int {
        val matrix = parseInput(input)
        var counter = 0
        matrix.indices.forEach { i ->
            matrix[i].indices.forEach { j ->
                if ('A' == matrix[i][j]) {
                    println("A op $i $j")
                    counter += findMAS(i, j, matrix)
                }
            }
        }
        return counter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day04_test")
    val input = readInput("input/Day04")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 18)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 9)
    println("Answer part2: " + part2(input))
}