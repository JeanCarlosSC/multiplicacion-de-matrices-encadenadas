package lib.sRAD.logicRAD

fun Char.isOperator(): Boolean {
    return this == '+' || this == '-' || this == '*' || this == '^' || this == '/'
}
