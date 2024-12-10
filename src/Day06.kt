fun goLeft(point: Point, map: List<List<Char>>, path: MutableSet<Point>) {
    when {
        point.x - 1 < 0 -> {}
        map[point.y][point.x - 1] == '#' -> {
            goUp(point, map, path)
        }

        else -> {
            val nextPoint = Point(point.x - 1, point.y)
            path.add(nextPoint)
            goLeft(nextPoint, map, path)
        }
    }
}

fun goDown(point: Point, map: List<List<Char>>, path: MutableSet<Point>) {
    when {
        point.y + 1 >= map.size -> {}
        map[point.y + 1][point.x] == '#' -> {
            goLeft(point, map, path)
        }

        else -> {
            val nextPoint = Point(point.x, point.y + 1)
            path.add(nextPoint)
            goDown(nextPoint, map, path)
        }
    }
}

fun goRight(point: Point, map: List<List<Char>>, path: MutableSet<Point>) {
    when {
        point.x + 1 >= map[point.y].size -> {}
        map[point.y][point.x + 1] == '#' -> {
            goDown(point, map, path)
        }

        else -> {
            val nextPoint = Point(point.x + 1, point.y)
            path.add(nextPoint)
            goRight(nextPoint, map, path)
        }
    }
}

fun goUp(point: Point, map: List<List<Char>>, path: MutableSet<Point>) {
    when {
        point.y - 1 < 0 -> {}
        map[point.y - 1][point.x] == '#' -> {
            goRight(point, map, path)
        }

        else -> {
            val nextPoint = Point(point.x, point.y - 1)
            path.add(nextPoint)
            goUp(nextPoint, map, path)
        }
    }
}

fun main() {

    fun findStart(map: List<List<Char>>): Point {
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == '^') {
                    return Point(x, y)
                }
            }
        }
        throw IllegalStateException("No start point found")
    }

    fun part1(input: List<String>): Int {
        val map = toCharMatrix(input)
        val start = findStart(map)
        val path = mutableSetOf(start)
        goUp(start, map, path)
        return path.size
    }

    fun addToken(x: Int, y: Int, map: List<List<Char>>): MutableList<MutableList<Char>> {
        val newMap = map.map { row ->
            row.map { it }.toMutableList()
        }.toMutableList()
        if (newMap[y][x] == '.') {
            newMap[y][x] = '#'
        }
        return newMap
    }

    fun traverseMap(start: Point, map: MutableList<MutableList<Char>>): Boolean {
        val path = mutableSetOf(Pair(start, Direction.UP))
        var direction = Direction.UP
        var nextPoint = start
        while (true) {
            when (direction) {
                Direction.UP -> {
                    when {
                        nextPoint.y - 1 < 0 -> {
                            return false
                        }

                        map[nextPoint.y - 1][nextPoint.x] == '#' -> {
                            direction = Direction.RIGHT
                            val nextPath = Pair(nextPoint, direction)
                            if (path.contains(nextPath)) return true
                            path.add(nextPath)
                        }

                        else -> {
                            nextPoint = Point(nextPoint.x, nextPoint.y - 1)
                            path.add(Pair(nextPoint, Direction.UP))
                        }
                    }
                }

                Direction.DOWN -> {
                    when {
                        nextPoint.y + 1 >= map.size -> {
                            return false
                        }

                        map[nextPoint.y + 1][nextPoint.x] == '#' -> {
                            direction = Direction.LEFT
                            val nextPath = Pair(nextPoint, direction)
                            if (path.contains(nextPath)) return true
                            path.add(nextPath)
                        }

                        else -> {
                            nextPoint = Point(nextPoint.x, nextPoint.y + 1)
                            path.add(Pair(nextPoint, Direction.DOWN))
                        }
                    }
                }

                Direction.LEFT -> {
                    when {
                        nextPoint.x - 1 < 0 -> {
                            return false
                        }

                        map[nextPoint.y][nextPoint.x - 1] == '#' -> {
                            val nextPath = Pair(nextPoint, Direction.UP)
                            if (path.contains(nextPath)) return true
                            path.add(nextPath)
                            direction = Direction.UP
                        }

                        else -> {
                            nextPoint = Point(nextPoint.x - 1, nextPoint.y)
                            path.add(Pair(nextPoint, Direction.LEFT))
                        }
                    }
                }

                Direction.RIGHT -> {
                    when {
                        nextPoint.x + 1 >= map[nextPoint.y].size -> {
                            return false
                        }

                        map[nextPoint.y][nextPoint.x + 1] == '#' -> {
                            val nextPath = Pair(nextPoint, Direction.DOWN)
                            if (path.contains(nextPath)) return true
                            path.add(nextPath)
                            direction = Direction.DOWN
                        }

                        else -> {
                            nextPoint = Point(nextPoint.x + 1, nextPoint.y)
                            path.add(Pair(nextPoint, Direction.RIGHT))
                        }
                    }
                }

                else -> return false
            }

        }

    }

    fun part2(input: List<String>): Int {
        val map = toCharMatrix(input)
        val start = findStart(map)
        var counter = 0
        for (y in map.indices) {
            for (x in map[y].indices) {
                val newMap = addToken(x, y, map)
                val cycle = traverseMap(start, newMap)
                if (cycle) counter++

            }
        }
        return counter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day06_test")
    val input = readInput("input/Day06")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 41)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 6)
    println("Answer part2: " + part2(input))
}

data class Point(val x: Int, val y: Int)
