import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun toCharMatrix(input: List<String>): List<List<Char>> =
    input.map { row ->
        row.map { it }
    }

fun toIntMatrix(input: List<String>, delimiter: String): List<List<Int>> {
    return input.map { line ->
        line.split(delimiter).map { it.toInt() } }
}

fun toIntMatrix(input: List<String>): List<List<Int>> {
    return input.map { line ->
        line.map { it.digitToIntOrNull()!! } }
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT
}