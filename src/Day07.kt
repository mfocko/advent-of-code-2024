private data class Equation(
    val target: Long,
    val operands: List<Long>,
) {
    private fun canSatisfy(
        ops: List<(Long, Long) -> Long>,
        result: Long,
        i: Int,
        op: (Long, Long) -> Long,
    ): Boolean {
        // used all operands
        if (i >= operands.size) {
            return result == target
        }

        // overshot
        if (result > target) {
            return false
        }

        return ops.any { nextOp ->
            canSatisfy(ops, op(result, operands[i]), i + 1, nextOp)
        }
    }

    fun isSatisfiable(ops: List<(Long, Long) -> Long>): Boolean = canSatisfy(ops, 0L, 0, Long::plus)

    val satisfiable: Boolean
        get() = canSatisfy(listOf(Long::plus, Long::times), 0L, 0, Long::plus)
}

private fun String.toEquation(): Equation =
    split(": ").let { (target, operands) ->
        Equation(target.toLong(), operands.split(" ").map { it.toLong() })
    }

class Day07(
    inputType: String,
) : Day<Long, Long>() {
    private val equations: List<Equation> = readInput(7, inputType).map { it.toEquation() }.toList()

    companion object {
        fun concat(
            x: Long,
            y: Long,
        ): Long {
            val shift =
                let {
                    var result = 1

                    var yy = y
                    while (yy > 0) {
                        yy /= 10
                        result *= 10
                    }

                    return@let result
                }

            return x * shift + y
        }
    }

    override fun precompute() {
        // no-op
    }

    override fun part1(): Long = equations.filter { it.satisfiable }.sumOf { it.target }

    override fun part2(): Long = equations.filter { it.isSatisfiable(listOf(Long::plus, Long::times, ::concat)) }.sumOf { it.target }
}

fun main() {
    Day07("sample").test(3749L, 11387L)
    Day07("input").run()
}
