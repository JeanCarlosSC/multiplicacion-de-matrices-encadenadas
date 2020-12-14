package lib.sRAD.logicRAD

import java.lang.NumberFormatException

fun String.isDouble(): Boolean {
    return try {
        this.toDouble()
        true
    }catch (numberFormat: NumberFormatException){
        false
    }
}