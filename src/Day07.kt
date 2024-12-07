private data class Equation(
    val target: Long,
    val operands: List<Long>,
) {
    private fun canSatisfy(
        ops: List<(Long, Long) -> Long>,
        result: Long,
        i: Int,
        op: (Long, Long) -> Long,
    ): Boolean =
        when {
            i >= operands.size -> result == target
            result > target -> false
            else ->
                ops.any { nextOp ->
                    canSatisfy(ops, op(result, operands[i]), i + 1, nextOp)
                }
        }

    fun isSatisfiable(ops: List<(Long, Long) -> Long>): Boolean = canSatisfy(ops, 0L, 0, Long::plus)
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
        private val PART1_OPS: List<(Long, Long) -> Long> = listOf(Long::plus, Long::times)
        private val PART2_OPS: List<(Long, Long) -> Long> = listOf(Long::plus, Long::times, ::concat)

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

    private fun getSum(ops: List<(Long, Long) -> Long>): Long =
        equations
            .filter { it.isSatisfiable(ops) }
            .sumOf { it.target }

    override fun part1(): Long = getSum(PART1_OPS)

    override fun part2(): Long = getSum(PART2_OPS)
}

fun main() {
    Day07("sample").test(3749L, 11387L)
    Day07("input").run()
}
