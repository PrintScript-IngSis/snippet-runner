package printscript.group13.snippetrunner.input

import org.example.formatter.FormattingRules


data class FormatterInput(
    val code: String,
    val version: String = "1.1",
    val rules: String = "src/main/resources/FormatterRules.json"//path to rules file
)
