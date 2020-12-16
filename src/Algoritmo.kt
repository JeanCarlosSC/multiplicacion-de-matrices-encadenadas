import javax.swing.JTextField

fun evaluar(mTfNums: List<JTextField?>): Array<Array<String>> {

    val campos = mTfNums.size
    val n = Array(campos) { IntArray(campos) }
    val s = Array(campos) { Array(campos){"(AB)"} }
    val result = Array(campos + 1) { Array(campos + 1){"∞"} }

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
                        else -> ""
                    }
                    if (n[i][j] < n[i][k] + n[k+1][j] + d[i]*d[k+1]*d[j+1]){
                        asignado = n[i][j]
                    }
                    else {
                        asignado = n[i][k] + n[k+1][j] + d[i]*d[k+1]*d[j+1]
                    }
                    n[i][j]=asignado
                    str += "k=$k Asignó $asignado.<br>"
                    str += "min(${n[i][j]}, ${n[i][k]}  + ${n[k+1][j]} ${s[i][j]} + ${d[i]}*${d[k+1]}*${d[j+1]})<br>"
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
