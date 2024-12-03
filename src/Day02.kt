class Day02(
    inputType: String,
) : Day<Int, Int>() {
    private val reports: List<List<Int>> =
        readInput(2, inputType)
            .map { line ->
                line.split(" ").map { it.toInt() }
            }.toList()

    override fun precompute() {
        // no-op
    }

    private fun isCorrect(report: List<Int>): Boolean {
        val isIncreasing = report[0] < report[1]
        return report.windowed(2).fold(true) { acc, (x, y) ->
            acc && maxOf(x - y, y - x) in 1..3 && ((isIncreasing && x < y) || (!isIncreasing && x > y))
        }
    }

    override fun part1(): Int = reports.count(::isCorrect)

    override fun part2(): Int =
        reports.count { report ->
            report.indices.any { skip ->
                isCorrect(report.filterIndexed { idx, _ -> idx != skip })
            }
        }
}

fun main() {
    Day02("sample").test(2, 4)
    Day02("input").run()
}
