class Day04(
    inputType: String,
) : Day<Int, Int>() {
    private val wordSearch: List<String> = readInput(4, inputType)

    companion object {
        private val DIRECTIONS = product(-1..1, -1..1).filter { (x, y) -> x != 0 || y != 0 }
        private val NEIGHBORING_DIRECTIONS =
            listOf(
                (1 to 1) to (1 to -1),
                (1 to -1) to (-1 to -1),
                (-1 to -1) to (-1 to 1),
                (-1 to 1) to (1 to 1),
            )
    }

    override fun precompute() {
        // no-op
    }

    private fun startsWith(
        word: String,
        y0: Int,
        x0: Int,
        dy: Int,
        dx: Int,
    ): Boolean =
        word.withIndex().all { c ->
            val (y, x) = y0 + dy * c.index to x0 + dx * c.index
            y in wordSearch.indices && x in wordSearch[y].indices && wordSearch[y][x] == c.value
        }

    override fun part1(): Int =
        product(wordSearch.indices, wordSearch[0].indices).sumOf { (y, x) ->
            DIRECTIONS.count { (dy, dx) ->
                startsWith("XMAS", y, x, dy, dx)
            }
        }

    override fun part2(): Int =
        product(wordSearch.indices, wordSearch[0].indices).sumOf { (y, x) ->
            when (wordSearch[y][x]) {
                'A' ->
                    NEIGHBORING_DIRECTIONS.count { (d0, d1) ->
                        val (dy0, dx0) = d0
                        val (dy1, dx1) = d1
                        startsWith("MAS", y - dy0, x - dx0, dy0, dx0) && startsWith("MAS", y - dy1, x - dx1, dy1, dx1)
                    }

                else -> 0
            }
        }
}

fun main() {
    Day04("sample").test(18, 9)
    Day04("input").run()
}
