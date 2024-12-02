class Day01(
    inputType: String,
) : Day<Int, Int>() {
    private val left: MutableList<Int>
    private val right: MutableList<Int>

    init {
        val (left, right) =
            readInput(
                1,
                inputType,
            ).fold(mutableListOf<Int>() to mutableListOf<Int>()) { (left, right), line ->
                val nums = line.split(Regex("\\s+"))

                left.add(nums[0].toInt())
                right.add(nums[1].toInt())

                left to right
            }

        this.left = left
        this.right = right
    }

    override fun precompute() {
        left.sort()
        right.sort()
    }

    override fun part1(): Int = left.zip(right).sumOf { (l, r) -> maxOf(l - r, r - l) }

    override fun part2(): Int {
        val freqs =
            buildMap<Int, Int> {
                right.forEach { it ->
                    put(it, 1 + getOrDefault(it, 0))
                }
            }

        return left.sumOf { it -> it * freqs.getOrDefault(it, 0) }
    }
}

fun main() {
    Day01("sample").test(11, 31)
    Day01("input").run()
}
