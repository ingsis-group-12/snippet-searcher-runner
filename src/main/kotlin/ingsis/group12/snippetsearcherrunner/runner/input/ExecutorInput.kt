package ingsis.group12.snippetsearcherrunner.runner.input

import jakarta.validation.constraints.NotNull

data class ExecutorInput(
    @field:NotNull(message = "content type is missing")
    val content: String?,
    val version: String? = "1.1",
    val inputs: List<String>? = emptyList(),
    val env: List<EnvironmentInput>? = emptyList(),
)
