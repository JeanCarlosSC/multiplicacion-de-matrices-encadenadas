import javax.swing.JOptionPane
import javax.swing.JTextField

val letras = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G')

fun evaluar(mTfNums: List<JTextField?>): Array<Array<String>> {

    val campos = mTfNums.size
    val n = Array(campos) { IntArray(campos) }
    val s = Array(campos) { Array(campos){"(AB)"} }
    val result = Array(campos + 1) { Array(campos + 1){"∞"} }

    //validar dimensiones
    for(i in mTfNums.indices) {
        if (i>0 && mTfNums[i-1].segundoValor() != mTfNums[i].primerValor()){
            JOptionPane.showMessageDialog(null, "Dimensiones incorrectas", "ERROR", JOptionPane.ERROR_MESSAGE)
            return Array(1){ Array(1){""}}
        }
    }

    //tabla
    result[0][0] = "i / j"
    for (i in 0 until campos) {
        result[0][i + 1] = i.toString() + ""
        result[i + 1][0] = i.toString() + ""
    }

    //tabla y matriz N
    for (i in 0 until campos) {
        for (j in 0 until campos) {
            if (j <= i) {
                result[i + 1][j + 1] = "0"
                n[i][j] = 0
            }
            else {
                n[i][j] = Int.MAX_VALUE
            }
        }
    }

    //matriz d
    val d = IntArray(campos+1)
    for (i in 0 .. campos) {
        d[i] =
            if(i==campos)
                mTfNums[i-1].segundoValor()
            else
                mTfNums[i].primerValor()
    }

    //calculo
    for(j in 0 until campos) {
        for(i in campos-1 downTo 0) {
            if(j>i) {
                var str = "<html>"
                for (k in i until j) {
                    var asignado : Int
                    s[i][j] = when {
                        j==i+1 -> when (j) {
                            1 -> "(AB)"
                            2 -> "(BC)"
                            3 -> "(CD)"
                            4 -> "(DE)"
                            else -> "(EF)"
                        }
                        else -> when (k-i) {
                            0 -> s[i+1][i+2].completar(j-i)//j cantidad de letras en total y se agregan a la derecha
                            1 -> s[i][i+1]
                            2 -> s[i][i+2].completar(3)
                            3 -> s[i][i+3].completar(4)
                            else -> s[i][i+4].completar(5)
                        }
                    }
                    if (n[i][j] < n[i][k] + n[k+1][j] + d[i]*d[k+1]*d[j+1]){
                        asignado = n[i][j]
                    }
                    else {
                        asignado = n[i][k] + n[k+1][j] + d[i]*d[k+1]*d[j+1]
                    }
                    n[i][j]=asignado
                    str += "k=$k Asignó $asignado.<br>"
                    str += "min(${n[i][j]}, ${n[i][k]} " +
                            "${if(n[k+1][j] != 0) s[i][j].complemento(j-i) else if(n[i][k] != 0) s[i][j] else ""} + " +
                            "${n[k+1][j]} ${if(n[k+1][j] != 0) s[i][j] else s[i][j].complemento(j-i)} + ${d[i]}*${d[k+1]}*${d[j+1]})<br>"
                    str += "${s[i][j]}<br>"
                    str += "<br>"
                }
                str += "= ${n[i][j]} ${s[i][j]}<br>"
                str += "</html>"
                result[i+1][j+1] = str
            }
        }
    }
    return result
}

private fun String.completar(cantidadLetras: Int): String {
    if(cantidadLetras == 2) {
        return this
    }
    if(cantidadLetras > 2) {
        val first: Char
        var i = 0
        while (true){
            if(this[i].isLetter()){
                first = this[i]
                break
            }
            i++
        }
        var str = this
        var add = false
        var pendientes = cantidadLetras - (this.length+2)/3
        for (j in letras.indices) {
            if(first == letras[j])
                add = true
            if (!str.contains(letras[j]) && pendientes>0 && add == true) {
                str = "($str${letras[j]})"
                pendientes--
            }
        }
        return str
    }
    else return "Z"
}

private fun String.complemento(cantidadLetras: Int): String {
    var str = ""
    var pendientes = cantidadLetras - (this.length+2)/3
    for (j in letras.indices) {
        if (!this.contains(letras[j]) && pendientes>0) {
            if(str.length > 0)
                str = "($str${letras[j]})"
            if(str.length == 0)
                str = "${letras[j]}"
            pendientes--
        }
    }
    return str
}

private fun JTextField?.segundoValor(): Int {
    var num = ""
    for (i in this!!.text.indices) {
        if (text[i] == ','){
            num = text.substring(i+1, text.length)
            break
        }
    }
    return num.toInt()
}

private fun JTextField?.primerValor(): Int {
    var num = ""
    for (i in this!!.text.indices) {
        if (text[i] == ','){
            num = text.substring(0, i)
            break
        }
    }
    return num.toInt()
}
