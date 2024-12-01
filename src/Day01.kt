fun parseInput(inputType: String): Pair<MutableList<Int>, MutableList<Int>> =
    readInput(1, inputType).fold(mutableListOf<Int>() to mutableListOf<Int>()) { (left, right), line ->
        val nums = line.split(Regex("\\s+"))

        left.add(nums[0].toInt())
        right.add(nums[1].toInt())

        left to right
    }

fun precompute(input: Pair<MutableList<Int>, MutableList<Int>>) {
    input.first.sort()
    input.second.sort()
}

fun part1(input: Pair<List<Int>, List<Int>>): Int =
    input.first.zip(input.second).sumOf { (l, r) -> maxOf(l - r, r - l) }

fun part2(input: Pair<List<Int>, List<Int>>): Int {
    val (left, right) = input
    val freqs = buildMap<Int, Int> {
        right.forEach { it ->
            put(it, 1 + getOrDefault(it, 0))
        }
    }

    return left.sumOf { it -> it * freqs.getOrDefault(it, 0) }
}

fun main() {
    // Test of sample
    val testInput = parseInput("sample")
    precompute(testInput)

    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Challenge input
    val input = parseInput("input")
    precompute(input)

    part1(input).println()
    part2(input).println()
}
