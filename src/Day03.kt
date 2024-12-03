class Day03(
    inputType: String,
) : Day<Int, Int>() {
    enum class InstructionType {
        Do,
        Dont,
        Mul,
    }

    data class Instruction(
        val type: InstructionType,
        val value: Int,
    )

    companion object {
        private val OP_REGEX = "(mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\))".toRegex()
    }

    private val instructions: List<Instruction> =
        OP_REGEX
            .findAll(readInputAsString(3, inputType))
            .map {
                val (op, l, r) = it.destructured

                val type =
                    when {
                        op.startsWith("mul") -> InstructionType.Mul
                        op.startsWith("don't") -> InstructionType.Dont
                        op.startsWith("do") -> InstructionType.Do
                        else -> error("cannot have other operation")
                    }
                val value =
                    when (type) {
                        InstructionType.Mul -> l.toInt() * r.toInt()
                        else -> 0
                    }

                Instruction(type, value)
            }.toList()

    override fun precompute() {
        // no-op
    }

    override fun part1(): Int =
        instructions.sumOf {
            when (it.type) {
                InstructionType.Mul -> it.value
                else -> 0
            }
        }

    override fun part2(): Int =
        instructions
            .fold(true to 0) { (running, sum), instr ->
                var (running, sum) = running to sum
                when (instr.type) {
                    InstructionType.Do -> {
                        running = true
                    }

                    InstructionType.Dont -> {
                        running = false
                    }

                    InstructionType.Mul -> {
                        if (running) {
                            sum += instr.value
                        }
                    }
                }

                running to sum
            }.second
}

fun main() {
    Day03("sample").test(161)
    Day03("sample2").test(null, 48)
    Day03("input").run()
}
