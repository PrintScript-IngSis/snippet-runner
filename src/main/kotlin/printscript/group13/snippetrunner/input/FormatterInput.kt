package printscript.group13.snippetrunner.input

import org.example.formatter.FormattingRules


data class FormatterInput(
    val code: String,
    val version: String,
    val rules: FormattingRules
)
