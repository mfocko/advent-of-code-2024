class Day05(
    inputType: String,
) : Day<Int, Int>() {
    private val rules: Map<Int, MutableSet<Int>>
    private val updates: List<List<Int>>

    companion object {
    }

    init {
        val (rules, updates) = readInputAsString(5, inputType).split("\n\n")

        this.rules =
            buildMap {
                rules
                    .lineSequence()
                    .map { line ->
                        line.split("|").map(String::toInt).let { it[0] to it[1] }
                    }.forEach { (before, after) ->
                        getOrPut(after) { mutableSetOf() }.add(before)
                    }
            }
        this.updates = updates.lineSequence().map { line -> line.split(",").map(String::toInt).toList() }.toList()
    }

    override fun precompute() {
        // no-op
    }

    private fun isCorrect(update: List<Int>): Boolean =
        update.withIndex().reversed().all { p ->
            rules.getOrDefault(p.value, mutableSetOf()).all { preceding -> update.lastIndexOf(preceding) < p.index }
        }

    override fun part1(): Int = updates.filter(::isCorrect).sumOf { it[it.size / 2] }

    private fun fix(update: List<Int>): List<Int> = update

    override fun part2(): Int = updates.filter { !isCorrect(it) }.map { fix(it) }.sumOf { it[it.size / 2] }
}

fun main() {
    Day05("sample").test(143, 123)
    Day05("input").run()
}
