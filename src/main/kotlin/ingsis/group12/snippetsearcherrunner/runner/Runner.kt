package ingsis.group12.snippetsearcherrunner.runner

import edu.austral.ingsis.gradle.common.ast.AST
import edu.austral.ingsis.gradle.formatter.createDefaultFormatter
import edu.austral.ingsis.gradle.interpreter.ComposeInterpreter
import edu.austral.ingsis.gradle.interpreter.util.PrinterCollector
import edu.austral.ingsis.gradle.iterator.LexerIterator
import edu.austral.ingsis.gradle.iterator.ParserIterator
import edu.austral.ingsis.gradle.lexer.director.LexerDirector
import edu.austral.ingsis.gradle.parser.InputContext
import edu.austral.ingsis.gradle.parser.factory.createComposeParser
import edu.austral.ingsis.gradle.parser.impl.ProgramNodeParser
import edu.austral.ingsis.gradle.sca.ReportSuccess
import edu.austral.ingsis.gradle.sca.adapter.StringToJsonAdapter
import edu.austral.ingsis.gradle.sca.factory.createComposeAnalyzer
import ingsis.group12.snippetsearcherrunner.runner.input.ExecutorInput
import ingsis.group12.snippetsearcherrunner.runner.input.FormatterInput
import ingsis.group12.snippetsearcherrunner.runner.input.LinterInput
import ingsis.group12.snippetsearcherrunner.runner.output.ExecutorOutput
import ingsis.group12.snippetsearcherrunner.runner.output.FormatterOutput
import ingsis.group12.snippetsearcherrunner.runner.output.LinterOutput
import ingsis.group12.snippetsearcherrunner.runner.util.RunnerEnvReader
import ingsis.group12.snippetsearcherrunner.runner.util.RunnerInputReader
import ingsis.group12.snippetsearcherrunner.runner.util.getRules
import org.slf4j.LoggerFactory

class Runner(private val version: String) {
    private val logger = LoggerFactory.getLogger(Runner::class.java)

    fun execute(input: ExecutorInput): ExecutorOutput {
        try {
            logger.info("Interpreting code ")
            val lexer = LexerDirector().createComposeLexer(version)
            logger.info("Lexing...")
            val lexerIterator = LexerIterator(lexer, input.content!!.byteInputStream().bufferedReader())
            logger.info("Parsing...")
            val composeParser = createComposeParser()
            val parserIterator = ParserIterator(lexerIterator, composeParser)
            val collector = PrinterCollector()
            logger.info("Interpreting...")
            var interpreter =
                ComposeInterpreter(
                    emitter = collector,
                    envReader = RunnerEnvReader(input.env!!),
                    inputReader = RunnerInputReader(input.inputs!!),
                )
            while (parserIterator.hasNext()) {
                val ast = parserIterator.next()
                if (ast != null) {
                    interpreter = interpreter.interpretAndUpdateContext(ast)
                }
            }
            return ExecutorOutput(collector.getPrintedValues(), "")
        } catch (e: Exception) {
            logger.error(e.message)
            return ExecutorOutput(listOf(), e.message.toString())
        }
    }

    fun analyze(input: LinterInput): LinterOutput {
        try {
            logger.info("Analyzing code")
            val ast = createAstNode(input.content!!)
            val rule = StringToJsonAdapter().adapt(input.rules!!)
            val composeLinter = createComposeAnalyzer()
            logger.info("Analyzing...")
            return when (val output = composeLinter.analyze(ast, listOf(rule))) {
                ReportSuccess -> LinterOutput("ReportSuccess", "")
                else -> LinterOutput("ReportFailure", output.toString())
            }
        } catch (e: Exception) {
            logger.error(e.message)
            return LinterOutput("", e.message.toString())
        }
    }

    fun format(input: FormatterInput): FormatterOutput {
        try {
            logger.info("Formatting code")
            val ast = createAstNode(input.content!!)
            val rules = getRules(input.rules!!)
            logger.info("Formatting...")
            val formatter = createDefaultFormatter()
            val formatted = formatter.format(ast, rules)
            return FormatterOutput(formatted, "")
        } catch (e: Exception) {
            logger.error(e.message)
            return FormatterOutput(input.content!!, e.message.toString())
        }
    }

    private fun createAstNode(input: String): AST {
        val lexer = LexerDirector().createComposeLexer(version)
        println("Lexing...")
        val tokenList = lexer.splitIntoTokens(input)
        val parser = ProgramNodeParser()
        println("Parsing...")
        return parser.parse(InputContext(tokenList)).first
    }
}
