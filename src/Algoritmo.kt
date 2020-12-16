import javax.swing.JOptionPane
import javax.swing.JTextField

var letras = listOf('A', 'B', 'C', 'D', 'E', 'F') //letras usadas para nombrar a las matrices

fun evaluar(mTfNums: List<JTextField?>): Array<Array<String>> {

    val campos = mTfNums.size //cantidad de campos ingresados
    val result = Array(campos + 1) { Array(campos + 1){"∞"} } //matriz que se devuelve con todos los datos obtenidos
    val n = Array(campos) { IntArray(campos) } //matriz de valores asignados

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

    //matriz de cadenas de operaciones
    val s = Array(campos) { Array(campos){""} }
    for (j in 0 until campos){
        for (i in 0 until campos){
            if (j==i+1){
                s[i][j] = when (j) {
                    1 -> "(AB)"
                    2 -> "(BC)"
                    3 -> "(CD)"
                    4 -> "(DE)"
                    else -> "(EF)"
                }
            }
        }
    }

    //calculo
    var min = "" //minimo que varia de acuerdo a cada comparacion k

    for(j in 0 until campos) {
        for(i in campos-1 downTo 0) {
            if(j>i) {
                var str = "<html>"
                for (k in i until j) {
                    var asignado : Int  //valor que asigna cada comparacion k
                    var operacion1 = "" //operacion de la que viene n[i][k]
                    var operacion2 = "" //operacion de la que viene n[k+1][j]

                    if(j-i != 1) {
                        if (n[i][k] == 0)
                            operacion1 = ""
                        else
                            operacion1 = s[i][k]

                        if (n[k + 1][j] == 0)
                            operacion2 = ""
                        else
                            operacion2 = s[k + 1][j]
                    }

                    if (n[i][j] < n[i][k] + n[k+1][j] + d[i]*d[k+1]*d[j+1]){ //comparacion k
                        asignado = n[i][j]
                    }
                    else {
                        asignado = n[i][k] + n[k+1][j] + d[i]*d[k+1]*d[j+1]
                        if(j-i != 1){
                            if(n[i][k] != 0 && n[k + 1][j] != 0)
                                min = "[$operacion1$operacion2]".completar(j - i + 1, j)
                            else
                                min = "$operacion1$operacion2".completar(j - i + 1, j)
                        }
                        else
                            min = s[i][j]
                    }

                    val temp = n[i][j]
                    n[i][j]=asignado

                    //guardar resultados
                    str += "k=$k Asignó $asignado.<br>"
                    str += "min(${if (temp == Int.MAX_VALUE) "∞" else temp}, " +
                            "${n[i][k]} $operacion1 + ${n[k+1][j]} $operacion2 + ${d[i]}*${d[k+1]}*${d[j+1]})<br>"
                    str += "${min}<br>"
                    str += "<br>"
                }

                //ultimos datos a guardar en celda
                str += "= ${n[i][j]} ${min}<br>"
                str += "</html>"
                s[i][j] = min
                result[i+1][j+1] = str
            }
        }
    }

    //retorno
    return result
}

private fun String.completar(cantidadLetras: Int, j: Int): String {
    if (this.isEmpty())
        return ""

    if(cantidadLetras == 2)
        return this

    if(cantidadLetras > 2) {
        var first = ' '
        for (i in this){
            if(i.isLetter()){
                first = i
                break
            }
        }
        var str = this
        var add = false
        var pendientes = cantidadLetras - (this.length+2)/3

        //agrega a la derecha
        for (i in 0 .. j) {
            if(first == letras[i])
                add = true

            if (!str.contains(letras[i]) && pendientes>0 && add == true) {
                str = "($str${letras[i]})"
                pendientes--
            }
        }

        //agrega a la izquierda
        if (pendientes > 0){
            for (i in j downTo 0) {
                if (!str.contains(letras[i]) && pendientes>0) {
                    str = "(${letras[i]}$str)"
                    pendientes--
                }
            }
        }
        return str
    }
    else return "ERROR"
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
