package ingsis.group12.snippetsearcherrunner.runner.input

import jakarta.validation.constraints.NotNull

data class FormatterInput(
    @field:NotNull(message = "name type is missing")
    val content: String?,
    val language: String? = "printscript 1.1",
    @field:NotNull(message = "formatter rules are missing")
    val rules: List<FormatterRuleInput>?,
)
