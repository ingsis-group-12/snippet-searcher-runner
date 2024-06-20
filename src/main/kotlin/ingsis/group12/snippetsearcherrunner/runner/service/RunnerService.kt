package ingsis.group12.snippetsearcherrunner.runner.service

import ingsis.group12.snippetsearcherrunner.runner.Runner
import ingsis.group12.snippetsearcherrunner.runner.input.ExecutorInput
import ingsis.group12.snippetsearcherrunner.runner.input.FormatterInput
import ingsis.group12.snippetsearcherrunner.runner.input.LinterInput
import ingsis.group12.snippetsearcherrunner.runner.output.ExecutorOutput
import ingsis.group12.snippetsearcherrunner.runner.output.FormatterOutput
import ingsis.group12.snippetsearcherrunner.runner.output.LinterOutput
import org.springframework.stereotype.Service

@Service
class RunnerService {
    fun interpret(input: ExecutorInput): ExecutorOutput {
        return Runner(input.version!!).execute(input)
    }

    fun analyze(input: LinterInput): LinterOutput {
        return Runner(input.version!!).analyze(input)
    }

    fun format(input: FormatterInput): FormatterOutput {
        return Runner(input.version!!).format(input)
    }
}
