package ingsis.group12.snippetsearcherrunner.runner.service

import ingsis.group12.snippetsearcherrunner.runner.Runner
import ingsis.group12.snippetsearcherrunner.runner.exception.LanguageNotSupport
import ingsis.group12.snippetsearcherrunner.runner.input.ExecutorInput
import ingsis.group12.snippetsearcherrunner.runner.input.FormatterInput
import ingsis.group12.snippetsearcherrunner.runner.input.LinterInput
import ingsis.group12.snippetsearcherrunner.runner.output.ExecutorOutput
import ingsis.group12.snippetsearcherrunner.runner.output.FormatterOutput
import ingsis.group12.snippetsearcherrunner.runner.output.LinterOutput
import ingsis.group12.snippetsearcherrunner.runner.util.languageSupport
import org.springframework.stereotype.Service

@Service
class RunnerService {
    fun interpret(input: ExecutorInput): ExecutorOutput {
        val version = getVersion(input.language!!)
        return Runner(version).execute(input)
    }

    fun analyze(input: LinterInput): LinterOutput {
        val version = getVersion(input.language!!)
        return Runner(version).analyze(input)
    }

    fun format(input: FormatterInput): FormatterOutput {
        val version = getVersion(input.language!!)
        return Runner(version).format(input)
    }

    private fun extractVersion(input: String): String? {
        val regex = """\d+\.\d+""".toRegex()
        val matchResult = regex.find(input)
        return matchResult?.value
    }

    private fun getVersion(input: String): String {
        val validateLanguage = languageSupport.contains(input)
        val version = extractVersion(input)
        if (!validateLanguage || version == null) {
            throw LanguageNotSupport("Language not supported")
        }
        return version
    }
}
