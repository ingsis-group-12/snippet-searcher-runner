package ingsis.group12.snippetsearcherrunner.runner.service

data class Config(
    val inputPath: String,
    val outputPath: String,
    val lintingRulesPath: String,
    val formattingRulesPath: String,
)
