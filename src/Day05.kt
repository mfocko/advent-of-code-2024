class Day05(
    inputType: String,
) : Day<Int, Int>() {
    private val rules: Map<Int, MutableSet<Int>>
    private val updates: List<List<Int>>

    companion object {
        val UNVISITED = 0
        val OPEN = 1
        val DONE = 2
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
                        getOrPut(before) { mutableSetOf() }.add(after)
                    }
            }
        this.updates = updates.lineSequence().map { line -> line.split(",").map(String::toInt).toList() }.toList()
    }

    private fun printGraph() {
        println("digraph G {")

        rules.forEach { u, vs ->
            vs.forEach { v ->
                println("\t$u -> $v")
            }
        }

        println("}")
    }

    override fun precompute() {
        // no-op
    }

    private fun isCorrect(update: List<Int>): Boolean =
        update.withIndex().all { p ->
            rules
                .getOrDefault(p.value, mutableSetOf())
                .all { succeeding -> update.indexOf(succeeding).let { it == -1 || it > p.index } }
        }

    override fun part1(): Int = updates.filter(::isCorrect).sumOf { it[it.size / 2] }

    // Toposort
    private fun fix(xs: Set<Int>): List<Int> {
        val ordering = mutableListOf<Int>()
        val state = mutableMapOf<Int, Int>()

        fun visit(u: Int) {
            if (state.getOrDefault(u, UNVISITED) == DONE) {
                return
            }
            check(state.getOrDefault(u, UNVISITED) != OPEN) { "We have found a loop ending in $u" }

            state[u] = OPEN
            // ‹.intersect()›: Recursively search only the interesting successors
            rules.getOrDefault(u, emptySet()).intersect(xs).forEach { visit(it) }

            state[u] = DONE
            ordering.add(u)
        }
        xs.forEach { visit(it) }

        return ordering
    }

    override fun part2(): Int = updates.filter { !isCorrect(it) }.map { fix(it.toSet()) }.sumOf { it[it.size / 2] }
}

fun main() {
    Day05("sample").test(143, 123)
    Day05("input").run()
}
