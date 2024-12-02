import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Converts string to md5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

private fun openFile(
    day: Int,
    name: String,
) = File("inputs/day%02d".format(day), "$name.txt")

/**
 * Reads lines from the given input txt file.
 */
fun readInput(
    day: Int,
    name: String,
) = openFile(day, name).readText().trim().lines()

fun readInputAsString(
    day: Int,
    name: String,
) = openFile(day, name).readText()

fun readInputAsInts(
    day: Int,
    name: String,
) = readInput(day, name).map { it.toInt() }

fun readInputAsCommaSeparatedInts(
    day: Int,
    name: String,
) = openFile(day, name)
    .readText()
    .split(",")
    .map { it.toInt() }

fun readGraph(
    day: Int,
    name: String,
) = readInput(day, name).fold(mapOf<String, Set<String>>()) { currentGraph, edge ->
    val (fromVertex, toVertex) = edge.split("-")
    val fromNeighbours = currentGraph.getOrDefault(fromVertex, emptySet()) + toVertex
    val toNeighbours = currentGraph.getOrDefault(toVertex, emptySet()) + fromVertex

    currentGraph + mapOf(fromVertex to fromNeighbours, toVertex to toNeighbours)
}.toMap()

fun <A, B> product(
    xs: Sequence<A>,
    ys: Sequence<B>,
): Sequence<Pair<A, B>> = xs.flatMap { x -> ys.map { y -> x to y } }

fun <A, B, C> product(
    xs: Sequence<A>,
    ys: Sequence<B>,
    zs: Sequence<C>,
): Sequence<Triple<A, B, C>> = xs.flatMap { x -> ys.flatMap { y -> zs.map { z -> Triple(x, y, z) } } }

fun <A, B> product(
    xs: Iterable<A>,
    ys: Iterable<B>,
): Sequence<Pair<A, B>> = product(xs.asSequence(), ys.asSequence())

fun <A, B, C> product(
    xs: Iterable<A>,
    ys: Iterable<B>,
    zs: Iterable<C>,
): Sequence<Triple<A, B, C>> = product(xs.asSequence(), ys.asSequence(), zs.asSequence())
