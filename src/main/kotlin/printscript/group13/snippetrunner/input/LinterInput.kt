package printscript.group13.snippetrunner.input

import linter.LinterRules

data class LinterInput(
    val code: String,
    val version: String,
    val rules: LinterRules
)
