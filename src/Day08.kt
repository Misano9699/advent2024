fun main() {

    fun calculateAntinodes(grid: List<List<Char>>, node: Node, n: Node): List<Node> {
        val antinodes = mutableListOf<Node>()
        val dx = node.x - n.x
        val dy = node.y - n.y
        val x1: Int = node.x + dx
        val x2: Int = n.x - dx
        val y1: Int = node.y + dy
        val y2: Int = n.y - dy
        if (x1 >= 0 && x1 < grid[0].size && y1 >= 0 && y1 < grid.size) {
            antinodes.add(Node(x1, y1, '.'))
        }
        if (x2 >= 0 && x2 < grid[0].size && y2 >= 0 && y2 < grid.size) {
            antinodes.add(Node(x2, y2, '.'))
        }
        return antinodes
    }

    fun calculateAntinodesExtended(grid: List<List<Char>>, node: Node, n: Node): List<Node> {
        val antinodes = mutableListOf<Node>()
        val dx = node.x - n.x
        val dy = node.y - n.y
        var x1: Int = node.x + dx
        var y1: Int = node.y + dy
        var x2: Int = n.x - dx
        var y2: Int = n.y - dy
        while (y1 in grid.indices && x1 in grid[y1].indices) {
            antinodes.add(Node(x1, y1, '.'))
            x1 += dx
            y1 += dy
        }
        while (y2 in grid.indices && x2 in grid[y2].indices) {
            antinodes.add(Node(x2, y2, '.'))
            x2 -= dx
            y2 -= dy
        }
        return antinodes
    }

    fun calculateAllAntinodes(nodes: MutableList<Node>, antinodes: MutableSet<Node>, grid: List<List<Char>>) {
        (0..<nodes.size - 1).forEach { i ->
            val node = nodes[i]
            (i + 1..<nodes.size).forEach { j ->
                val n = nodes[j]
                if (node.node == n.node) {
                    val calculatedAntinodes = calculateAntinodes(grid, node, n)
                    antinodes.addAll(calculatedAntinodes)
                }
            }
        }
    }

    fun calculateAllAntinodesExtended(nodes: MutableList<Node>, antinodes: MutableSet<Node>, grid: List<List<Char>>) {
        (0..<nodes.size - 1).forEach { i ->
            val node = nodes[i]
            (i + 1..<nodes.size).forEach { j ->
                val n = nodes[j]
                if (node.node == n.node) {
                    val calculatedAntinodes = calculateAntinodesExtended(grid, node, n)
                    antinodes.addAll(calculatedAntinodes)
                    antinodes.add(Node(node.x, node.y, '.'))
                    antinodes.add(Node(n.x, n.y, '.'))
                }
            }
        }
    }

    fun addNodes(grid: List<List<Char>>): MutableList<Node> {
        val nodes = mutableListOf<Node>()
        grid.indices.forEach { y ->
            grid[y].indices.forEach { x ->
                if (grid[y][x] != '.') {
                    nodes.add(Node(x, y, grid[y][x]))
                }
            }
        }
        return nodes
    }

    fun part1(input: List<String>): Int {
        val grid = toCharMatrix(input)
        val antinodes = mutableSetOf<Node>()
        val nodes = addNodes(grid)
        calculateAllAntinodes(nodes, antinodes, grid)
        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val grid = toCharMatrix(input)
        val antinodes = mutableSetOf<Node>()
        val nodes = addNodes(grid)
        calculateAllAntinodesExtended(nodes, antinodes, grid)
        return antinodes.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day08_test")
    val input = readInput("input/Day08")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 14)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 34)
    println("Answer part2: " + part2(input))
}

data class Node(val x: Int, val y: Int, val node: Char)