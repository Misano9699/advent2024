fun nextPoint(i: Int, point: Point, grid: List<List<Int>>, nines: MutableList<Point>): Int {
    if (point.y in grid.indices && point.x in grid[0].indices) {
        if (grid[point.y][point.x] == i) {
            if (i == 9) {
                if (nines.contains(point)) {
                    return 1
                } else {
                    nines.add(point)
                    return 1
                }
            }
            return findNext(i + 1, point, grid, nines)
        } else {
            return 0
        }
    }
    return 0
}

fun findNext(i: Int, point: Point, grid: List<List<Int>>, nines: MutableList<Point>): Int {
    val pointUp = Point(point.x, point.y - 1)
    val pointLeft = Point(point.x - 1, point.y)
    val pointDown = Point(point.x, point.y + 1)
    val pointRight = Point(point.x + 1, point.y)
    var counter = nextPoint(i, pointUp, grid, nines)
    counter += nextPoint(i, pointLeft, grid, nines)
    counter += nextPoint(i, pointDown, grid, nines)
    counter += nextPoint(i, pointRight, grid, nines)
    return counter
}

fun main() {

    fun findZeroes(grid: List<List<Int>>): List<Point> {
        val zeroes = mutableListOf<Point>()
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if (value == 0) {
                    zeroes.add(Point(x, y))
                }
            }
        }
        return zeroes
    }

    fun part1(input: List<String>): Int {
        val grid = toIntMatrix(input)
        val startPoints = findZeroes(grid)
        return startPoints.sumOf { point ->
            val nines = mutableListOf<Point>()
            findNext(1, point, grid, nines)
            nines.size
        }
    }

    fun part2(input: List<String>): Int {
        val grid = toIntMatrix(input)
        val startPoints = findZeroes(grid)
        return startPoints.sumOf { point ->
            val nines = mutableListOf<Point>()
            findNext(1, point, grid, nines)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day10_test")
    val testInput1 = readInput("input/Day10_test_1")
    val input = readInput("input/Day10")

    println("---- PART 1 ----")
    check(part1(testInput1).also { println("Answer test input part1: $it") } == 1)
    check(part1(testInput).also { println("Answer test input part1: $it") } == 36)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 81)
    println("Answer part2: " + part2(input))
}
