package ingsis.group12.snippetsearcherrunner.runner.util

import edu.austral.ingsis.gradle.formatter.rule.ComposeRule
import edu.austral.ingsis.gradle.formatter.rule.Rule
import edu.austral.ingsis.gradle.formatter.rule.Rules
import edu.austral.ingsis.gradle.formatter.rule.adapter.RuleAdapter
import edu.austral.ingsis.gradle.formatter.rule.adapter.RuleJson
import edu.austral.ingsis.gradle.formatter.rule.adapter.context.Block
import edu.austral.ingsis.gradle.formatter.rule.adapter.context.Default
import edu.austral.ingsis.gradle.formatter.rule.adapter.context.RuleContext
import ingsis.group12.snippetsearcherrunner.runner.input.FormatterRuleInput

val mapOfFormatterRules =
    mapOf(
        "spaceBeforeColon" to Default(),
        "spaceAfterColon" to Default(),
        "spaceAroundEqual" to Default(),
        "newlineBeforePrintln" to Default(),
        "spacesAfterStartLine" to Block(),
    )

fun getFormatterRuleType(type: String): RuleContext? {
    return mapOfFormatterRules[type]
}

fun getRules(rules: List<FormatterRuleInput>): Rules {
    val defaultRules = mutableListOf<Rule>()
    val blockRules = mutableListOf<Rule>()
    for (rule in rules) {
        val ruleType = getFormatterRuleType(rule.type)
        if (ruleType != null) {
            if (ruleType is Default) {
                defaultRules.add(RuleAdapter().adapt(RuleJson(rule.type, rule.allowed, rule.maxInt)))
            } else if (ruleType is Block) {
                blockRules.add(RuleAdapter().adapt(RuleJson(rule.type, rule.allowed, rule.maxInt)))
            }
        }
    }
    return Rules(ComposeRule(defaultRules), ComposeRule(blockRules))
}
