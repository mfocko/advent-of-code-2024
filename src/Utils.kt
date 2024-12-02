import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Converts string to MD5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Opens file for a requested day with a given name. Useful for parametrizing
 * the input files, e.g., samples or challenge inputs.
 *
 * @param day Day of the AoC challenge
 * @param name Name of the file, e.g., `input` or `sample`
 * @return `File` object
 */
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

/**
 * Reads input as one string.
 */
fun readInputAsString(
    day: Int,
    name: String,
) = openFile(day, name).readText()

/**
 * Reads input as line-separated integers.
 */
fun readInputAsInts(
    day: Int,
    name: String,
) = readInput(day, name).map { it.toInt() }

/**
 * Reads input as one line of comma-separated integers.
 */
fun readInputAsCommaSeparatedInts(
    day: Int,
    name: String,
) = openFile(day, name)
    .readText()
    .split(",")
    .map { it.toInt() }

/**
 * Reads input representing a graph in a form of
 *
 *     u₁-v₁
 *     u₂-v₂
 *     u₃-v₃
 *
 * where each edge resides on just one line.
 */
fun readGraph(
    day: Int,
    name: String,
) = readInput(day, name)
    .fold(mapOf<String, Set<String>>()) { currentGraph, edge ->
        val (fromVertex, toVertex) = edge.split("-")
        val fromNeighbours = currentGraph.getOrDefault(fromVertex, emptySet()) + toVertex
        val toNeighbours = currentGraph.getOrDefault(toVertex, emptySet()) + fromVertex

        currentGraph + mapOf(fromVertex to fromNeighbours, toVertex to toNeighbours)
    }.toMap()

/**
 * Constructs product of two sequences.
 *
 * @param xs First sequence
 * @param ys Second sequence
 * @return Sequence of pairs with elements from `xs` and `ys`
 */
fun <A, B> product(
    xs: Sequence<A>,
    ys: Sequence<B>,
): Sequence<Pair<A, B>> = xs.flatMap { x -> ys.map { y -> x to y } }

/**
 * Constructs product of three sequences.
 *
 * @param xs First sequence
 * @param ys Second sequence
 * @param zs Third sequence
 * @return Sequence of triples with elements from `xs`, `ys` and `zs`
 */
fun <A, B, C> product(
    xs: Sequence<A>,
    ys: Sequence<B>,
    zs: Sequence<C>,
): Sequence<Triple<A, B, C>> = xs.flatMap { x -> ys.flatMap { y -> zs.map { z -> Triple(x, y, z) } } }

/**
 * Constructs product of two iterables.
 *
 * @param xs First iterable
 * @param ys Second iterable
 * @return Sequence of pairs with elements from `xs` and `ys`
 */
fun <A, B> product(
    xs: Iterable<A>,
    ys: Iterable<B>,
): Sequence<Pair<A, B>> = product(xs.asSequence(), ys.asSequence())

/**
 * Constructs product of three iterables.
 *
 * @param xs First iterable
 * @param ys Second iterable
 * @param zs Third iterable
 * @return Sequence of triples with elements from `xs`, `ys` and `zs`
 */
fun <A, B, C> product(
    xs: Iterable<A>,
    ys: Iterable<B>,
    zs: Iterable<C>,
): Sequence<Triple<A, B, C>> = product(xs.asSequence(), ys.asSequence(), zs.asSequence())
