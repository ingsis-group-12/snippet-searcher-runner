package ingsis.group12.snippetsearcherrunner.runner.service

import ingsis.group12.snippetsearcherrunner.runner.exception.LanguageNotSupport
import ingsis.group12.snippetsearcherrunner.runner.input.ExecutorInput
import ingsis.group12.snippetsearcherrunner.runner.input.FormatterInput
import ingsis.group12.snippetsearcherrunner.runner.input.FormatterRuleInput
import ingsis.group12.snippetsearcherrunner.runner.input.LinterInput
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File

class RunnerServiceTest {
    private val runnerService = RunnerService()
    private val config =
        Config(
            "src/test/resources/input",
            "src/test/resources/output",
            "src/test/resources/linting-rules",
            "src/test/resources/format-rules",
        )

    @Test
    fun `run interpreter with correct input should return correct output`() {
        val input = File("${config.inputPath}/source_001")
        val output = File("${config.outputPath}/output_001")
        val runnerInput = ExecutorInput(content = input.readText())
        val result = runnerService.interpret(runnerInput)
        assertEquals(output.readText(), result.outputs[0])
    }

    @Test
    fun `run intepreter with readInput and providing input should return correct outputs`() {
        val input = File("${config.inputPath}/source_002")
        val output = File("${config.outputPath}/output_002")
        val runnerInput = ExecutorInput(content = input.readText(), inputs = listOf("snake"))
        val result = runnerService.interpret(runnerInput)
        assertEquals(output.readText(), result.outputs.toString())
    }

    @Test
    fun `run interpret with readInput not providing input should throw exception`() {
        val input = File("${config.inputPath}/source_002")
        val runnerInput = ExecutorInput(content = input.readText())
        val result = runnerService.interpret(runnerInput)
        assertEquals(true, result.error.isNotBlank())
    }

    @Test
    fun `run linting with correct lint rules should return correct output`() {
        val input = File("${config.inputPath}/source_003")
        val output = File("${config.outputPath}/output_003")
        val lintingRules = File("${config.lintingRulesPath}/analyze_rules_001.json").readText()
        val content = Json.parseToJsonElement(lintingRules).jsonObject
        val runnerInput = LinterInput(content = input.readText(), rules = content)
        val result = runnerService.analyze(runnerInput)
        assertEquals(output.readText(), result.output)
    }

    @Test
    fun `run linting with incorrect lint rules should return correct output`() {
        val input = File("${config.inputPath}/source_003")
        val lintingRules = File("${config.lintingRulesPath}/analyze_rules_000.json").readText()
        val content = Json.parseToJsonElement(lintingRules).jsonObject
        val runnerInput = LinterInput(content = input.readText(), rules = content)
        val result = runnerService.analyze(runnerInput)
        println(result.output)
        assertEquals(true, result.output.contains("ReportFailure"))
    }

    @Test
    fun `run formatter with correct format rules should return correct output`() {
        val input = File("${config.inputPath}/source_004")
        val output = File("${config.outputPath}/output_004")
        val formatRules = File("${config.formattingRulesPath}/format_rules.json").readText()
        val rulesWrapper = Json.decodeFromString<RulesWrapper>(formatRules)
        val formatterRules: List<FormatterRuleInputTest> = rulesWrapper.rules
        val runnerInput =
            FormatterInput(content = input.readText(), rules = formatterRules.map { FormatterRuleInput(it.type, it.allowed, it.maxInt) })
        val result = runnerService.format(runnerInput)
        assertEquals(output.readText(), result.output)
    }

    @Test
    fun `run interpreter with not language supported should throw exception`() {
        val input = File("${config.inputPath}/source_005")
        val runnerInput = ExecutorInput(content = input.readText(), language = "not_supported_language")
        assertThrows<LanguageNotSupport> { runnerService.interpret(runnerInput) }
    }
}

@Serializable
data class RulesWrapper(val rules: List<FormatterRuleInputTest>)

@Serializable
data class FormatterRuleInputTest(
    val type: String,
    val allowed: Boolean,
    val maxInt: Int?,
)
